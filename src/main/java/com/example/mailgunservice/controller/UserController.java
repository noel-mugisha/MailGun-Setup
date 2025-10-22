package com.example.mailgunservice.controller;

import com.example.mailgunservice.model.User;
import com.example.mailgunservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerAndSendOtp(
            @RequestBody Map<String, String> request
    ) {
        try {
            String fullName = request.get("fullName");
            String email = request.get("email");

            User user = userService.registerUser(fullName, email);

            return ResponseEntity.ok("User registered and OTP email sent successfully to: " + user.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
