const express = require('express');
const mongoose = require('mongoose');

const app = express();
app.use(express.json());

const PORT = process.env.PORT || 3003;
const MONGO_URI = process.env.MONGO_URI || 'mongodb://localhost:27017/customer_db';

mongoose.connect(MONGO_URI)
  .then(() => console.log('Connected to MongoDB - Customer DB'))
  .catch(err => console.error('MongoDB connection error:', err));

const CustomerSchema = new mongoose.Schema({
    name: String,
    address: String,
    contact: String
}, { timestamps: true });
const Customer = mongoose.model('Customer', CustomerSchema);

app.post('/', async (req, res) => {
    try {
        const customer = new Customer(req.body);
        await customer.save();
        res.status(201).json(customer);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

app.get('/', async (req, res) => {
    const customers = await Customer.find();
    res.json(customers);
});

app.get('/:id', async (req, res) => {
    try {
        const customer = await Customer.findById(req.params.id);
        if (!customer) return res.status(404).send('Customer not found');
        res.json(customer);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

app.put('/:id', async (req, res) => {
    try {
        const customer = await Customer.findByIdAndUpdate(req.params.id, req.body, {new: true});
        res.json(customer);
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

app.delete('/:id', async (req, res) => {
    try {
        await Customer.findByIdAndDelete(req.params.id);
        res.status(204).send();
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

app.listen(PORT, () => console.log(`Customer Service running on port ${PORT}`));
