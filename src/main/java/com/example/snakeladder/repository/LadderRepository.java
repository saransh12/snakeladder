package com.example.snakeladder.repository;

import com.example.snakeladder.model.Ladder;
import com.example.snakeladder.model.Snake;
import com.example.snakeladder.model.SnakeLadderBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LadderRepository extends JpaRepository<Ladder, Long> {
    Ladder findBySnakeLadderBoardAndBottom(SnakeLadderBoard snakeLadderBoard, Integer bottom);
}
