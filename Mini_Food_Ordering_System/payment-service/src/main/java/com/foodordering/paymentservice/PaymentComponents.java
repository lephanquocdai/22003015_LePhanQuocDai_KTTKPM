package com.foodordering.paymentservice;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Configuration
class AppConfig {
    @Bean
    public RestTemplate restTemplate() { return new RestTemplate(); }
}

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private Double amount;
    private String method; // COD, Banking
    private String status;
}

interface PaymentRepository extends JpaRepository<Payment, Long> {}

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
class PaymentController {
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    @Value("${ORDER_SERVICE_URL:http://localhost:8083}")
    private String orderServiceUrl;

    @PostMapping
    public Payment processPayment(@RequestBody Payment payment) {
        payment.setStatus("PAID");
        Payment saved = paymentRepository.save(payment);
        
        // Update Order status
        restTemplate.put(orderServiceUrl + "/orders/" + payment.getOrderId() + "/status?status=PAID", null);
        
        // Notification
        System.out.println("Notification: User placed order #" + payment.getOrderId() + " successfully and paid via " + payment.getMethod());
        
        return saved;
    }
}

