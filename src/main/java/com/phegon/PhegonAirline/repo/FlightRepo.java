package com.phegon.PhegonAirline.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phegon.PhegonAirline.entities.Flight;
import com.phegon.PhegonAirline.enums.FlightStatus;

public interface FlightRepo extends JpaRepository<Flight,Long>{
    boolean existsByFlightNumber(String flightNumber);

    List<Flight>findByDepartureAirportIataCodeAndArrivalAirportIataCodeAndStatusAndDepartureTimeBetween(
        String departureIataCode, String arrivalIataCode, FlightStatus status, LocalDateTime startOfDay, LocalDateTime endOfDay
    );
}
