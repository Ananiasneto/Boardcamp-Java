package com.boardcamp.api.Exception;

public class CustomerNotFoundException extends RuntimeException{
    
    public CustomerNotFoundException(String message){
        super(message);
    }
    
}
