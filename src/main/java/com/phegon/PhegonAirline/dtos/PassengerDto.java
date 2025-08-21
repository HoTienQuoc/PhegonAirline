package com.phegon.PhegonAirline.dtos;

import com.phegon.PhegonAirline.entities.Booking;
import com.phegon.PhegonAirline.enums.PassengerType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDto {
    private Long id;

    private Booking booking;

    private String firstName;

    private String lastName;

    private String passportNumber;

    private PassengerType type;

    private String seatNumber;

    private String specialRequests;

}
