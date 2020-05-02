package com.nd2k.server.service;

import com.nd2k.server.dto.AuthenticationResponse;
import com.nd2k.server.dto.UserRequestDto;
import com.nd2k.server.dto.UserResponseDto;
import com.nd2k.server.exception.BusinessException;
import com.nd2k.server.mapper.UserMapper;
import com.nd2k.server.model.User;
import com.nd2k.server.repository.UserRepository;
import com.nd2k.server.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

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
    public AuthenticationResponse login(@RequestBody UserRequestDto userRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userRequestDto.getEmail(),
                userRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new AuthenticationResponse(token, userRequestDto.getEmail());
    }
}
