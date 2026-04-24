# Travel Booking System - Person 5

Phần việc của Người 5 trong hệ thống Microservices: **Booking Service** & **Payment Service**.

## 1. Booking Service (Port 3001)
- **API**: `POST /bookings`
- **Input**: `{ "userId": "string", "tourId": "string" }`
- **Output**: `{ "bookingId": "BKxxxx" }`

## 2. Payment Service (Port 3002)
- **API**: `POST /payments`
- **Input**: `{ "bookingId": "string", "amount": number }`
- **Output**: `{ "status": "success" | "fail" }`

## 3. Hướng dẫn chạy
Mỗi service cần được cài đặt và chạy riêng biệt:

```bash
# Booking Service
cd booking-service
npm install
node index.js

# Payment Service
cd payment-service
npm install
node index.js
```

## 4. Nguyên tắc hệ thống
- Các service không gọi trực tiếp lẫn nhau.
- Chỉ nhận lệnh điều phối từ Orchestrator.
