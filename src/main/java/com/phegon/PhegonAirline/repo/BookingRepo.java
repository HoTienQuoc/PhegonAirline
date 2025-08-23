package com.phegon.PhegonAirline.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phegon.PhegonAirline.entities.Booking;

public interface BookingRepo extends JpaRepository<Booking,Long>{
    List<Booking> findByUserIdOrderByIdDesc(Long userId);
}
