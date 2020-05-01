package com.nd2k.server.service;

import com.nd2k.server.dto.UserRequestDto;
import com.nd2k.server.dto.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserResponseDto register(UserRequestDto userRequestDto);

    UserResponseDto login(UserRequestDto userRequestDto);
}
