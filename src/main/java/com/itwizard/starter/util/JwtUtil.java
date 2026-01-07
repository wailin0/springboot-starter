package com.itwizard.starter.util;

import com.itwizard.starter.modules.auth.dto.JwtPayload;
import com.itwizard.starter.modules.auth.entity.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtUtil {

    private final JwtEncoder encoder;

    private final RefreshTokenUtil refreshTokenUtil;

    @Value("${jwt.expiry:3600}")
    private long expiry;

    public JwtUtil(JwtEncoder encoder, RefreshTokenUtil refreshTokenUtil) {
        this.encoder = encoder;
        this.refreshTokenUtil = refreshTokenUtil;
    }

    public String generateToken(JwtPayload jwtPayload) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("com.itwizard.starter")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(jwtPayload.getUsername())
                .claim("role", jwtPayload.getRole())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        return this.encoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public String generateRefreshToken(Long userId, String ip, String agent) throws Exception {
        String token = refreshTokenUtil.generateRefreshToken(userId, ip, agent);
        return token;
    }

    /**
     * INFO: just generate access-token
     * WARN: to prevent re-play attack, must generate refresh token, if refresh
     * token is used.
     */
    public String refreshNewAccessToken(String token) throws Exception {
        User user = refreshTokenUtil.verify(token);
        JwtPayload payload = JwtPayload.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .build();

        return generateToken(payload);
    }
}
