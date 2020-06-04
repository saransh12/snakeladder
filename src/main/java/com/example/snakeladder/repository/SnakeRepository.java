package com.example.snakeladder.repository;

import com.example.snakeladder.model.Player;
import com.example.snakeladder.model.Snake;
import com.example.snakeladder.model.SnakeLadderBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnakeRepository extends JpaRepository<Snake, Long> {
    Snake findBySnakeLadderBoardAndHead(SnakeLadderBoard snakeLadderBoard, Integer head);
}
