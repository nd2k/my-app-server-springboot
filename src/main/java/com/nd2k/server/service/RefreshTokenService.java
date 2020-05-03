package com.nd2k.server.service;

import com.nd2k.server.dto.RefreshTokenDto;
import com.nd2k.server.model.RefreshToken;

public interface RefreshTokenService {

    RefreshToken generateRefreshToken();

    void validateRefreshToken(String refreshToken);

    void deleteRefreshToken(String refreshToken);
}
