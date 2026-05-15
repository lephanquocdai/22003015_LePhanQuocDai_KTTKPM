package com.foodordering.inventoryservice;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.PostConstruct;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
class Inventory {
    @Id
    private Long foodId;
    private Integer quantity;
}

interface InventoryRepository extends JpaRepository<Inventory, Long> {}

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
class InventoryController {
    private final InventoryRepository inventoryRepository;

    @PostConstruct
    public void seed() {
        inventoryRepository.save(new Inventory(1L, 100));
        inventoryRepository.save(new Inventory(2L, 50));
    }

    @GetMapping("/{foodId}")
    public Inventory getInventory(@PathVariable("foodId") Long foodId) {
        return inventoryRepository.findById(foodId).orElse(new Inventory(foodId, 0));
    }

    @PostMapping("/deduct/{foodId}")
    public String deduct(@PathVariable("foodId") Long foodId, @RequestParam("qty") Integer qty) {
        Inventory inv = inventoryRepository.findById(foodId).orElseThrow();
        if (inv.getQuantity() >= qty) {
            inv.setQuantity(inv.getQuantity() - qty);
            inventoryRepository.save(inv);
            return "Success";
        }
        throw new RuntimeException("Not enough inventory");
    }
}


