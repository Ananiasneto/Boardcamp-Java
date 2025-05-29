package com.boardcamp.api.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.Dto.RentalsDto;
import com.boardcamp.api.Model.RentalsModel;
import com.boardcamp.api.Service.RentalsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rentals")
public class RentalsController {
    private final RentalsService rentalsService;
    public RentalsController(RentalsService rentalsService){
        this.rentalsService=rentalsService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllRentals(){
        List<RentalsModel> rentals=rentalsService.getAllRentals();
        return ResponseEntity.status(HttpStatus.OK).body(rentals);
    }
      @PostMapping
    public ResponseEntity<Object> postRental(@RequestBody @Valid RentalsDto body){
        
        RentalsModel rental=rentalsService.insertRental(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(rental);
    }
    @PostMapping("/{id}/return")
    public ResponseEntity<Object> finalizaRental(@PathVariable Long id) {
        RentalsModel rental=rentalsService.putRental(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(rental);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletaRental(@PathVariable Long id) {
        rentalsService.deletRental(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
