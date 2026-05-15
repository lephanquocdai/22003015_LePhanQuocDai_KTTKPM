package com.foodordering.orderservice;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Configuration
class AppConfig {
    @Bean
    public RestTemplate restTemplate() { return new RestTemplate(); }
}

@Entity
@Table(name = "orders")
@Data @NoArgsConstructor @AllArgsConstructor
class OrderEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long foodId;
    private String status;
}

interface OrderRepository extends JpaRepository<OrderEntity, Long> {}

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
class OrderController {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @Value("${USER_SERVICE_URL:http://localhost:8081}")
    private String userServiceUrl;
    
    @Value("${FOOD_SERVICE_URL:http://localhost:8082}")
    private String foodServiceUrl;
    
    @Value("${INVENTORY_SERVICE_URL:http://localhost:8085}")
    private String inventoryServiceUrl;

    @PostMapping
    @CircuitBreaker(name = "orderService", fallbackMethod = "orderFallback")
    @Retry(name = "orderService")
    @RateLimiter(name = "orderService")
    @TimeLimiter(name = "orderService")
    public CompletableFuture<OrderEntity> createOrder(@RequestBody OrderEntity order) {
        return CompletableFuture.supplyAsync(() -> {
            // Validate User
            restTemplate.getForObject(userServiceUrl + "/users/" + order.getUserId(), Object.class);
            // Get Food
            restTemplate.getForObject(foodServiceUrl + "/foods/" + order.getFoodId(), Object.class);
            // Deduct Inventory
            restTemplate.postForObject(inventoryServiceUrl + "/inventory/deduct/" + order.getFoodId() + "?qty=1", null, String.class);
            
            order.setStatus("CREATED");
            return orderRepository.save(order);
        });
    }
    
    public CompletableFuture<OrderEntity> orderFallback(OrderEntity order, Throwable t) {
        order.setStatus("FAILED: " + t.getMessage());
        return CompletableFuture.completedFuture(order);
    }

    @GetMapping
    public List<OrderEntity> getOrders() { return orderRepository.findAll(); }

    @PutMapping("/{id}/status")
    public OrderEntity updateStatus(@PathVariable("id") Long id, @RequestParam("status") String status) {
        OrderEntity order = orderRepository.findById(id).orElseThrow();
        order.setStatus(status);
        return orderRepository.save(order);
    }
}


