package com.boardcamp.api.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GamesDto {

    @NotBlank(message = "o nome do jogo está em branco")
    private String name;

    @NotBlank(message = "a imagem está em branco")
    private String image;

    @NotNull(message = "o estoque não pode ser nulo")
    @Min(value = 1, message = "o estoque não pode ser menor que 1")
    private Integer stockTotal;

    @NotNull(message = "preço por dia está em branco")
    @Min(value = 1, message = "o preço por dia não pode ser menor que 1")
    private Integer pricePerDay;
}
