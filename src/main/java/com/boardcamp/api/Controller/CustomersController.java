package com.boardcamp.api.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.Dto.CustomersDto;
import com.boardcamp.api.Model.CustomersModel;
import com.boardcamp.api.Service.CustomersService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomersController {
    private final CustomersService customersService;
    public CustomersController(CustomersService customersService){
        this.customersService=customersService;
    }
    @GetMapping
    public ResponseEntity<Object> getCustomers(){
        List<CustomersModel> customers=customersService.findAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomersById(@PathVariable Long id){
        CustomersModel customer=customersService.findCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }
    @PostMapping
    public  ResponseEntity<Object> postCustomer(@RequestBody @Valid CustomersDto customer){
        CustomersModel customerSave=customersService.insertCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerSave);
}
}