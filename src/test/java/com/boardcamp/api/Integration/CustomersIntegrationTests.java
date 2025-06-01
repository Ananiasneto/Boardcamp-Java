package com.boardcamp.api.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import com.boardcamp.api.Dto.CustomersDto;
import com.boardcamp.api.Model.CustomersModel;
import com.boardcamp.api.repository.CustomersRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CustomersIntegrationTests {

    @Autowired
    private CustomersRepository customersRepository;

   @Autowired
	private TestRestTemplate restTemplate;

   @AfterEach
   @BeforeEach
    public void cleanUpDatabase() {
    customersRepository.deleteAll();
    }
  
@Test
public void givenCustomers_whenGetCustomers_thenReturnArrayCustomersModel() {
    CustomersModel customer=new CustomersModel(null,"test","12345678910","12345678910");
    customersRepository.save(customer);

    ResponseEntity<CustomersModel[]> response = restTemplate.exchange(
        "/customers",
        HttpMethod.GET,
        null,
        CustomersModel[].class
    );
    
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
		assertEquals(1, customersRepository.count()); 


}
@Test
public void givenCustomerById_whenGetCustomerById_thenReturnCustomer() {
    CustomersModel customer=new CustomersModel(null,"test","12345678910","12345678910");
    CustomersModel newCustomer=customersRepository.save(customer);

    ResponseEntity<CustomersModel> response = restTemplate.exchange(
        "/customers/{id}",
        HttpMethod.GET,
        null,
        CustomersModel.class,
        newCustomer.getId()
    );

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
    assertEquals(newCustomer.getName(), response.getBody().getName());
	assertEquals(1, customersRepository.count()); 
}
@Test
public void givenCustomerByIdNotExist_whenGetCustomerById_thenThrowError() {
    CustomersModel customer=new CustomersModel(99999999999l,"test","12345678910","12345678910");
    ResponseEntity<String> response = restTemplate.exchange(
        "/customers/{id}",
        HttpMethod.GET,
        null,
        String.class,
        customer.getId()
    );

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
     assertEquals("usuario não encontrado", response.getBody()); 
	assertEquals(0, customersRepository.count()); 

}
@Test
public void givenCustomerByIdInvalid_whenGetCustomerById_thenThrowError() {
    ResponseEntity<String> response = restTemplate.exchange(
        "/customers/{id}",
        HttpMethod.GET,
        null,
        String.class,
        "w"
    );

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); 
	assertEquals(0, customersRepository.count()); 

}
@Test
public void givenCustomerBodyInvalid_whenPostCustomer_thenThrowError() {
    CustomersDto customer=new CustomersDto();

    HttpEntity<CustomersDto> body=new HttpEntity<>(customer);

    ResponseEntity<String> response = restTemplate.exchange(
        "/customers",
        HttpMethod.POST,
        body,
        String.class
    );

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); 
	assertEquals(0, customersRepository.count()); 

}
@Test
public void givenCustomerCpfExist_whenPostCustomer_thenThrowError() {
    CustomersModel customer=new CustomersModel(null,"test","12345678910","12345678910");
    CustomersDto customerDto=new CustomersDto("test2","10345678910","12345678910");
   customersRepository.save(customer);

    HttpEntity<CustomersDto> body=new HttpEntity<>(customerDto);

    ResponseEntity<String> response = restTemplate.exchange(
        "/customers",
        HttpMethod.POST,
        body,
        String.class
    );

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode()); 
    assertEquals("usuario já cadastrado", response.getBody());
	assertEquals(1, customersRepository.count()); 

}
@Test
public void givenCustomerSucess_whenPostCustomer_thenReturnCustomer() {
    CustomersDto customerDto=new CustomersDto("test2","10345678910","12345678910");

    HttpEntity<CustomersDto> body=new HttpEntity<>(customerDto);

    ResponseEntity<CustomersModel> response = restTemplate.exchange(
        "/customers",
        HttpMethod.POST,
        body,
        CustomersModel.class
    );

    assertEquals(HttpStatus.CREATED, response.getStatusCode()); 
    assertEquals("test2",response.getBody().getName());
	assertEquals(1, customersRepository.count()); 

}

}
