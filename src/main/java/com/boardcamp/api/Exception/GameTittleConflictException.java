package com.boardcamp.api.Exception;

public class GameTittleConflictException extends RuntimeException {
    
   public GameTittleConflictException(String message){
        super(message);
    }
}
