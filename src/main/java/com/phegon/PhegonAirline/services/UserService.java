package com.phegon.PhegonAirline.services;

import java.util.List;

import com.phegon.PhegonAirline.dtos.Response;
import com.phegon.PhegonAirline.dtos.UserDto;
import com.phegon.PhegonAirline.entities.User;

public interface UserService {
    User currentUser();

    Response<?> updateMyAccount (UserDto userDTO);
    
    Response<List<UserDto>> getAllPilots();

    Response<UserDto> getAccountDetails();
}
