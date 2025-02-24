package com.example.snakeladder.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Snake {
    @Id
    @GeneratedValue
    private Long id;

    private int head;
    private int tail;

    @ManyToOne
    private SnakeLadderBoard snakeLadderBoard;

}
