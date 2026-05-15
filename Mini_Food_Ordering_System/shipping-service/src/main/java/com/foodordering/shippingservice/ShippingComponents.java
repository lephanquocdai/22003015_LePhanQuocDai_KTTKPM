package com.foodordering.shippingservice;

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
class Shipping {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private String status; // SHIPPING, DELIVERED
}

interface ShippingRepository extends JpaRepository<Shipping, Long> {}

@RestController
@RequestMapping("/shipping")
@RequiredArgsConstructor
class ShippingController {
    private final ShippingRepository shippingRepository;
    private final RestTemplate restTemplate;

    @Value("${ORDER_SERVICE_URL:http://localhost:8083}")
    private String orderServiceUrl;

    @PostMapping
    public Shipping createShipping(@RequestBody Shipping shipping) {
        shipping.setStatus("SHIPPING");
        Shipping saved = shippingRepository.save(shipping);
        
        // Update Order status
        restTemplate.put(orderServiceUrl + "/orders/" + shipping.getOrderId() + "/status?status=SHIPPING", null);
        
        return saved;
    }

    @GetMapping("/{orderId}")
    public Shipping getShipping(@PathVariable("orderId") Long orderId) {
        return shippingRepository.findAll().stream().filter(s -> s.getOrderId().equals(orderId)).findFirst().orElseThrow();
    }
}


