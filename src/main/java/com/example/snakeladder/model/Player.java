package com.example.snakeladder.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Player {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
//    private int position = 0;

//    public Boolean moveBy(int steps){
//        setPosition(position + steps);
//        return true;
//    }
//
//    public Boolean moveTo(int position){
//        setPosition(position);
//        return true;
//    }

}
