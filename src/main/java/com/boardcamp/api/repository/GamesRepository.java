package com.boardcamp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.boardcamp.api.Model.GamesModel;

public interface GamesRepository extends JpaRepository<GamesModel, Long> { }