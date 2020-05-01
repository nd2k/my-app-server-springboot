package com.nd2k.server.mapper;

import com.nd2k.server.dto.UserResponseDto;
import com.nd2k.server.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserResponseDto userToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPassword(user.getPassword());
        return userResponseDto;
    }
}
