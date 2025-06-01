package com.boardcamp.api.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.boardcamp.api.Dto.RentalsDto;
import com.boardcamp.api.Model.CustomersModel;
import com.boardcamp.api.Model.GamesModel;
import com.boardcamp.api.Model.RentalsModel;
import com.boardcamp.api.repository.CustomersRepository;
import com.boardcamp.api.repository.GamesRepository;
import com.boardcamp.api.repository.RentalsRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RentalsIntegrationsTests {

    @Autowired
    private  RentalsRepository rentalsRepository;
    @Autowired
    private  CustomersRepository customersRepository;
    @Autowired
    private  GamesRepository gamesRepository;
    @Autowired
	private TestRestTemplate restTemplate;

   @AfterEach
   @BeforeEach
    public void cleanUpDatabase() {
    rentalsRepository.deleteAll();
    customersRepository.deleteAll();
    gamesRepository.deleteAll();
    
    }

    @Test
    void givenRental_whenGetRentals_thenReturnArrayRentals(){
        CustomersModel customer=new CustomersModel(null,"test","12345678910","12345678910");
        GamesModel game = new GamesModel(null, "test", "test", 3, 3000);
        gamesRepository.save(game);
        customersRepository.save(customer);
        RentalsModel rental = new RentalsModel(null,LocalDate.now(),3,
null,300,null,customer,game
        );
        rentalsRepository.save(rental);

    ResponseEntity<RentalsModel[]> response = restTemplate.exchange(
        "/rentals",
        HttpMethod.GET,
        null,
        RentalsModel[].class
    );
    
        assertEquals(HttpStatus.OK, response.getStatusCode()); 
		assertEquals(1, rentalsRepository.count()); 

    }
    @Test
    void givenRentalInvalid_whenPostRentals_thenThrowError(){

    RentalsDto rentaldto=new RentalsDto(null,null,3);
    HttpEntity<RentalsDto> body = new HttpEntity<>(rentaldto);

    ResponseEntity<String> response = restTemplate.exchange(
        "/rentals",
        HttpMethod.POST,
        body,
        String.class
    );
    
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); 
		assertEquals(0, rentalsRepository.count()); 

    }
    @Test
    void givenRentalIdCustomerNotFound_whenPostRentals_thenThrowError(){
    GamesModel game = new GamesModel(null, "test", "test", 3, 3000);
    gamesRepository.save(game);
    RentalsDto rentaldto=new RentalsDto(99999999999l,game.getId(),3);

    HttpEntity<RentalsDto> body = new HttpEntity<>(rentaldto);
    ResponseEntity<String> response = restTemplate.exchange(
        "/rentals",
        HttpMethod.POST,
        body,
        String.class
    );
    
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
        assertEquals("cliente não encontrado", response.getBody());
		assertEquals(0, rentalsRepository.count()); 

    }
    @Test
    void givenRentalIdGameNotFound_whenPostRentals_thenThrowError(){
    CustomersModel customer=new CustomersModel(null,"test","12345678910","12345678910");
    customersRepository.save(customer);
    RentalsDto rentaldto=new RentalsDto(customer.getId(),99999999999l,3);

    HttpEntity<RentalsDto> body = new HttpEntity<>(rentaldto);
    ResponseEntity<String> response = restTemplate.exchange(
        "/rentals",
        HttpMethod.POST,
        body,
        String.class
    );
    
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
        assertEquals("jogo não encontrado", response.getBody());
		assertEquals(0, rentalsRepository.count()); 

    }
    @Test
    void givenRentalGameNotStock_whenPostRentals_thenThrowError(){
    CustomersModel customer=new CustomersModel(null,"test","12345678910","12345678910");
    GamesModel game = new GamesModel(null, "test", "test", 1, 3000);
    gamesRepository.save(game);
    customersRepository.save(customer);
    RentalsDto rentaldto=new RentalsDto(customer.getId(),game.getId(),3);
    RentalsModel rental=new RentalsModel(rentaldto,customer,game);
    rentalsRepository.save(rental);

    HttpEntity<RentalsDto> body = new HttpEntity<>(rentaldto);
    ResponseEntity<String> response = restTemplate.exchange(
        "/rentals",
        HttpMethod.POST,
        body,
        String.class
    );
    
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode()); 
        assertEquals("não tem mais esse jogo no estoque", response.getBody());
		assertEquals(1, rentalsRepository.count()); 

    }
    @Test
    void givenRentalsucess_whenPostRentals_thenReturnRental(){
    CustomersModel customer=new CustomersModel(null,"test","12345678910","12345678910");
    GamesModel game = new GamesModel(null, "test", "test", 1, 3000);
    GamesModel gameId=gamesRepository.save(game);
     CustomersModel customerId=customersRepository.save(customer);
    RentalsDto rentaldto=new RentalsDto(customer.getId(),game.getId(),3);

    HttpEntity<RentalsDto> body = new HttpEntity<>(rentaldto);
    ResponseEntity<RentalsModel> response = restTemplate.exchange(
        "/rentals",
        HttpMethod.POST,
        body,
        RentalsModel.class
    );
    
        assertEquals(HttpStatus.CREATED, response.getStatusCode()); 
        assertEquals(gameId, response.getBody().getGame());
        assertEquals(customerId, response.getBody().getCustomers());
		assertEquals(1, rentalsRepository.count()); 

    }
}
