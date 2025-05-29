package com.boardcamp.api.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;


import com.boardcamp.api.Dto.RentalsDto;
import com.boardcamp.api.Exception.CustomerNotFoundException;
import com.boardcamp.api.Exception.GameNotFoundException;
import com.boardcamp.api.Exception.GameStockUnprocesableEntityException;
import com.boardcamp.api.Exception.RentalNotFoundException;
import com.boardcamp.api.Exception.RentalReturnDateNotNullException;
import com.boardcamp.api.Exception.RentalReturnDateNullException;
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
        .orElseThrow(() -> new CustomerNotFoundException("cliente não encontrado"));

    GamesModel game = gamesRepository.findById(dto.getGameId())
        .orElseThrow(() -> new GameNotFoundException("jogo não encontrado"));

        RentalsModel rental=new RentalsModel(dto,customer,game);

         List<RentalsModel> gamesRental=rentalsRepository.findByGameIdAndReturnDateIsNull(rental.getGame().getId());

        if(rental.getGame().getStockTotal()<=gamesRental.size()){
        throw new GameStockUnprocesableEntityException ("não tem mais esse jogo no estoque");
        }
        
        return rentalsRepository.save(rental);
    }

     public RentalsModel putRental(Long id) {
    RentalsModel rental = rentalsRepository.findById(id)
        .orElseThrow(() -> new RentalNotFoundException("aluguel não encontrado"));

        if(rental.getReturnDate()!=null){
            throw new RentalReturnDateNotNullException("aluguel já finalizado");
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
 public void deletRental(Long id) {
    RentalsModel rental = rentalsRepository.findById(id)
        .orElseThrow(() -> new RentalNotFoundException("aluguel não encontrado"));

        if(rental.getReturnDate()==null){
            throw new RentalReturnDateNullException("aluguel não está finalizado");
        }
        rentalsRepository.delete(rental);
    }

}
