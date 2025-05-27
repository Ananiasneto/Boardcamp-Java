package com.boardcamp.api.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.Dto.RentalsDto;
import com.boardcamp.api.Model.CustomersModel;
import com.boardcamp.api.Model.GamesModel;
import com.boardcamp.api.Model.RentalsModel;
import com.boardcamp.api.repository.CustomersRepository;
import com.boardcamp.api.repository.GamesRepository;
import com.boardcamp.api.repository.RentalsRepository;

@Service
public class RentalsService {
    private final RentalsRepository rentalsRepository;
    private final CustomersRepository customersRepository;
    private final GamesRepository gamesRepository;

    
    public RentalsService(RentalsRepository rentalsRepository,GamesRepository gamesRepository,CustomersRepository customersRepository){
        this.rentalsRepository=rentalsRepository;
        this.customersRepository=customersRepository;
        this.gamesRepository=gamesRepository;
    }

    public List<RentalsModel> getAllRentals(){
        return rentalsRepository.findAll();
    }
     
    public RentalsModel getRental(RentalsDto dto){
        CustomersModel customer = customersRepository.findById(dto.getCustomerId())
        .orElseThrow(() -> new RuntimeException("cliente não encontrado"));

    GamesModel game = gamesRepository.findById(dto.getGameId())
        .orElseThrow(() -> new RuntimeException("jogo não encontrado"));

        RentalsModel rental=new RentalsModel(dto,customer,game);
        return rentalsRepository.save(rental);
    }
}
