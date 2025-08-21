package com.phegon.PhegonAirline.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.phegon.PhegonAirline.entities.Airport;
import com.phegon.PhegonAirline.entities.User;
import com.phegon.PhegonAirline.enums.FlightStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto {
    private Long id;

    private String flightNumber;

    private FlightStatus status;

    private AirportDto departureAirport;

    private AirportDto arrivalAirport;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private BigDecimal basePrice;

    private UserDto assignedPilot;

    private List<BookingDto> bookings; 

    private String departureAirportIataCode;

    private String arrivalAirportIataCode;

}
