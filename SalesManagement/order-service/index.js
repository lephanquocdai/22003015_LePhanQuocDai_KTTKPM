const express = require('express');
const mongoose = require('mongoose');
const amqp = require('amqplib');
const axios = require('axios');

const app = express();
app.use(express.json());

const PORT = process.env.PORT || 3002;
const MONGO_URI = process.env.MONGO_URI || 'mongodb://localhost:27017/order_db';
const RABBITMQ_URL = process.env.RABBITMQ_URL || 'amqp://localhost';

mongoose.connect(MONGO_URI)
  .then(() => console.log('Connected to MongoDB - Order DB'))
  .catch(err => console.error('MongoDB connection error:', err));

const OrderSchema = new mongoose.Schema({
    productId: String,
    customerId: String,
    quantity: Number,
    status: { type: String, default: 'CREATED' } // CREATED, CANCELLED, COMPLETED
}, { timestamps: true });
const Order = mongoose.model('Order', OrderSchema);

let channel;
async function connectRabbitMQ() {
    try {
        const connection = await amqp.connect(RABBITMQ_URL);
        channel = await connection.createChannel();
        await channel.assertExchange('order_events', 'topic', { durable: false });
        console.log('Connected to RabbitMQ - Order Service');
    } catch (error) {
        console.error('RabbitMQ Connection failed, retrying...', error.message);
        setTimeout(connectRabbitMQ, 5000);
    }
}
setTimeout(connectRabbitMQ, 10000); // Initial delay to ensure RabbitMQ is up

app.post('/', async (req, res) => {
    try {
        const { productId, customerId, quantity } = req.body;

        // Check if product and customer exist via REST API calls
        try {
             await axios.get(`${process.env.PRODUCT_SERVICE_URL || 'http://localhost:3001'}/${productId}`);
             await axios.get(`${process.env.CUSTOMER_SERVICE_URL || 'http://localhost:3003'}/${customerId}`);
        } catch(e) {
            return res.status(400).json({ error: "Invalid product or customer ID, or services are unavailable." });
        }

        const order = new Order({ productId, customerId, quantity });
        await order.save();

        // Publish event to message broker
        if (channel) {
            const event = { orderId: order._id, productId, quantity, customerId };
            channel.publish('order_events', 'order.created', Buffer.from(JSON.stringify(event)));
            console.log("Published order.created event");
        }

        res.status(201).json(order);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

app.get('/', async (req, res) => {
    const orders = await Order.find();
    res.json(orders);
});

app.get('/:id', async (req, res) => {
    try {
        const order = await Order.findById(req.params.id);
        if (!order) return res.status(404).send('Order not found');
        res.json(order);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

app.put('/:id', async (req, res) => {
    try {
        const order = await Order.findByIdAndUpdate(req.params.id, req.body, {new: true});
        res.json(order);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

app.delete('/:id', async (req, res) => {
    try {
        // Soft delete / cancel order
        const order = await Order.findByIdAndUpdate(req.params.id, { status: 'CANCELLED' }, {new: true});
        
        // Publish cancel event if needed to rollback inventory
        if (channel) {
            const event = { orderId: order._id, productId: order.productId, quantity: order.quantity };
            channel.publish('order_events', 'order.cancelled', Buffer.from(JSON.stringify(event)));
            console.log("Published order.cancelled event");
        }
        
        res.json({ message: "Order cancelled", order });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

app.listen(PORT, () => console.log(`Order Service running on port ${PORT}`));
