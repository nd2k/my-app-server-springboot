package com.nd2k.server.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RequestMapping(value = "api/v1/secure")
@AllArgsConstructor
public class SecureController {

    @GetMapping
    public String getSecureContent() {
        return "Secure page";
    }
}
