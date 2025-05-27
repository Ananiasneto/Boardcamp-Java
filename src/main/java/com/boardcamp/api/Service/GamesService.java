package com.boardcamp.api.Service;

import java.util.List;

import org.springframework.stereotype.Service;

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

}
