package com.boardcamp.api.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({GameTittleConflictException.class})
    public ResponseEntity<Object> gameTittleConflictException (GameTittleConflictException gameTittleConflictException){

    return ResponseEntity.status(HttpStatus.CONFLICT).body(gameTittleConflictException.getMessage());
        
    }
    
}
