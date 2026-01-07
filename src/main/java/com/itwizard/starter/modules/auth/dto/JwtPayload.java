package com.itwizard.starter.modules.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class JwtPayload {
    private String username;
    private String role;
}

