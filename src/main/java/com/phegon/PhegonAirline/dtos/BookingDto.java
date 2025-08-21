package com.phegon.PhegonAirline.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.phegon.PhegonAirline.entities.Flight;
import com.phegon.PhegonAirline.entities.User;
import com.phegon.PhegonAirline.enums.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Long id;

    private String bookingRefrence;
    
    private User user;

    private Flight flight;

    private LocalDateTime bookingDate;

    private BookingStatus status;

    private List<PassengerDto> passengers;
}
