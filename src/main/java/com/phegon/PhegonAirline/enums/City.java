package com.phegon.PhegonAirline.enums;

public enum City {
    //NIGERIA
    LAGOS(Country.NIGERIA),
    ABUJA(Country.NIGERIA),
    //USA
    MIAMI(Country.USA),
    DALLAS(Country.USA),
    //UK
    LONDON(Country.UK),
    LEEDS(Country.UK);


    private final Country country;
    
    City(Country country){
        this.country = country;
    }
}
