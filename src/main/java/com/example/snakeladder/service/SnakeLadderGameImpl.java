package com.example.snakeladder.service;

import com.example.snakeladder.model.*;
import com.example.snakeladder.repository.LadderRepository;
import com.example.snakeladder.repository.PlayerRepository;
import com.example.snakeladder.repository.SnakeLadderBoardRepository;
import com.example.snakeladder.repository.SnakeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SnakeLadderGameImpl implements ISnakeLadderGame {
    private SnakeLadderBoardRepository snakeLadderBoardRepository;
    private PlayerRepository playerRepository;
    private SnakeRepository snakeRepository;
    private LadderRepository ladderRepository;


    @Override
    public Boolean createBoard(Integer size) {
        SnakeLadderBoard snakeLadderBoard = new SnakeLadderBoard();
        snakeLadderBoard.setSize(size);
        snakeLadderBoard.setGameState(SnakeLadderBoard.GameState.IN_PROGRESS);
        snakeLadderBoardRepository.save(snakeLadderBoard);
        return true;
    }

    public void addPlayer(String name){
        Player player = new Player();
        player.setName(name);
        playerRepository.save(player);
        return;
    }

    @Override
    public Boolean addPlayer(String name, Long snakeLadderBoardId) {
        Player player = new Player();
        player.setName(name);
        playerRepository.save(player);

        SnakeLadderBoard snakeLadderBoard = snakeLadderBoardRepository.findById(snakeLadderBoardId).get();

        snakeLadderBoard.getPlayers().add(player);

        snakeLadderBoard.getPlayerPositions().put(player, 0);
        snakeLadderBoard.setNumberOfPlayers(snakeLadderBoard.getNumberOfPlayers()+1);

        snakeLadderBoardRepository.save(snakeLadderBoard);
        return true;

        //duplicate entries: https://stackoverflow.com/questions/26763213/getting-duplicate-entries-using-hibernate
    }

    @Override
    public Boolean addLadder(Integer top, Integer bottom, Long snakeBoardId) {
        SnakeLadderBoard snakeLadderBoard = snakeLadderBoardRepository.findById(snakeBoardId).get();
        Ladder ladder = new Ladder();
        ladder.setBottom(bottom);
        ladder.setTop(top);
        ladder.setSnakeLadderBoard(snakeLadderBoard);
        ladderRepository.save(ladder);
        return true;
    }

    @Override
    public Boolean addSnake(Integer head, Integer tail, Long snakeBoardId) {
        SnakeLadderBoard snakeLadderBoard = snakeLadderBoardRepository.findById(snakeBoardId).get();
        Snake snake = new Snake();
        snake.setHead(head);snake.setTail(tail);
        snake.setSnakeLadderBoard(snakeLadderBoard);
        snakeRepository.save(snake);
        return true;
    }

    @Override
    public void rollDice(Long snakeBoardId) {
        SnakeLadderBoard snakeLadderBoard = snakeLadderBoardRepository.findById(snakeBoardId).get();
        if (snakeLadderBoard.getGameState() == SnakeLadderBoard.GameState.IN_PROGRESS) {
            Integer turn = snakeLadderBoard.getTurn();
//            Player player = (Player) snakeLadderBoard.getPlayers().toArray()[turn];
            Player player = snakeLadderBoard.getPlayers().get(turn);
            Integer steps = Dice.roll();
            Integer destination = snakeLadderBoard.getPlayerPositions().get(player) + steps;
            if (destination > 100) destination = destination - steps;
            if (destination == 100) {snakeLadderBoard.setGameState(SnakeLadderBoard.GameState.OVER); System.out.println(player + "won"); return;}

            Snake snake = snakeRepository.findBySnakeLadderBoardAndHead(snakeLadderBoard, destination);
            Ladder ladder = ladderRepository.findBySnakeLadderBoardAndBottom(snakeLadderBoard, destination);
            if (snake != null) destination = snake.getTail();
            if (ladder != null) destination = ladder.getTop();
            snakeLadderBoard.getPlayerPositions().put(player, destination);
            turn = (turn + 1) % snakeLadderBoard.getNumberOfPlayers();

            System.out.println(turn + " " + snakeLadderBoard.getNumberOfPlayers() + " " + steps + player);

            snakeLadderBoard.setTurn(turn);
            snakeLadderBoardRepository.save(snakeLadderBoard);
            System.out.println(snakeLadderBoard.getPlayerPositions());
        }
    }


}
