package com.boardcamp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boardcamp.api.Model.CustomersModel;

public interface CustomersRepository extends JpaRepository<CustomersModel,Long> {
    Boolean existsByCpf(String cpf);
    
} 