package com.itwizard.starter.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserRegisterDto {
    private String username;
    private String password;
}
