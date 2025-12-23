package com.internship.taskmanager.service;

import com.internship.taskmanager.dto.LoginRequest;
import com.internship.taskmanager.dto.LoginResponse;
import com.internship.taskmanager.model.User;
import com.internship.taskmanager.repository.UserRepository;
import com.internship.taskmanager.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        // In production, use password encoder
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return LoginResponse.builder()
                .token(token)
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
