package com.example.snakeladder.model;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Random;


public class Dice {

    private static final int minValue = 1;
    private static final int maxValue = 6;

    public static int roll(){
        return new Random().nextInt(maxValue - minValue + 1) + minValue;
    }

}
