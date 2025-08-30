package com.phegon.PhegonAirline.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phegon.PhegonAirline.dtos.CreateFlightRequest;
import com.phegon.PhegonAirline.dtos.FlightDto;
import com.phegon.PhegonAirline.dtos.Response;
import com.phegon.PhegonAirline.enums.City;
import com.phegon.PhegonAirline.enums.Country;
import com.phegon.PhegonAirline.enums.FlightStatus;
import com.phegon.PhegonAirline.services.FlightService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN','PILOT')")
    public ResponseEntity<Response<?>> createAirport(@Valid @RequestBody CreateFlightRequest createFlightRequest){
        return ResponseEntity.ok(flightService.createFlight(createFlightRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<FlightDto>> getFlightById(@PathVariable Long id){
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @GetMapping()    
    public ResponseEntity<Response<List<FlightDto>>> getAllFlights(){
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN','PILOT')")
    public ResponseEntity<Response<?>> updateAirport(@RequestBody CreateFlightRequest flightRequest){
        return ResponseEntity.ok(flightService.updateFlight(flightRequest));
    }

    @GetMapping("/search")    
    public ResponseEntity<Response<List<FlightDto>>> searchFlight(
        @RequestParam(required = true) String departureIataCode,
        @RequestParam(required = true) String arrivalIataCode,
        @RequestParam(required = false, defaultValue = "SCHEDULED") FlightStatus status,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate
    ){
        return ResponseEntity.ok(flightService.searchFlight(departureIataCode, arrivalIataCode, status, departureDate));
    }

    @GetMapping("/cities") 
    public ResponseEntity<Response<List<City>>> getAllCities(){
        return ResponseEntity.ok(flightService.getAllCities());
    }

    @GetMapping("/countries") 
    public ResponseEntity<Response<List<Country>>> getAllCountries(){
        return ResponseEntity.ok(flightService.getAllCountries());
    }


}
