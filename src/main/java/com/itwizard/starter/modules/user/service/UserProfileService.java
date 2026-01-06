package com.itwizard.starter.modules.user.service;

import com.itwizard.starter.modules.auth.entity.User;
import com.itwizard.starter.exception.ResourceNotFoundException;
import com.itwizard.starter.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        // Clear sensitive information before returning
        user.setPassword(null);
        return user;
    }
}

