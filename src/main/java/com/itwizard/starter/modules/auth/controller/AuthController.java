package com.itwizard.starter.modules.auth.controller;

import com.itwizard.starter.util.ApiResponse;
import com.itwizard.starter.modules.auth.dto.LoginRequest;
import com.itwizard.starter.modules.auth.dto.RegisterRequest;
import com.itwizard.starter.modules.auth.service.AuthService;
import com.itwizard.starter.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping
    public ResponseEntity<ApiResponse> getUser(Authentication authentication) {
        if (authentication == null) {
            return ResponseUtil.success("Welcome", "anonymous user");
        }
        return ResponseUtil.success("Welcome", authentication.getName());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseUtil.success("Login successful", token);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseUtil.created("User registered successfully", null);
    }
}
