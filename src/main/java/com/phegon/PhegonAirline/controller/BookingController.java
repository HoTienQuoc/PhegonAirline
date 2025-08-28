package com.phegon.PhegonAirline.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phegon.PhegonAirline.dtos.BookingDto;
import com.phegon.PhegonAirline.dtos.CreateBookingRequest;
import com.phegon.PhegonAirline.dtos.Response;
import com.phegon.PhegonAirline.enums.BookingStatus;
import com.phegon.PhegonAirline.services.BookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping()
    public ResponseEntity<Response<?>> createBooking(@Valid @RequestBody CreateBookingRequest createBookingRequest){
        return ResponseEntity.ok(bookingService.createBooking(createBookingRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<BookingDto>> getBookingById(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PILOT')")
    public ResponseEntity<Response<List<BookingDto>>> getAllBookings(){
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/me")
    public ResponseEntity<Response<List<BookingDto>>> getMyBookings(){
        return ResponseEntity.ok(bookingService.getMyBookings());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateBookingStatus(@PathVariable Long id, @RequestBody BookingStatus bookingStatus){
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, bookingStatus));
    }
    

}
