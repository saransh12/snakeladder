package com.example.snakeladder.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
