package com.itwizard.starter.auth.controller;

import com.itwizard.starter.auth.dto.ApiResponse;
import com.itwizard.starter.auth.entity.User;
import com.itwizard.starter.auth.repository.UserRepository;
import com.itwizard.starter.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getLoggedInUser(Authentication authentication) {


        User user = userRepository.findByUsername(authentication.getName());


        return ResponseUtil.success(null, user);

    }


}
