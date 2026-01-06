package com.itwizard.starter.auth.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class UserRegisterDto {

    private String username;
    private String password;

}
