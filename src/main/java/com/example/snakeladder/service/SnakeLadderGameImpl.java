package com.example.snakeladder.service;

import com.example.snakeladder.model.*;
import com.example.snakeladder.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class SnakeLadderGameImpl implements ISnakeLadderGame {
    private SnakeLadderBoardRepository snakeLadderBoardRepository;
    private PlayerRepository playerRepository;
    private SnakeRepository snakeRepository;
    private LadderRepository ladderRepository;
    private PlayerGamePositionRepository playerGamePositionRepository;


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
    @Transactional
    public Boolean addPlayer(String name, Long snakeLadderBoardId) {
        try {
            SnakeLadderBoard board = snakeLadderBoardRepository.findById(snakeLadderBoardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));

            // Check if player exists or create new
            Player player = playerRepository.findByName(name)
                .orElseGet(() -> {
                    Player newPlayer = new Player();
                    newPlayer.setName(name);
                    return playerRepository.save(newPlayer);
                });

            // Create and save PlayerGamePosition
            PlayerGamePosition position = new PlayerGamePosition();
            position.setPlayer(player);
            position.setBoard(board);
            position.setPosition(0);
            position.setTurnOrder(board.getPlayerPositions().size());
            
            // Save the position
            playerGamePositionRepository.save(position);
            
            // Add to board's collection
            board.getPlayerPositions().add(position);
            snakeLadderBoardRepository.save(board);
            
            return true;
        } catch (Exception e) {
            log.error("Error adding player: {}", e.getMessage());
            return false;
        }
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
    @Transactional
    public void rollDice(Long snakeBoardId) {
        SnakeLadderBoard board = snakeLadderBoardRepository.findById(snakeBoardId)
            .orElseThrow(() -> new RuntimeException("Board not found"));
        
        if (board.getGameState() == SnakeLadderBoard.GameState.IN_PROGRESS) {
            PlayerGamePosition currentPlayerPos = board.getCurrentPlayerPosition();
            
            Integer steps = Dice.roll();
            Integer newPosition = currentPlayerPos.getPosition() + steps;
            
            // Handle overshooting
            if (newPosition > board.getSize()) {
                newPosition = currentPlayerPos.getPosition();
            }
            
            // Check for snakes and ladders
            Snake snake = snakeRepository.findBySnakeLadderBoardAndHead(board, newPosition);
            if (snake != null) {
                newPosition = snake.getTail();
            }
            
            Ladder ladder = ladderRepository.findBySnakeLadderBoardAndBottom(board, newPosition);
            if (ladder != null) {
                newPosition = ladder.getTop();
            }
            
            // Update position
            currentPlayerPos.setPosition(newPosition);
            
            // Check win condition
            if (newPosition == board.getSize()) {
                board.setGameState(SnakeLadderBoard.GameState.OVER);
                log.info("Player {} won!", currentPlayerPos.getPlayer().getName());
            } else {
                board.nextTurn();
            }
            
            snakeLadderBoardRepository.save(board);
        }
    }


}
