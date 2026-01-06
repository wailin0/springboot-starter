package com.itwizard.starter.modules.auth.dto;

import lombok.Data;

@Data
public class JwtPayload {
    private String username;
    private String role;
}

