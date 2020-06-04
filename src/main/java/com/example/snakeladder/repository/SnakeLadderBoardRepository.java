package com.example.snakeladder.repository;

import com.example.snakeladder.model.Ladder;
import com.example.snakeladder.model.SnakeLadderBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnakeLadderBoardRepository extends JpaRepository<SnakeLadderBoard, Long> {

}
