package com.phegon.PhegonAirline.services;

import com.phegon.PhegonAirline.dtos.LoginRequest;
import com.phegon.PhegonAirline.dtos.LoginResponse;
import com.phegon.PhegonAirline.dtos.RegistrationRequest;
import com.phegon.PhegonAirline.dtos.Response;

public interface AuthService {
    Response<?> register (RegistrationRequest registrationRequest);
    Response<LoginResponse> login(LoginRequest loginRequest);
}
