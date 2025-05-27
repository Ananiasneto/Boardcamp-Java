package com.boardcamp.api.Model;

import com.boardcamp.api.Dto.GamesDto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "game_tb")
public class GamesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "o nome do jogo está em branco")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "a imagem está em branco")
    private String image;

    @Column(nullable = false)
    @NotNull(message = "o estoque não pode ser nulo")
    @Min(value = 1, message = "o estoque não pode menor que 0")
    private Integer stockTotal;

    @Column(nullable = false)
    @NotNull(message = "preço por dia está em branco")
    @Min(value = 1, message = "o preço por dia não pode menor que 0")
    private Integer pricePerDay;

    public GamesModel(GamesDto gamesDto) {
    this.name = gamesDto.getName();
    this.image = gamesDto.getImage();
    this.stockTotal = gamesDto.getStockTotal();
    this.pricePerDay = gamesDto.getPricePerDay();
}
}
