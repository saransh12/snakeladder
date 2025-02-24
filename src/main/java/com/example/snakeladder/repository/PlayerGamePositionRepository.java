package com.example.snakeladder.repository;

import com.example.snakeladder.model.PlayerGamePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerGamePositionRepository extends JpaRepository<PlayerGamePosition, Long> {
} 