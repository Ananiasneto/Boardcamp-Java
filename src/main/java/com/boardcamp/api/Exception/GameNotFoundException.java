package com.boardcamp.api.Exception;

public class GameNotFoundException extends RuntimeException{
    
    public GameNotFoundException(String message){
        super(message);
    }
    
}
