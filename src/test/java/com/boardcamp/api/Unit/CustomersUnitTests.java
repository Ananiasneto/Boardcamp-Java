package com.boardcamp.api.Unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.Dto.CustomersDto;
import com.boardcamp.api.Exception.CustomerCpfConflictException;
import com.boardcamp.api.Exception.CustomerNotFoundException;
import com.boardcamp.api.Model.CustomersModel;
import com.boardcamp.api.Service.CustomersService;

import com.boardcamp.api.repository.CustomersRepository;

@SpringBootTest
public class CustomersUnitTests {
    
	@InjectMocks
	private CustomersService customersService;

	@Mock
	private CustomersRepository customersRepository;

	@Test
	void givenCustomerNotFound_whenGetById_thenThrowError (){
		Long id=1L;
		doReturn(Optional.empty()).when(customersRepository).findById(any());
		 CustomerNotFoundException customerNotFoundException =assertThrows(CustomerNotFoundException.class,()-> customersService.findCustomerById(id));
		
		verify(customersRepository,times(1)).findById(any());
		assertEquals("usuario não encontrado", customerNotFoundException.getMessage());
	}
    @Test
	void givenCustomerExist_whenGetById_thenReturnCustomer (){
		Long id=1L;
        CustomersModel customersModel=new CustomersModel(id,"Test","Test","Test");
		doReturn(Optional.of(customersModel)).when(customersRepository).findById(any());
		 
        CustomersModel newCustomersModel=customersService.findCustomerById(id);

		
		verify(customersRepository,times(1)).findById(any());
	    assertNotNull(newCustomersModel);
        assertEquals(customersModel, newCustomersModel);
	}
    

     @Test
    void givenCpfExist_whenInsertCustomer_thenThrowError() {
        CustomersDto customersDto = new CustomersDto("test","test","test");
        doReturn(true).when(customersRepository).existsByCpf(customersDto.getCpf());

        CustomerCpfConflictException customerCpfConflictException=assertThrows(CustomerCpfConflictException.class, ()->customersService.insertCustomer(customersDto));

        verify(customersRepository, times(1)).existsByCpf(any());
        verify(customersRepository, times(0)).save(any());
        assertEquals("usuario já cadastrado", customerCpfConflictException.getMessage());
    }

    @Test
    void givenCpfNotExist_whenInsertCustomer_thenSaveCustomer() {
        CustomersDto customersDto = new CustomersDto("test","test","test");
        CustomersModel customersModel=new CustomersModel(customersDto);
        doReturn(false).when(customersRepository).existsByCpf(customersDto.getCpf());
        doReturn(customersModel).when(customersRepository).save(any());


        CustomersModel newCustomers=customersService.insertCustomer(customersDto);

        verify(customersRepository, times(1)).existsByCpf(any());
        verify(customersRepository, times(1)).save(any());
        assertNotNull(newCustomers);
        assertEquals(newCustomers, customersModel);
    }
}
