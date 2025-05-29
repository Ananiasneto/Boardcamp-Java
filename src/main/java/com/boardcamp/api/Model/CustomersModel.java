package com.boardcamp.api.Model;

import com.boardcamp.api.Dto.CustomersDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_tb")
public class CustomersModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "o nome não pode estar em branco")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "o telefone não pode estar em branco")
    private String phone;

    @Column(nullable = false, unique = true, length = 11)
    @NotBlank(message = "o cpf não pode estar em branco")
    @Size(min = 11, max = 11, message = "o cpf deve ter 11 caracteres")
    private String cpf;

    public CustomersModel(CustomersDto dto){
        this.cpf=dto.getCpf();
        this.name=dto.getName();
        this.phone=dto.getPhone();
        }
}
