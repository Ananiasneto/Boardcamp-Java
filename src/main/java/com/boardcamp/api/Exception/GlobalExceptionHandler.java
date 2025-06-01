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

    @ExceptionHandler({GameNotFoundException.class})
    public ResponseEntity<Object> gameNotFoundException (GameNotFoundException gameNotFoundException){

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameNotFoundException.getMessage());
    }

    @ExceptionHandler({GameStockUnprocesableEntityException.class})
    public ResponseEntity<Object> gameStockUnprocesableEntityException (GameStockUnprocesableEntityException gameStockUnprocesableEntityException){

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(gameStockUnprocesableEntityException .getMessage());
    }
    @ExceptionHandler({RentalNotFoundException.class})
    public ResponseEntity<Object> rentalNotFoundException(RentalNotFoundException rentalNotFoundException){
        
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rentalNotFoundException.getMessage());
    }
    
    @ExceptionHandler({RentalReturnDateNotNullException.class})
    public ResponseEntity<Object> rentalReturnDateNotNullException(RentalReturnDateNotNullException rentalReturnDateNotNullException){
        
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(rentalReturnDateNotNullException.getMessage());
    }
    @ExceptionHandler({RentalReturnDateNullException.class})
    public ResponseEntity<Object> rentalReturnDateNullException(RentalReturnDateNullException rentalReturnDateNullException){
        
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rentalReturnDateNullException.getMessage());
    }
}
