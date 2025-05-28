package com.boardcamp.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boardcamp.api.Model.RentalsModel;

public interface RentalsRepository extends JpaRepository<RentalsModel,Long>{
    List<RentalsModel> findByGameIdAndReturnDateIsNull(Long gameId);
}
