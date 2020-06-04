package com.example.snakeladder.command;


import com.example.snakeladder.service.ISnakeLadderGame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@Slf4j
@ShellComponent
public class SnakeLadderCommands {

    @Autowired
    private ISnakeLadderGame snakeLadderGameService;

    @ShellMethod("Create board")
    public void createBoard(Integer size){
        snakeLadderGameService.createBoard(size);
        return;
    }

    @ShellMethod("Add player to board")
    public void addPlayerToBoard(String name, Long snakeLadderBoardId){
        snakeLadderGameService.addPlayer(name, snakeLadderBoardId);
        return;
    }

    @ShellMethod("Add ladder")
    public void addLadder(Integer top, Integer bottom, Long snakeBoardId){
        snakeLadderGameService.addLadder(top, bottom, snakeBoardId);
        return;
    }

    @ShellMethod("Add snake")
    public void addSnake(Integer head, Integer tail, Long snakeBoardId){
        snakeLadderGameService.addSnake(head, tail, snakeBoardId);
        return;
    }

    @ShellMethod("Roll dice")
    public void rollDice(Long snakeBoardId) {
        snakeLadderGameService.rollDice(snakeBoardId);
        return;
    }
//
//    @ShellMethod("List available rooms")
//    public void listAvailableRooms(){
//        List<Room> availableRooms = roomService.showAvailableRooms();
//        log.info(String.valueOf(availableRooms));
//        return;
//    }

}
