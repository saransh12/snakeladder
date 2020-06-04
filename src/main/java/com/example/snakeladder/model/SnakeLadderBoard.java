package com.example.snakeladder.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "player_position_mapping",
            joinColumns = {@JoinColumn(name = "board_id", referencedColumnName = "id")})
    private Map<Player, Integer> playerPositions = new HashMap<>();

//    @OneToMany()
//    private Set<Player> players = new HashSet<>();

    @OneToMany
    private List<Player> players = new ArrayList<>();

    private int numberOfPlayers = 0;
    private int turn = 0;
}
