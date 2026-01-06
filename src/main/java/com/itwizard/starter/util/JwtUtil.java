package com.itwizard.starter.util;

import com.itwizard.starter.auth.dto.JwtPayload;
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

    @Value("${jwt.expiry:3600}")
    private long expiry;

    public JwtUtil(JwtEncoder encoder) {
        this.encoder = encoder;
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
        // @formatter:on
        JwsHeader jwsHeader = JwsHeader
                .with(MacAlgorithm.HS256).build();
        return this.encoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

}
