package com.phegon.PhegonAirline.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String ex){
        super(ex);
    }
}
