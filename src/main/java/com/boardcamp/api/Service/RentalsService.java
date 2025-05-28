package com.boardcamp.api.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
     
    public RentalsModel insertRental(RentalsDto dto){
        CustomersModel customer = customersRepository.findById(dto.getCustomerId())
        .orElseThrow(() -> new RuntimeException("cliente não encontrado"));

    GamesModel game = gamesRepository.findById(dto.getGameId())
        .orElseThrow(() -> new RuntimeException("jogo não encontrado"));

        RentalsModel rental=new RentalsModel(dto,customer,game);

         List<RentalsModel> gamesRental=rentalsRepository.findByGameIdAndReturnDateIsNull(rental.getGame().getId());

        if(rental.getGame().getStockTotal()<=gamesRental.size()){
        throw new RuntimeException("não tem mais esse jogo no estoque desse jogo");
        }
        
        return rentalsRepository.save(rental);
    }

     public RentalsModel putRental(Long id) {
    RentalsModel rental = rentalsRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("aluguel não encontrado"));
    List<RentalsModel> gamesRental=rentalsRepository.findByGameIdAndReturnDateIsNull(rental.getGame().getId());
    if(rental.getGame().getStockTotal()<=gamesRental.size()){
       throw new RuntimeException("não tem mais esse jogo no estoque desse jogo");
    }

     LocalDate returnDate = LocalDate.now();
    rental.setReturnDate(returnDate);

    LocalDate expectedReturnDate = rental.getRentDate().plusDays(rental.getDaysRented());

    if (returnDate.isAfter(expectedReturnDate)) {
        long diasDeAtraso = ChronoUnit.DAYS.between(expectedReturnDate, returnDate);
        int valor=rental.getGame().getPricePerDay();
        int multa = valor *(int) diasDeAtraso;
        rental.setDelayFee(multa);
    } else {
        rental.setDelayFee(0);
    }

        return rentalsRepository.save(rental);
}

}
