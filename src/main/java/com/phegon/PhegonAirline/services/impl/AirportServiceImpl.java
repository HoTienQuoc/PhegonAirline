package com.phegon.PhegonAirline.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.phegon.PhegonAirline.dtos.AirportDto;
import com.phegon.PhegonAirline.dtos.Response;
import com.phegon.PhegonAirline.entities.Airport;
import com.phegon.PhegonAirline.enums.City;
import com.phegon.PhegonAirline.enums.Country;
import com.phegon.PhegonAirline.exceptions.BadRequestException;
import com.phegon.PhegonAirline.exceptions.NotFoundException;
import com.phegon.PhegonAirline.repo.AirportRepo;
import com.phegon.PhegonAirline.services.AirportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService{

    private final AirportRepo airportRepo;
    private final ModelMapper modelMapper;
    
    @Override
    public Response<?> createAirport(AirportDto airportDTO) {
        log.info("Inside createAirport()");

        Country country = airportDTO.getCountry();
        City city = airportDTO.getCity();

        if(!city.getCountry().equals(country)){
            throw new BadRequestException("CITY does not belong to the country");
        }

        Airport airport = modelMapper.map(airportDTO,Airport.class);
        airportRepo.save(airport);

        return Response.builder()
            .statusCode(HttpStatus.OK.value())
            .message ("Airport Created SuccesfulLy")
            .build();
    }

    @Override
    public Response<?> updateAirport(AirportDto airportDTO) {
        Long id = airportDTO.getId();
        Airport existingAirport = airportRepo.findById(id)
            .orElseThrow(()-> new NotFoundException("Airport Not Found"));

        if(airportDTO.getCity() != null){
            if(!airportDTO.getCity().getCountry().equals(existingAirport.getCountry())){
                throw new BadRequestException("CITY does not belong to the country");
            }
            existingAirport.setCity(airportDTO.getCity());
        }

        if (airportDTO.getName() != null){
            existingAirport.setName(airportDTO.getName());
        }

        if (airportDTO.getIataCode() != null){
            existingAirport.setIataCode(airportDTO.getIataCode());
        }
        airportRepo.save(existingAirport);

        return Response.builder()
            .statusCode(HttpStatus.OK.value())
            .message("Airport updated Succesfully")
            .build();
    }

    @Override
    public Response<List<AirportDto>> getAllAirports() {
        List<AirportDto> airports = airportRepo.findAll().stream()
            .map(airport -> modelMapper.map(airport,AirportDto.class))
            .toList();
            return Response.<List<AirportDto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(airports.isEmpty() ? "No Airports Found": "Airports retrieved successfully")
                .data(airports)
                .build();
    }

    @Override
    public Response<AirportDto> getAirportById(Long id) {
        Airport airport = airportRepo.findById(id)
            .orElseThrow(()-> new NotFoundException("Airport Not Found"));
        
        AirportDto airportDTO = modelMapper.map(airport, AirportDto.class);

        return Response.<AirportDto>builder()
            .statusCode(HttpStatus.OK.value())
            .message ( "Airports retrieved successfully")
            .data(airportDTO)
            .build();
    }

    

    

}
