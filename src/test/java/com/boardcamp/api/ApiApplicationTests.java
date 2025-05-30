package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.Dto.GamesDto;
import com.boardcamp.api.Exception.GameTittleConflictException;
import com.boardcamp.api.Model.GamesModel;
import com.boardcamp.api.Service.GamesService;
import com.boardcamp.api.repository.GamesRepository;

@SpringBootTest
class ApiApplicationTests {

	@InjectMocks
	private GamesService gamesService;

	@Mock
	private GamesRepository gamesRepository;

	@Test
	void givenGameRepeated_whenGameCreating_thenThrowError (){
		GamesDto gamesDto=new GamesDto("Test","Test",1,2);
		doReturn(true).when(gamesRepository).existsByName(any());

		GameTittleConflictException gameTittleConflictException=assertThrows(GameTittleConflictException.class,()-> gamesService.insertGames(gamesDto));
		
		verify(gamesRepository,times(1)).existsByName(any());
		verify(gamesRepository,times(0)).save(any());
		assertEquals("esse jogo já está cadastrado", gameTittleConflictException.getMessage());
	}
	@Test
	void givenGameInsert_whenGameCreating_thenGameCreated (){
		GamesDto gamesDto=new GamesDto("Test","Test",1,2);
		GamesModel gamesModel=new GamesModel(gamesDto);
		doReturn(false).when(gamesRepository).existsByName(any());
		doReturn(gamesModel).when(gamesRepository).save(any());

		GamesModel result=gamesService.insertGames(gamesDto);
		
		verify(gamesRepository,times(1)).existsByName(any());
		verify(gamesRepository,times(1)).save(any());
		assertEquals(gamesModel, result);
	}

	@Test
	void contextLoads() {
	}

}
