package com.nd2k.server.controller;

import com.nd2k.server.dto.AuthenticationResponse;
import com.nd2k.server.dto.UserRequestDto;
import com.nd2k.server.dto.UserResponseDto;
import com.nd2k.server.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping(value = "api/v1/auth")
@AllArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<Object> register(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = authService.register(userRequestDto);
        userResponseDto.setHttpStatus(HttpStatus.OK);
        return new ResponseEntity<>(userResponseDto, userResponseDto.getHttpStatus());
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody UserRequestDto userRequestDto) {
        AuthenticationResponse authenticationResponse = authService.login(userRequestDto);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
}
