package com.boardcamp.api.Model;

import java.time.LocalDate;

import com.boardcamp.api.Dto.RentalsDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rental_tb")
public class RentalsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private LocalDate rentDate;
    @Column(nullable = false)
    private Integer daysRented;
    @Column
    private LocalDate returnDate;
    @Column(nullable = false)
    private Integer originalPrice;
    @Column
    private Integer delayFee;

    @ManyToOne
    @JoinColumn(name = "game_id",nullable = false)
    private GamesModel game;

    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private CustomersModel customers;

    public RentalsModel(RentalsDto dto, CustomersModel customer, GamesModel game) {
        this.rentDate = LocalDate.now();
        this.daysRented = dto.getDaysRented();
        this.returnDate = null;
        this.originalPrice = game.getPricePerDay() * dto.getDaysRented();
        this.delayFee = 0;
        this.customers = customer;
        this.game = game;
    }
}
