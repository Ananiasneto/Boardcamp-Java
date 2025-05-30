package com.boardcamp.api.Unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.Dto.RentalsDto;
import com.boardcamp.api.Exception.CustomerNotFoundException;
import com.boardcamp.api.Exception.GameNotFoundException;
import com.boardcamp.api.Exception.GameStockUnprocesableEntityException;
import com.boardcamp.api.Exception.RentalNotFoundException;
import com.boardcamp.api.Exception.RentalReturnDateNotNullException;
import com.boardcamp.api.Model.CustomersModel;
import com.boardcamp.api.Model.GamesModel;
import com.boardcamp.api.Model.RentalsModel;
import com.boardcamp.api.Service.RentalsService;
import com.boardcamp.api.repository.CustomersRepository;
import com.boardcamp.api.repository.GamesRepository;
import com.boardcamp.api.repository.RentalsRepository;

@SpringBootTest
public class RentalsUnitTests {

    @InjectMocks
    private RentalsService rentalsService;

    @Mock
    private RentalsRepository rentalsRepository;

	@Mock
	private CustomersRepository customersRepository;

	@Mock
	private GamesRepository gamesRepository;

    @Test
    void givenCustomerNotFound_WhenInsertRental_ThenThrowError(){
        RentalsDto rentalsDto=new RentalsDto(1L,1L,3);
        doReturn(Optional.empty()).when(customersRepository).findById(any());

        CustomerNotFoundException customerNotFoundException=assertThrows(CustomerNotFoundException.class, ()->rentalsService.insertRental(rentalsDto));

        verify(customersRepository,times(1)).findById(any());

        verify(gamesRepository,times(0)).findById(any());
         verify(rentalsRepository,times(0)).findByGameIdAndReturnDateIsNull(any());
          verify(rentalsRepository,times(0)).save(any());

        assertEquals("cliente não encontrado", customerNotFoundException.getMessage());
    }

      @Test
    void givenGameNotFound_WhenInsertRental_ThenThrowError(){
        RentalsDto rentalsDto=new RentalsDto(1L,1L,3);
        CustomersModel customer=new CustomersModel(1L,"test","test","test");
        doReturn(Optional.of(customer)).when(customersRepository).findById(any());
        doReturn(Optional.empty()).when(gamesRepository).findById(any());

        GameNotFoundException gameNotFoundException=assertThrows(GameNotFoundException.class, ()->rentalsService.insertRental(rentalsDto));

        verify(customersRepository,times(1)).findById(any());

        verify(gamesRepository,times(1)).findById(any());
         verify(rentalsRepository,times(0)).findByGameIdAndReturnDateIsNull(any());
          verify(rentalsRepository,times(0)).save(any());

        assertEquals("jogo não encontrado", gameNotFoundException.getMessage());
    }
      @Test
    void givenGameNotStock_WhenInsertRental_ThenThrowError(){
        RentalsDto rentalsDto=new RentalsDto(1L,1L,3);
        CustomersModel customer=new CustomersModel(1L,"test","test","test");
        GamesModel game=new GamesModel(1L,"test","test",1,2);
          List<RentalsModel> gamesRental=List.of(new RentalsModel());

        doReturn(Optional.of(customer)).when(customersRepository).findById(any());
        doReturn(Optional.of(game)).when(gamesRepository).findById(any());
        doReturn(gamesRental).when(rentalsRepository).findByGameIdAndReturnDateIsNull(any());


        GameStockUnprocesableEntityException gameStockUnprocesableEntityException=assertThrows(GameStockUnprocesableEntityException.class, ()->rentalsService.insertRental(rentalsDto));

        verify(customersRepository,times(1)).findById(any());

        verify(gamesRepository,times(1)).findById(any());
         verify(rentalsRepository,times(1)).findByGameIdAndReturnDateIsNull(any());
          verify(rentalsRepository,times(0)).save(any());
        assertEquals("não tem mais esse jogo no estoque", gameStockUnprocesableEntityException.getMessage());
    }

     @Test
    void givenInsertRentalSucess_WhenInsertRental_ThenReturnRental(){
        RentalsDto rentalsDto=new RentalsDto(1L,1L,3);
        CustomersModel customer=new CustomersModel(1L,"test","test","test");
        GamesModel game=new GamesModel(1L,"test","test",2,2);
        RentalsModel rentalsModel=new RentalsModel(rentalsDto,customer,game);
          List<RentalsModel> gamesRental=List.of(new RentalsModel());

        doReturn(Optional.of(customer)).when(customersRepository).findById(any());
        doReturn(Optional.of(game)).when(gamesRepository).findById(any());
        doReturn(gamesRental).when(rentalsRepository).findByGameIdAndReturnDateIsNull(any());
        doReturn(rentalsModel).when(rentalsRepository).save(any());


        RentalsModel result=rentalsService.insertRental(rentalsDto);

        verify(customersRepository,times(1)).findById(any());

        verify(gamesRepository,times(1)).findById(any());
         verify(rentalsRepository,times(1)).findByGameIdAndReturnDateIsNull(any());
          verify(rentalsRepository,times(1)).save(any());
        assertEquals(rentalsModel, result);

    }

    @Test
    void givenRentalNotFound_WhenPutRental_ThenThrowError(){
        doReturn(Optional.empty()).when(rentalsRepository).findById(any());

        RentalNotFoundException rentalNotFoundException=assertThrows(RentalNotFoundException.class,()->rentalsService.putRental(any()));


        verify(rentalsRepository,times(1)).findById(any());

        verify(rentalsRepository,times(0)).save(any());

        assertEquals("aluguel não encontrado", rentalNotFoundException.getMessage());
    }

     @Test
    void givenRentalIsFinish_WhenPutRental_ThenThrowError(){
        RentalsModel rental=new RentalsModel();
        rental.setReturnDate(LocalDate.now());
        doReturn(Optional.of(rental)).when(rentalsRepository).findById(any());


        RentalReturnDateNotNullException rentalReturnDateNotNullException=assertThrows(RentalReturnDateNotNullException.class,()->rentalsService.putRental(any()));


        verify(rentalsRepository,times(1)).findById(any());

        verify(rentalsRepository,times(0)).save(any());

        assertEquals("aluguel já finalizado", rentalReturnDateNotNullException.getMessage());
    }
 @Test
    void givenRentalInsert_WhenPutRental_ThenReturnRental(){
        CustomersModel customer=new CustomersModel(1L,"test","test","test");

        GamesModel game =new GamesModel (1L,"test","test",1,2);

        RentalsDto rentalDto=new RentalsDto(1L,1l,1);
        RentalsModel rental=new RentalsModel(rentalDto,customer,game);

        doReturn(Optional.of(rental)).when(rentalsRepository).findById(any());
        rentalsService.putRental(1L);


        verify(rentalsRepository,times(1)).findById(any());
        verify(rentalsRepository,times(1)).save(rental);
    }

}
