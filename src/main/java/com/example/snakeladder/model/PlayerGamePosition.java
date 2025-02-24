package com.example.snakeladder.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class PlayerGamePosition {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private SnakeLadderBoard board;

    private Integer position = 0;
    
    private Integer turnOrder;  // To maintain player order in the game
} 