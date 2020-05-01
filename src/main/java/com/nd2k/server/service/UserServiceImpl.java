package com.nd2k.server.service;

import com.nd2k.server.dto.UserRequestDto;
import com.nd2k.server.dto.UserResponseDto;
import com.nd2k.server.exception.BusinessException;
import com.nd2k.server.mapper.UserMapper;
import com.nd2k.server.model.User;
import com.nd2k.server.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponseDto register(UserRequestDto userRequestDto) throws BusinessException {
        Optional<User> userAlreadyRegistered = userRepository.findUserByEmail(userRequestDto.getEmail());
        if(Boolean.FALSE.equals(userAlreadyRegistered.isPresent())) {
            User user = new User();
            user.setEmail(userRequestDto.getEmail());
            user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
            userRepository.save(user);
            return UserMapper.userToUserResponseDto(user);
        }
        throw new BusinessException("Email already taken", "Your email address has already been taken", HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserResponseDto login(UserRequestDto userRequestDto) throws BusinessException {
        try {
            Optional<User> user = userRepository.findUserByEmail(userRequestDto.getEmail());
            if(passwordEncoder.matches(userRequestDto.getPassword(), user.get().getPassword())) {
                UserResponseDto userResponseDto = new UserResponseDto();
                userResponseDto.setEmail(user.get().getEmail());
                userResponseDto.setPassword(user.get().getPassword());
                userResponseDto.setHttpStatus(HttpStatus.OK);
                return userResponseDto;
            } else {
                throw new BusinessException("User not found", "The given credentials are wrong", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            throw new BusinessException("Technical Error", "A technical problem occurs", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
