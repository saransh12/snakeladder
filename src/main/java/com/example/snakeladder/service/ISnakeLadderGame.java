package com.example.snakeladder.service;

public interface ISnakeLadderGame {
    Boolean createBoard(Integer size);
    Boolean addPlayer(String name, Long snakeLadderBoardId);
    Boolean addLadder(Integer top, Integer bottom, Long snakeBoardId);
    Boolean addSnake(Integer head, Integer tail, Long snakeBoardId);
    void rollDice(Long snakeBoardId);
}
