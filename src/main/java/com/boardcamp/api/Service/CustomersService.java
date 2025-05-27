package com.boardcamp.api.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.Dto.CustomersDto;
import com.boardcamp.api.Model.CustomersModel;
import com.boardcamp.api.repository.CustomersRepository;

@Service
public class CustomersService {
    private final CustomersRepository customersRepository;
    public CustomersService(CustomersRepository customersRepository){
        this.customersRepository=customersRepository;
    }
    public List<CustomersModel> findAllCustomers() {
    return customersRepository.findAll();
    }
    public CustomersModel insertCustomer(CustomersDto body) {
    CustomersModel customer=new CustomersModel(body);
    return customersRepository.save(customer);
    }
}
