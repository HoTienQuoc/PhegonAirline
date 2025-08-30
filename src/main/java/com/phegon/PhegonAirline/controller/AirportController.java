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

import com.phegon.PhegonAirline.dtos.AirportDto;
import com.phegon.PhegonAirline.dtos.Response;
import com.phegon.PhegonAirline.services.AirportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/airports")
@RequiredArgsConstructor
public class AirportController {
    private final AirportService airportService;

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<?>> createAirport(@Valid @RequestBody AirportDto airportDTO){
        return ResponseEntity.ok(airportService.createAirport(airportDTO));
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<?>> updateAirport(@RequestBody AirportDto airportDTO){
        return ResponseEntity.ok(airportService.updateAirport(airportDTO));
    }

    @GetMapping()
    public ResponseEntity<Response<List<AirportDto>>> getAllAirports(){
        return ResponseEntity.ok(airportService.getAllAirports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<AirportDto>> getAirportById(@PathVariable Long id){
        return ResponseEntity.ok(airportService.getAirportById(id));
    }

}
