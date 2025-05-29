package com.boardcamp.api.Exception;

public class RentalReturnDateNotNullException extends RuntimeException{
    public RentalReturnDateNotNullException(String message){
        super(message);
    }
}
