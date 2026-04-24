const express = require('express');
const cors = require('cors');
const app = express();
const port = 8084;

app.use(cors(), express.json());

app.post('/payments', (req, res) => {
    const { bookingId, amount } = req.body;

    // Random success/fail
    const isSuccess = Math.random() > 0.2;
    const status = isSuccess ? "success" : "fail";

    console.log(`[Payment] Booking ${bookingId}: ${status}`);

    res.json({
        "status": status
    });
});

app.listen(port, () => console.log(`Payment Service running on port ${port}`));

