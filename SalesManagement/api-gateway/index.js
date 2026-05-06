const express = require('express');
const proxy = require('express-http-proxy');
const cors = require('cors');

const app = express();

// Middleware
app.use(cors());

// Health check
app.get('/health', (req, res) => res.send('API Gateway is running'));

// Proxy routes to Microservices
app.use('/products', proxy('http://product-service:3001'));
app.use('/orders', proxy('http://order-service:3002'));
app.use('/customers', proxy('http://customer-service:3003'));

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`API Gateway is listening on port ${PORT}`);
});
