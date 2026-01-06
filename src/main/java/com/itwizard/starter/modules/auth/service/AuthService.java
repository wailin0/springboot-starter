package com.itwizard.starter.modules.auth.service;

import com.itwizard.starter.modules.auth.entity.User;
import com.itwizard.starter.exception.ResourceNotFoundException;
import com.itwizard.starter.exception.UnauthorizedException;
import com.itwizard.starter.exception.ValidationException;
import com.itwizard.starter.modules.auth.dto.LoginRequest;
import com.itwizard.starter.modules.auth.dto.RegisterRequest;
import com.itwizard.starter.modules.user.repository.UserRepository;
import com.itwizard.starter.util.JwtUtil;
import com.itwizard.starter.modules.auth.dto.JwtPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new ValidationException("User already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        user.setEnabled(true);

        return userRepository.save(user);
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Incorrect password");
        }

        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new UnauthorizedException("User account is disabled");
        }

        JwtPayload jwtPayload = new JwtPayload();
        jwtPayload.setUsername(user.getUsername());
        jwtPayload.setRole(user.getRole());

        return jwtUtil.generateToken(jwtPayload);
    }
}

