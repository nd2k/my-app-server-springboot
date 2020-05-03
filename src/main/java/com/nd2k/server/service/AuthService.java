package com.nd2k.server.service;

import com.nd2k.server.dto.AuthenticationResponse;
import com.nd2k.server.dto.RefreshTokenDto;
import com.nd2k.server.dto.UserRequestDto;
import com.nd2k.server.dto.UserResponseDto;

public interface AuthService {

    UserResponseDto register(UserRequestDto userRequestDto);

    AuthenticationResponse login(UserRequestDto userRequestDto);

    AuthenticationResponse refreshToken(RefreshTokenDto refreshTokenDto);
}
