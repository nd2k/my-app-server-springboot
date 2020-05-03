package com.nd2k.server.service;

import com.nd2k.server.dto.RefreshTokenDto;
import com.nd2k.server.exception.BusinessException;
import com.nd2k.server.model.RefreshToken;
import com.nd2k.server.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken generateRefreshToken() {

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void validateRefreshToken(String refreshToken) {

        refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new BusinessException("RefreshToken Exception", "Invalid Refresh Token", HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteByRefreshToken(refreshToken);
    }
}
