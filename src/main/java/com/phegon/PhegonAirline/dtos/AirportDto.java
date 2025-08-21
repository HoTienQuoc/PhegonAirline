package com.phegon.PhegonAirline.dtos;

import com.phegon.PhegonAirline.enums.City;
import com.phegon.PhegonAirline.enums.Country;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirportDto {
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotEmpty(message = "City is required")
    private City city;

    @NotEmpty(message = "Country is required")
    private Country country;

    @NotBlank(message = "IataCode is required")
    private String iataCode;
}
