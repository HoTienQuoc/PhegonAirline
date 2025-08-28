package com.phegon.PhegonAirline.services;

import java.util.List;

import com.phegon.PhegonAirline.dtos.BookingDto;
import com.phegon.PhegonAirline.dtos.CreateBookingRequest;
import com.phegon.PhegonAirline.dtos.Response;
import com.phegon.PhegonAirline.enums.BookingStatus;

public interface BookingService {
    Response<?> createBooking (CreateBookingRequest createBookingRequest);
    Response<BookingDto> getBookingById(Long id); 
    Response<List<BookingDto>> getAllBookings(); 
    Response<List<BookingDto>> getMyBookings();
    Response<?> updateBookingStatus(Long id, BookingStatus status); 
}
