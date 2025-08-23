package com.phegon.PhegonAirline.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phegon.PhegonAirline.entities.EmailNotification;

public interface EmailNotificationRepo extends JpaRepository<EmailNotification, Long>{
    
}
