package com.boardcamp.api.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.Dto.GamesDto;
import com.boardcamp.api.Exception.GameTittleConflictException;
import com.boardcamp.api.Model.GamesModel;
import com.boardcamp.api.repository.GamesRepository;

@Service
public class GamesService {
    private final GamesRepository gamesRepository;
    public GamesService(GamesRepository gamesRepository){
        this.gamesRepository=gamesRepository;
    }
    public List<GamesModel> findAllGames() {
    return gamesRepository.findAll();
    }
    public GamesModel insertGames(GamesDto game){
        boolean gameExist=gamesRepository.existsByName(game.getName());
        if(gameExist){
            throw new GameTittleConflictException("esse jogo já está cadastrado");
        }else{
           GamesModel gameDto=new GamesModel(game);
        GamesModel gameSave=gamesRepository.save(gameDto);
        return gameSave; 
        }
        
    }

}
