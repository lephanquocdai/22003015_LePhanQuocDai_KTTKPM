package com.foodordering.foodservice;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.PostConstruct;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
class Food {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
}

interface FoodRepository extends JpaRepository<Food, Long> {}

@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
class FoodController {
    private final FoodRepository foodRepository;

    @PostConstruct
    public void seed() {
        foodRepository.save(new Food(null, "Pizza", 10.0));
        foodRepository.save(new Food(null, "Burger", 5.0));
    }

    @GetMapping
    public List<Food> getFoods() { return foodRepository.findAll(); }

    @GetMapping("/{id}")
    public Food getFood(@PathVariable("id") Long id) { return foodRepository.findById(id).orElseThrow(); }

    @PostMapping
    public Food addFood(@RequestBody Food food) { return foodRepository.save(food); }

    @PutMapping("/{id}")
    public Food updateFood(@PathVariable("id") Long id, @RequestBody Food food) {
        food.setId(id);
        return foodRepository.save(food);
    }

    @DeleteMapping("/{id}")
    public void deleteFood(@PathVariable("id") Long id) { foodRepository.deleteById(id); }
}


