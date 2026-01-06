package com.itwizard.starter.auth.controller;

import com.itwizard.starter.auth.dto.ApiResponse;
import com.itwizard.starter.auth.dto.JwtPayload;
import com.itwizard.starter.auth.dto.UserRegisterDto;
import com.itwizard.starter.auth.entity.User;
import com.itwizard.starter.auth.repository.UserRepository;
import com.itwizard.starter.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.itwizard.starter.util.JwtUtil;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;





    @GetMapping
    public ResponseEntity<ApiResponse> getUser(Authentication authentication) {

        if (authentication == null) {
            return ResponseUtil.success("Welcome", "anonymous user");
        }

        return ResponseUtil.success("Welcome", authentication.getName());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody User user) {
        User userData = userRepository.findByUsername(user.getUsername());

        System.out.println("userData"+ userData);

        if (userData == null) {
            return ResponseUtil.notFound("User not found");
        }

        if (!passwordEncoder.matches(user.getPassword(), userData.getPassword())) {
            return ResponseUtil.unauthorized("Incorrect password");
        }

        JwtPayload jwtPayload = new JwtPayload();
        jwtPayload.setUsername(userData.getUsername());
        jwtPayload.setRole(userData.getRole());

        String token = jwtUtil.generateToken(jwtPayload);

        return ResponseUtil.success("Login successful", token);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserRegisterDto userRegisterDto) {
        // Check if user already exists
        User existingUser = userRepository.findByUsername(userRegisterDto.getUsername());
        if (existingUser != null) {
            return ResponseUtil.error("User already exists");
        }

        User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setRole("user");


        userRepository.save(user);

        return ResponseUtil.success("User registered successfully");
    }
}
