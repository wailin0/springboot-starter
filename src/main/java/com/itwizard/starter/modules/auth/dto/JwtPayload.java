package com.itwizard.starter.modules.auth.dto;

import lombok.Data;

import java.util.List;

@Data
public class JwtPayload {

    private String username;
    private String role;
}
