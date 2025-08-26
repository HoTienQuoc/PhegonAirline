package com.phegon.PhegonAirline.services;

import com.phegon.PhegonAirline.entities.Booking;
import com.phegon.PhegonAirline.entities.User;

public interface EmailNotificationService {
    void sendBookingTickerEmail(Booking booking);

    void sendWelcomeEmail(User user);
}
