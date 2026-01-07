package com.itwizard.starter.modules.auth.controller;

import com.itwizard.starter.util.ApiResponse;
import com.itwizard.starter.util.HttpUtil;
import com.itwizard.starter.modules.auth.dto.LoginRequest;
import com.itwizard.starter.modules.auth.dto.LoginResponseDto;
import com.itwizard.starter.modules.auth.dto.RefreshTokenRequestDto;
import com.itwizard.starter.modules.auth.dto.RegisterRequest;
import com.itwizard.starter.modules.auth.service.AuthService;
import com.itwizard.starter.util.ResponseUtil;
import com.itwizard.starter.util.TokenGenerateParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final HttpUtil httpUtil;

    private final AuthService authService;

    @GetMapping
    public ResponseEntity<ApiResponse> getUser(Authentication authentication) {
        if (authentication == null) {
            return ResponseUtil.success("Welcome", "anonymous user");
        }
        return ResponseUtil.success("Welcome", authentication.getName());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @Valid @RequestBody LoginRequest loginRequest) throws Exception {

        String ip = this.httpUtil.getClientIp(request);

        LoginResponseDto tokens = authService.login(loginRequest, new TokenGenerateParam(ip, userAgent));
        return ResponseUtil.success("Login successful", tokens);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseUtil.created("User registered successfully", null);
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RefreshTokenRequestDto request) throws Exception {
        String newAccessToken = authService.refreshNewAccessToken(request.getToken());

        return ResponseUtil.created("TODO(i18n): refresh new access-token", newAccessToken);
    }
}
