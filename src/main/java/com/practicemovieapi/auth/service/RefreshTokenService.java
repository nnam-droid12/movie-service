package com.practicemovieapi.auth.service;

import com.practicemovieapi.Exception.RefreshTokenExpiredException;
import com.practicemovieapi.Exception.RefreshTokenNotFoundException;
import com.practicemovieapi.auth.entities.RefreshToken;
import com.practicemovieapi.auth.entities.User;
import com.practicemovieapi.auth.repository.RefreshTokenRepository;
import com.practicemovieapi.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken createRefreshToken(String username){
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        RefreshToken refreshToken = user.getRefreshToken();

        if(refreshToken == null){
            refreshToken = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expirationTime(Instant.now().plusMillis(5*60*60*1000))
                    .user(user)
                    .build();
            refreshTokenRepository.save(refreshToken);
        }
        return refreshToken;
    }

    public RefreshToken verifyRefreshToken(String refreshToken){
        RefreshToken refToken = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RefreshTokenNotFoundException("refreshToken not found"));

        if(refToken.getExpirationTime().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(refToken);
            throw new RefreshTokenExpiredException("refresh token expired");
        }
        return refToken;
    }
}
