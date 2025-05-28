package com.boardcamp.api.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.boardcamp.api.Dto.CustomersDto;
import com.boardcamp.api.Exception.CustomerCpfConflictException;
import com.boardcamp.api.Exception.CustomerNotFoundException;
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
    public CustomersModel findCustomerById(Long id) {
        Optional<CustomersModel> customer=customersRepository.findById(id);
        customer.orElseThrow(()->new CustomerNotFoundException("usuario não encontrado"));
        return customer.get();
    }
    public CustomersModel insertCustomer(CustomersDto body) {
    Boolean customerCpf=customersRepository.existsByCpf(body.getCpf());
    if(customerCpf){
        throw new CustomerCpfConflictException("usuario já cadastrado");
    }
        CustomersModel customer=new CustomersModel(body);
        return customersRepository.save(customer);
    

}
}
