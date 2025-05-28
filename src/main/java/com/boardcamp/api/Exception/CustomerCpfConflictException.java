package com.boardcamp.api.Exception;

public class CustomerCpfConflictException extends RuntimeException{
    public CustomerCpfConflictException(String message){
        super(message);
    }
    
}
