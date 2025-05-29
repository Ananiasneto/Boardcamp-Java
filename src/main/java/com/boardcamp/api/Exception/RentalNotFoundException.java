package com.boardcamp.api.Exception;

public class RentalNotFoundException extends RuntimeException{
    public RentalNotFoundException(String message){
        super(message);
    }
    
}
