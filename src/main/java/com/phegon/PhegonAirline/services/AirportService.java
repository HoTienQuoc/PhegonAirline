package com.phegon.PhegonAirline.services;

import java.util.List;

import com.phegon.PhegonAirline.dtos.AirportDto;
import com.phegon.PhegonAirline.dtos.Response;

public interface AirportService {
    Response<?> createAirport(AirportDto airportDTO);

    Response<?> updateAirport(AirportDto airportDTO); 

    Response<List<AirportDto>> getAllAirports();

    Response<AirportDto> getAirportById(Long id);
}
