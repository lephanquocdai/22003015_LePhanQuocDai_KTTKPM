package com.foodordering.userservice;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Entity
@Table(name = "users")
@Data @NoArgsConstructor @AllArgsConstructor
class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
}

interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByUsername(String username);
}

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UserController {
    private final UserRepository userRepository;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User existing = userRepository.findFirstByUsername(user.getUsername());
        if (existing != null && existing.getPassword().equals(user.getPassword())) {
            return "Login successful! Token: dummy-jwt-token-for-" + user.getUsername();
        }
        throw new RuntimeException("Invalid credentials");
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}


