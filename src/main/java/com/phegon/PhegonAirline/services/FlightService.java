package com.phegon.PhegonAirline.services;

import java.time.LocalDate;
import java.util.List;

import com.phegon.PhegonAirline.dtos.CreateFlightRequest;
import com.phegon.PhegonAirline.dtos.FlightDto;
import com.phegon.PhegonAirline.dtos.Response;
import com.phegon.PhegonAirline.enums.City;
import com.phegon.PhegonAirline.enums.Country;
import com.phegon.PhegonAirline.enums.FlightStatus;

public interface FlightService {
    Response<?> createFlight(CreateFlightRequest createFlightRequest);

    Response<FlightDto> getFlightById(Long id);

    Response<List<FlightDto>> getAllFlights();

    Response<?> updateFlight (CreateFlightRequest flightRequest);

    Response<List<FlightDto>> searchFlight(String departurePortIata, String arrivalPortIata, FlightStatus status, LocalDate departureDate);

    Response<List<City>> getAllCities();

    Response<List<Country>> getAllCountries();

}
