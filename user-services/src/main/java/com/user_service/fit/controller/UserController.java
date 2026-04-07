package com.user_service.fit.controller;

import com.user_service.fit.dto.UserDTO;
import com.user_service.fit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/validate/{id}")
    public ResponseEntity<UserDTO> validateUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.validateUser(id));
    }
}
