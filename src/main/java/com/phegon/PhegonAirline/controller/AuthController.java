package com.phegon.PhegonAirline.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phegon.PhegonAirline.dtos.LoginRequest;
import com.phegon.PhegonAirline.dtos.LoginResponse;
import com.phegon.PhegonAirline.dtos.RegistrationRequest;
import com.phegon.PhegonAirline.dtos.Response;
import com.phegon.PhegonAirline.services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Response<?>> register(@Valid @RequestBody RegistrationRequest registrationRequest){
        return ResponseEntity.ok(authService.register(registrationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
