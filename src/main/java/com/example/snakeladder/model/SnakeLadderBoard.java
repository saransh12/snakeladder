package com.example.snakeladder.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Data
public class SnakeLadderBoard {
    @Id
    @GeneratedValue
    private Long id;

    private Integer size;

    public enum GameState{
        IN_PROGRESS,
        OVER
    }

    private GameState gameState;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private List<PlayerGamePosition> playerPositions = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "snakeLadderBoard")
    private List<Snake> snakes;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "snakeLadderBoard")
    private List<Ladder> ladders;

    private int currentTurn = 0;

    public PlayerGamePosition getCurrentPlayerPosition() {
        return playerPositions.get(currentTurn);
    }

    public void nextTurn() {
        currentTurn = (currentTurn + 1) % playerPositions.size();
    }
}
