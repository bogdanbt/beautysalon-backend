package com.beautysalon.controller;

import com.beautysalon.config.JwtUtil;
import com.beautysalon.dto.JwtResponse;
import com.beautysalon.dto.LoginRequest;
import com.beautysalon.dto.RegisterRequest;
import com.beautysalon.model.User;
import com.beautysalon.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Шифруем пароль перед сохранением
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("client");

        userRepository.save(user);

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getId(),
                user.getRole()
        );


        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid email");
        }

        User user = userOpt.get();

        // Проверяем зашифрованный пароль
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getId(),
                user.getRole()
        );


        return ResponseEntity.ok(new JwtResponse(token));
    }
}
