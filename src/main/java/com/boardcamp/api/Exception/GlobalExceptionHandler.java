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
    @ExceptionHandler({CustomerCpfConflictException.class})
    public ResponseEntity<Object> customerCpfConflictException (CustomerCpfConflictException customerCpfConflictException){

    return ResponseEntity.status(HttpStatus.CONFLICT).body(customerCpfConflictException.getMessage());
    }
    @ExceptionHandler({CustomerNotFoundException.class})
    public ResponseEntity<Object> customerCpfConflictException (CustomerNotFoundException customerNotFoundException){

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customerNotFoundException.getMessage());
    }
}
