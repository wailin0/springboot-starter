package com.itwizard.starter.modules.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.itwizard.starter.modules.auth.entity.User;

@Data
@Entity
@Table(name = "user_resumes")
public class UserResume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String resumeUrl;
    private String resumeFileName;

    // Add other resume-related fields as needed
}

