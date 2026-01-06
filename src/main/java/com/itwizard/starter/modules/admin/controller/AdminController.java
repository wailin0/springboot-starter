package com.itwizard.starter.modules.admin.controller;

import com.itwizard.starter.util.ApiResponse;
import com.itwizard.starter.modules.auth.entity.User;
import com.itwizard.starter.modules.admin.service.AdminService;
import com.itwizard.starter.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<User> users = adminService.getAllUsers();
        return ResponseUtil.success("Users retrieved successfully", users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        User user = adminService.getUserById(id);
        user.setPassword(null); // Don't expose password
        return ResponseUtil.success("User retrieved successfully", user);
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<ApiResponse> updateUserRole(@PathVariable Long id, @RequestBody Map<String, String> request) {
        User user = adminService.updateUserRole(id, request.get("role"));
        user.setPassword(null);
        return ResponseUtil.success("User role updated successfully", user);
    }

    @PutMapping("/users/{id}/enable")
    public ResponseEntity<ApiResponse> enableUser(@PathVariable Long id) {
        adminService.enableUser(id);
        return ResponseUtil.success("User enabled successfully", null);
    }

    @PutMapping("/users/{id}/disable")
    public ResponseEntity<ApiResponse> disableUser(@PathVariable Long id) {
        adminService.disableUser(id);
        return ResponseUtil.success("User disabled successfully", null);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseUtil.success("User deleted successfully", null);
    }
}

