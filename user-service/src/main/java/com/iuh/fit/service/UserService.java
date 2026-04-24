package com.iuh.fit.service;

import com.iuh.fit.dto.LoginRequest;
import com.iuh.fit.dto.RegisterRequest;
import com.iuh.fit.event.UserEvent;
import com.iuh.fit.event.UserEventProducer;
import com.iuh.fit.model.User;
import com.iuh.fit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserEventProducer userEventProducer;

    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists: " + request.getUsername());
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword()) // plain text for demo
                .fullName(request.getFullName())
                .build();

        User saved = userRepository.save(user);
        log.info("[USER-SERVICE] User saved to DB: userId={}", saved.getId());

        // Publish USER_REGISTERED event
        UserEvent event = UserEvent.builder()
                .eventType("USER_REGISTERED")
                .userId(saved.getId())
                .username(saved.getUsername())
                .email(saved.getEmail())
                .fullName(saved.getFullName())
                .timestamp(LocalDateTime.now())
                .build();

        userEventProducer.publishUserRegistered(event);
        return saved;
    }

    public User login(LoginRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .filter(u -> u.getPassword().equals(request.getPassword()))
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }
}
