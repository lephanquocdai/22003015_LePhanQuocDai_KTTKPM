const express = require('express');
const mongoose = require('mongoose');
const amqp = require('amqplib');

const app = express();
app.use(express.json());

const PORT = process.env.PORT || 3001;
const MONGO_URI = process.env.MONGO_URI || 'mongodb://localhost:27017/product_db';
const RABBITMQ_URL = process.env.RABBITMQ_URL || 'amqp://localhost';

// Connect to MongoDB
mongoose.connect(MONGO_URI)
  .then(() => console.log('Connected to MongoDB - Product DB'))
  .catch(err => console.error('MongoDB connection error:', err));

const ProductSchema = new mongoose.Schema({
    name: String,
    price: Number,
    description: String,
    inventory: Number
}, { timestamps: true });
const Product = mongoose.model('Product', ProductSchema);

// Connect to RabbitMQ
let channel;
async function connectRabbitMQ() {
    try {
        const connection = await amqp.connect(RABBITMQ_URL);
        channel = await connection.createChannel();
        await channel.assertExchange('order_events', 'topic', { durable: false });
        
        // Queue for inventory updates
        const q = await channel.assertQueue('product_inventory_queue', { exclusive: false });
        channel.bindQueue(q.queue, 'order_events', 'order.created');

        channel.consume(q.queue, async (msg) => {
            if (msg.content) {
                const event = JSON.parse(msg.content.toString());
                console.log("Received order.created event:", event);
                
                // Reduce inventory logic
                const product = await Product.findById(event.productId);
                if (product && product.inventory >= event.quantity) {
                    product.inventory -= event.quantity;
                    await product.save();
                    console.log(`Inventory reduced for product ${product.name}, new inventory: ${product.inventory}`);
                }
            }
        }, { noAck: true });

        console.log('Connected to RabbitMQ - Product Service');
    } catch (error) {
        console.error('RabbitMQ Connection failed, retrying...', error.message);
        setTimeout(connectRabbitMQ, 5000);
    }
}
setTimeout(connectRabbitMQ, 10000); // Initial delay to ensure RabbitMQ is up

// Routes for Product Service
app.post('/', async (req, res) => {
    try {
        const product = new Product(req.body);
        await product.save();
        res.status(201).json(product);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

app.get('/', async (req, res) => {
    const products = await Product.find();
    res.json(products);
});

app.get('/:id', async (req, res) => {
    try {
        const product = await Product.findById(req.params.id);
        if (!product) return res.status(404).send('Product not found');
        res.json(product);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

app.put('/:id', async (req, res) => {
    try {
        const product = await Product.findByIdAndUpdate(req.params.id, req.body, {new: true});
        res.json(product);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

app.delete('/:id', async (req, res) => {
    try {
        await Product.findByIdAndDelete(req.params.id);
        res.status(204).send();
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

app.listen(PORT, () => console.log(`Product Service running on port ${PORT}`));
