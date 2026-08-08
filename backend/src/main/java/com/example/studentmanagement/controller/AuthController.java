package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.AuthenticationRequest;
import com.example.studentmanagement.dto.AuthenticationResponse;
import com.example.studentmanagement.dto.RegisterRequest;
import com.example.studentmanagement.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        log.info("New registration request received for email: {}", request.getEmail());
        AuthenticationResponse response = service.register(request);
        log.info("User registered successfully: {}", request.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        log.info("Authentication request received for email: {}", request.getEmail());
        AuthenticationResponse response = service.authenticate(request);
        log.info("User authenticated successfully: {}", request.getEmail());
        return ResponseEntity.ok(response);
    }
}
