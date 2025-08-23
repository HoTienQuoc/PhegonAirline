package com.phegon.PhegonAirline.dtos;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingRequest {
    @NotNull(message = "FLight ID cannot be null")
    private Long flightid;

    @NotEmpty(message = "At least one passenger must be provided")
    private List<PassengerDto> passengers;
}
