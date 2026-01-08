package com.itwizard.starter.util;

import java.time.Instant;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.itwizard.starter.exception.ResourceNotFoundException;
import com.itwizard.starter.exception.UnauthorizedException;
import com.itwizard.starter.modules.auth.entity.RefreshToken;
import com.itwizard.starter.modules.auth.entity.User;
import com.itwizard.starter.modules.auth.repository.RefreshTokenRepository;
import com.itwizard.starter.modules.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RefreshTokenUtil {

  private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60; // 7d

  private final SecretGenerator secretGenerator;
  private final PasswordEncoder passwordEncoder;

  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;

  private Long currentRefreshTokenId = null;

  private void revokeRefreshToken(RefreshToken refreshToken) {
    if (currentRefreshTokenId != null) {
      refreshToken.setRevoked(true);
      refreshToken.setRevokedAt(Instant.now());
      refreshToken.setReplacedById(this.currentRefreshTokenId);

      // INFO: force delete refresh token
      refreshTokenRepository.deleteById(refreshToken.getId());

      this.refreshTokenRepository.save(refreshToken);
    }
  }

  public String generateRefreshToken(Long userId, String ip, String agent) throws Exception {
    Instant expirationTime = Instant.now().plusSeconds(REFRESH_TOKEN_EXPIRATION);

    String token = this.secretGenerator.generateString(32);
    String lookup = this.secretGenerator.hmac(token);

    User user = this.userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("TODO(i18n): user not found for generate token"));

    RefreshToken refreshToken = this.refreshTokenRepository.save(RefreshToken
        .builder()
        .token(this.passwordEncoder.encode(token))
        .lookupHash(lookup)
        .createdByIp(ip)
        .userAgent(agent)
        .user(user)
        .expiresAt(expirationTime)
        .build());

    this.currentRefreshTokenId = refreshToken.getId();

    return token;
  }

  public User verify(String token) throws Exception {
    String lookup = this.secretGenerator.hmac(token);

    RefreshToken refreshToken = this.refreshTokenRepository.findByLookupHash(lookup)
        .orElseThrow(() -> new ResourceNotFoundException(token));

    if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
      throw new UnauthorizedException("TODO(i18n): Invalid refresh token");
    }

    if (refreshToken.isRevoked()) {
      throw new UnauthorizedException("TODO(i18n): Invalid refresh token");
    }

    this.revokeRefreshToken(refreshToken);

    return refreshToken.getUser();
  }
}
