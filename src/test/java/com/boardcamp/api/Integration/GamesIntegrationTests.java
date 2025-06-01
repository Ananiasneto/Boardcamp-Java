package com.boardcamp.api.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.boardcamp.api.Dto.GamesDto;
import com.boardcamp.api.Model.GamesModel;
import com.boardcamp.api.repository.GamesRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class GamesIntegrationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private GamesRepository gamesRepository;


    @AfterEach
    public void cleanUpDatabase() {
    gamesRepository.deleteAll();
    }
  
  @Test
public void givenGames_whenGetGames_thenReturnArrayGamesModel() {

    GamesModel game = new GamesModel(null, "test", "test", 3, 3000);
    gamesRepository.save(game);

    ResponseEntity<GamesModel[]> response = restTemplate.exchange(
        "/games",
        HttpMethod.GET,
        null,
        GamesModel[].class
    );

    assertEquals(HttpStatus.OK, response.getStatusCode()); 
		assertEquals(1, gamesRepository.count()); 


}


@Test
public void givenGamesExist_whenPostGames_thenThrowError() {

    GamesModel game = new GamesModel(null, "test", "test", 3, 3000);
    GamesDto gamesDto=new GamesDto("test", "test", 3, 3000);
    gamesRepository.save(game);

    HttpEntity<GamesDto> body = new HttpEntity<>(gamesDto);

    ResponseEntity<String> response = restTemplate.exchange(
        "/games",
        HttpMethod.POST,
        body,
        String.class
    );

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode()); 
    assertEquals("esse jogo já está cadastrado", response.getBody());
		assertEquals(1, gamesRepository.count()); 


}
@Test
public void givenGamesInvalid_whenPostGames_thenThrowError() {

    GamesDto gamesDto=new GamesDto("", "test", 3, 3000);

    HttpEntity<GamesDto> body = new HttpEntity<>(gamesDto);

    ResponseEntity<String> response = restTemplate.exchange(
        "/games",
        HttpMethod.POST,
        body,
        String.class
    );

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); 
		assertEquals(0, gamesRepository.count()); 


}

@Test
public void givenGamesSucess_whenPostGames_thenReturnGame() {

    GamesDto gamesDto=new GamesDto("test", "test", 3, 3000);

    HttpEntity<GamesDto> body = new HttpEntity<>(gamesDto);

    ResponseEntity<GamesModel> response = restTemplate.exchange(
        "/games",
        HttpMethod.POST,
        body,
        GamesModel.class
    );

    assertEquals(HttpStatus.CREATED, response.getStatusCode()); 
    assertEquals(1, gamesRepository.count()); 
    assertEquals("test", response.getBody().getName());


}
}
