package com.boardcamp.api.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomersDto {

    @Column(nullable = false)
    @NotBlank(message = "o nome não pode estar em branco")
    private String name;

    @Column(nullable = false, unique = true)
    @ NotBlank(message = "o telefone não pode estar em branco")
    @Pattern(regexp = "\\d{11}", message = "o telefone deve ter apenas números e ter 11 dígitos")
    private String phone;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "o cpf não pode estar em branco")
    @Size(min = 11, max = 11, message = "o cpf deve ter 11 caracteres")
    private String cpf;
}
