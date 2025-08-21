package com.phegon.PhegonAirline.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phegon.PhegonAirline.entities.Role;
import com.phegon.PhegonAirline.enums.AuthMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String name;

    private String email;

    /// / Write-only: Included when receiving data, excluded when sending data
    /// //Only used for writing (deserialization), ignored during reading (serialization)
    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private boolean emailVerified;

    //for oauth 2 authentication
    private AuthMethod provider;

    private String providerId;

    private List<Role> roles;

    private boolean active;

    private List<BookingDto> bookings;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

