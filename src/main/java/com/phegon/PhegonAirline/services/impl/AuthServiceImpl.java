package com.phegon.PhegonAirline.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.phegon.PhegonAirline.dtos.LoginRequest;
import com.phegon.PhegonAirline.dtos.LoginResponse;
import com.phegon.PhegonAirline.dtos.RegistrationRequest;
import com.phegon.PhegonAirline.dtos.Response;
import com.phegon.PhegonAirline.entities.Role;
import com.phegon.PhegonAirline.entities.User;
import com.phegon.PhegonAirline.enums.AuthMethod;
import com.phegon.PhegonAirline.exceptions.BadRequestException;
import com.phegon.PhegonAirline.exceptions.NotFoundException;
import com.phegon.PhegonAirline.repo.RoleRepo;
import com.phegon.PhegonAirline.repo.UserRepo;
import com.phegon.PhegonAirline.security.JwtUtils;
import com.phegon.PhegonAirline.services.AuthService;
import com.phegon.PhegonAirline.services.EmailNotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private EmailNotificationService emailNotificationService;


    @Override
    public Response<?> register(RegistrationRequest registrationRequest) {
        log.info("Inside register");
        if(userRepo.existsByEmail(registrationRequest.getEmail())){
            throw new BadRequestException("Email already exist");
        }
        List<Role> userRoles;
        if(registrationRequest.getRoles() != null && !registrationRequest.getRoles().isEmpty()){
            userRoles = registrationRequest.getRoles()
                .stream()
                .map(
                    roleName -> roleRepo
                    .findByName(roleName.toUpperCase())
                    .orElseThrow(()->new NotFoundException("Role "+roleName+" Not Found"))
                ).toList();
        }
        else{
            Role defaultRole = roleRepo.findByName("CUSTOMER")
            .orElseThrow(()-> new NotFoundException ("Role CUSTOMER DOESN'T EXISTS"));  
            userRoles = List.of(defaultRole);   
        }

        User userToSave = new User();
        userToSave.setName(registrationRequest.getName());
        userToSave.setEmail(registrationRequest.getEmail());
        userToSave.setPhoneNumber(registrationRequest.getPhoneNumber());
        userToSave.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userToSave.setRoles(userRoles);
        userToSave.setCreatedAt(LocalDateTime.now());
        userToSave.setUpdatedAt(LocalDateTime.now());
        userToSave.setProvider(AuthMethod.LOCAL);
        userToSave.setActive(true);

        User savedUser = userRepo.save(userToSave);

        emailNotificationService.sendWelcomeEmail(savedUser);


        return Response.builder()
            .statusCode(HttpStatus.OK.value())
            .message("user registered successfully")
            .build();
    }

    @Override
    public Response<LoginResponse> login(LoginRequest loginRequest) {
        log.info("Inside login®");
        User user = userRepo.findByEmail(loginRequest.getEmail())
            .orElseThrow(()-> new NotFoundException("Email Not Found"));
        if(!user.isActive()){
            throw new NotFoundException("Acount Not Active, Please reach Out to Customer Care...");
        }
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new BadRequestException("Invalid Password");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        List<String > roleNames = user.getRoles().stream()
        .map(Role::getName).toList();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setRoles(roleNames);


        return Response.<LoginResponse>builder()
            .statusCode(HttpStatus.OK.value())
            .message ("Login Successful")
            .data(loginResponse)
            .build();
    }

}
