package com.hospital.queue_system.service;

import com.hospital.queue_system.dto.LoginRequest;
import com.hospital.queue_system.dto.LoginResponse;
import com.hospital.queue_system.model.User;
import com.hospital.queue_system.repository.UserRepository;
import com.hospital.queue_system.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtils.generateToken(user.getEmail(), user.getRole());
        return new LoginResponse(token, user.getRole(), user.getName(), user.getEmail());
    }
}