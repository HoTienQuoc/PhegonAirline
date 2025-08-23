package com.phegon.PhegonAirline.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phegon.PhegonAirline.entities.Passenger;

public interface PassengerRepo extends JpaRepository<Passenger,Long>{
    
}
