package com.itwizard.starter.modules.user.controller;

import com.itwizard.starter.util.ApiResponse;
import com.itwizard.starter.modules.user.service.UserProfileService;
import com.itwizard.starter.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserProfileService userProfileService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getLoggedInUser(Authentication authentication) {
        var user = userProfileService.getUserByUsername(authentication.getName());
        return ResponseUtil.success("User profile retrieved successfully", user);
    }
}
