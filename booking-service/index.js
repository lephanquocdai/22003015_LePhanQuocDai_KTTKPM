const express = require('express');
const cors = require('cors');
const app = express();
const port = 8083;

app.use(cors(), express.json());

app.post('/bookings', (req, res) => {
    const { userId, tourId } = req.body;

    // Tạo ID random định dạng Bxxx
    const bookingId = 'B' + Math.floor(100 + Math.random() * 900);
    
    console.log(`[Booking] ${bookingId} created for user ${userId}`);

    res.json({
        "bookingId": bookingId
    });
});

app.listen(port, () => console.log(`Booking Service running on port ${port}`));

