package com.boardcamp.api.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.Model.GamesModel;
import com.boardcamp.api.Service.GamesService;

@RestController
@RequestMapping("/games")
public class GamesController {

    private final GamesService gamesService;
    public GamesController(GamesService gamesService){
        this.gamesService=gamesService;
    }

     @GetMapping
   public ResponseEntity<Object> getAllGames() {
    List<GamesModel> games=gamesService.findAllGames();
    return ResponseEntity.status(HttpStatus.OK).body(games);
}
}
