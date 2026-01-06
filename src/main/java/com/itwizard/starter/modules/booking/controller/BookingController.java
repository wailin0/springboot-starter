package com.itwizard.starter.modules.booking.controller;

import com.itwizard.starter.modules.user.entity.User;
import com.itwizard.starter.modules.user.repository.UserRepository;
import com.itwizard.starter.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class BookingController {

    private final UserRepository userRepository;

    public BookingController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getLoggedInUser(Authentication authentication) {


        User user = userRepository.findByUsername(authentication.getName());


        return ResponseUtil.success(null, user);

    }


}
