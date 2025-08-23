package com.phegon.PhegonAirline.dtos;

import com.phegon.PhegonAirline.enums.PassengerType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDto {
    private Long id;

    @NotBlank(message = "First Name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;

    private String passportNumber;

    @NotNull(message = "Type cannot be null")
    private PassengerType type;

    private String seatNumber;

    private String specialRequests;

}
