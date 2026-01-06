package com.itwizard.starter.modules.user.service;

import com.itwizard.starter.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Example service demonstrating how to use ResponseUtil for consistent responses
 */
@Service
public class UserService {

    /**
     * Example method showing clean response building with ResponseUtil
     *
     * @return ResponseEntity with message
     */
    public ResponseEntity<ApiResponse> getUserCreatedResponse() {
        return ResponseUtil.success("User created successfully");
    }

    /**
     * Example with message and data
     *
     * @param username The username to include in the response
     * @return ResponseEntity with message and data
     */
    public ResponseEntity<ApiResponse> getWelcomeResponse(String username) {
        return ResponseUtil.success("Welcome", username);
    }

    /**
     * Example error response
     *
     * @return ResponseEntity with error message
     */
    public ResponseEntity<ApiResponse> getUserNotFoundResponse() {
        return ResponseUtil.notFound("User not found");
    }
}
