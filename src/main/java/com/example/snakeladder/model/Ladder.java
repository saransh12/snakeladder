package com.example.snakeladder.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
public class Ladder {
    @Id
    @GeneratedValue
    private Long id;

    private int top;
    private int bottom;

    @ManyToOne
    private SnakeLadderBoard snakeLadderBoard;
}
