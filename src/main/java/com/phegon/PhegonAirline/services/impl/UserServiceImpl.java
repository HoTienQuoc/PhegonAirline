package com.phegon.PhegonAirline.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.phegon.PhegonAirline.dtos.Response;
import com.phegon.PhegonAirline.dtos.UserDto;
import com.phegon.PhegonAirline.entities.User;
import com.phegon.PhegonAirline.exceptions.NotFoundException;
import com.phegon.PhegonAirline.repo.UserRepo;
import com.phegon.PhegonAirline.services.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;



    @Override
    public User currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByEmail(email).orElseThrow(()-> new NotFoundException("User Not Found"));
    }

    @Override
    @Transactional
    public Response<?> updateMyAccount(UserDto userDTO) {
        log.info("Inside updateMyAccount()");

        log.info(String.valueOf(userDTO));

        User user = currentUser();

        if (userDTO.getName() != null && !userDTO.getName().isBlank()){
            user.setName(userDTO.getName());
        }

        if (userDTO.getPhoneNumber() != null && !userDTO.getPhoneNumber().isBlank()){
            user.setPhoneNumber(userDTO.getPhoneNumber());
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()){
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            user.setPassword(encodedPassword);
        }

        user.setUpdatedAt(LocalDateTime.now());

        // User savedUser = userRepo.save(user);
        userRepo.save(user);

        return Response.builder()
            .statusCode(HttpStatus.OK.value())
            .message("Acount Updated Successfully")
            // .data(savedUser)
            .build();
    }

    @Override
    public Response<List<UserDto>> getAllPilots() {
        log.info("Inside getAllPilots");
        List<UserDto> pilots = userRepo.findByRoleName ("PILOT").stream()
            .map(user -> modelMapper.map(user, UserDto.class))
            .toList();
        
        return Response.<List<UserDto>>builder().statusCode(HttpStatus.OK.value())
            .message(pilots.isEmpty() ? "No pilots Found" : "pilots retrieved succesfulLy")
            .data(pilots)
            .build();
    }

    @Override
    public Response<UserDto> getAccountDetails() {
        log.info("Inside getAccountDetails()");
        User user = currentUser();
        UserDto userDTO = modelMapper.map(user,UserDto.class);

        return Response.<UserDto>builder().statusCode(HttpStatus.OK.value())
            .message("success")
            .data(userDTO)
            .build();
    }
}
