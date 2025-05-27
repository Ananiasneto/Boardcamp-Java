package com.boardcamp.api.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RentalsDto {

    @NotNull(message = "o ID do cliente não pode ser vazio")
    private Long customerId;

    @NotNull(message = "o ID do jogo não pode ser vazio")
    private Long gameId;

    @NotNull(message = "o número de dias alugados não pode ser vazio")
    @Min(value = 1, message = "o aluguel não deve ser menor que 1 dia")
    private Integer daysRented;
}
