package com.phegon.PhegonAirline.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.phegon.PhegonAirline.dtos.BookingDto;
import com.phegon.PhegonAirline.dtos.CreateBookingRequest;
import com.phegon.PhegonAirline.dtos.Response;
import com.phegon.PhegonAirline.entities.Booking;
import com.phegon.PhegonAirline.entities.Flight;
import com.phegon.PhegonAirline.entities.Passenger;
import com.phegon.PhegonAirline.entities.User;
import com.phegon.PhegonAirline.enums.BookingStatus;
import com.phegon.PhegonAirline.enums.FlightStatus;
import com.phegon.PhegonAirline.exceptions.BadRequestException;
import com.phegon.PhegonAirline.exceptions.NotFoundException;
import com.phegon.PhegonAirline.repo.BookingRepo;
import com.phegon.PhegonAirline.repo.FlightRepo;
import com.phegon.PhegonAirline.repo.PassengerRepo;
import com.phegon.PhegonAirline.services.BookingService;
import com.phegon.PhegonAirline.services.EmailNotificationService;
import com.phegon.PhegonAirline.services.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    private final BookingRepo bookingRepo;
    private final UserService userService;
    private final FlightRepo flightRepo;
    private final PassengerRepo passengerRepo;
    private final ModelMapper modelMapper;
    private final EmailNotificationService emailNotificationService;

    @Override
    @Transactional
    public Response<?> createBooking(CreateBookingRequest createBookingRequest) {
        User user = userService.currentUser();
        Flight flight = flightRepo.findById(createBookingRequest.getFlightid())
            .orElseThrow(()-> new NotFoundException("Flight Not Found"));
        if (flight.getStatus() != FlightStatus.SCHEDULED){
            throw new BadRequestException("You can only book a flight that is scheduled");
        }
        Booking booking = new Booking();
        booking.setBookingReference(generateBookingReference());
        booking.setUser(user);
        booking.setFlight(flight);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus(BookingStatus.CONFIRMED);

        Booking savedBooking = bookingRepo.save(booking);

        if (createBookingRequest.getPassengers() != null && !createBookingRequest.getPassengers().isEmpty()){
            List<Passenger> passengers = createBookingRequest.getPassengers().stream()
                .map(passengerDTO ->{
                    Passenger passenger = modelMapper.map(passengerDTO, Passenger.class);
                    passenger.setBooking(savedBooking);
                    return passenger;
                }).toList();
            passengerRepo.saveAll(passengers);
            savedBooking.setPassengers(passengers);
        }

        //SEND EMAIL TICKER OUT
        emailNotificationService.sendBookingTickerEmail(savedBooking);

        return Response.builder()
            .statusCode(HttpStatus.OK.value())
            .message("Booking created successfully")
            .build();
    }

    private String generateBookingReference() {
        return UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }

    @Override
    public Response<BookingDto> getBookingById(Long id) {
        Booking booking = bookingRepo.findById(id)
            .orElseThrow(()-> new NotFoundException("Booking not found"));
        BookingDto bookingDTO = modelMapper. map (booking, BookingDto.class);
        bookingDTO.getFlight().setBookings(null);
        return Response.<BookingDto>builder()
            .statusCode(HttpStatus.OK.value())
            .message("Booking retreived successfully")
            .data(bookingDTO)
            .build();
    }


    @Override
    public Response<List<BookingDto>> getAllBookings() {
        List<Booking> allBookings = bookingRepo.findAll(Sort.by(Sort.Direction.DESC,"id"));
        List<BookingDto> bookings = allBookings.stream() 
            .map(booking->{
                BookingDto bookingDTO = modelMapper.map(booking, BookingDto.class);
                bookingDTO.getFlight().setBookings(null);
                return bookingDTO;
            }).toList();
        
        return Response.<List<BookingDto>>builder()
            .statusCode(HttpStatus.OK.value())
            .message(bookings.isEmpty()? "No Booking Found" : "Booking retreived successfully")            
            .data(bookings)
            .build();
    }

    
    @Override
    public Response<List<BookingDto>> getMyBookings() {
        User user = userService.currentUser();
        List<Booking> userBookings = bookingRepo.findByUserIdOrderByIdDesc(user.getId());
        List<BookingDto> bookings = userBookings.stream() 
            .map(booking->{
                BookingDto bookingDTO = modelMapper.map(booking, BookingDto.class);
                bookingDTO.getFlight().setBookings(null);
                return bookingDTO;
            }).toList();

        return Response.<List<BookingDto>>builder()
            .statusCode(HttpStatus.OK.value())
            .message(bookings.isEmpty()? "No Booking Found" : "Booking retreived successfully")            
            .data(bookings)
            .build();
    }

    @Override
    @Transactional
    public Response<?> updateBookingStatus(Long id, BookingStatus status) {
        Booking booking = bookingRepo.findById(id)
            .orElseThrow(()-> new NotFoundException("Booking Not Found"));
        booking.setStatus(status);
        bookingRepo.save(booking);

        return Response.builder()
            .statusCode(HttpStatus.OK.value())
            .message("Booking Updated Successfully")
            .build();  
    }

}
