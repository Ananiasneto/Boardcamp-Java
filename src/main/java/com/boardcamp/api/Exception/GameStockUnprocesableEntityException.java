package com.boardcamp.api.Exception;

public class GameStockUnprocesableEntityException extends RuntimeException {
    
   public GameStockUnprocesableEntityException(String message){
        super(message);
    }
}
