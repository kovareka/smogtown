package com.kovareka.smogtown.gameobjects;

import com.kovareka.smogtown.helpers.Direction;

import java.util.Random;

public class Wind {
    private Direction direction;
    private int speed;
    private Random r;

    public Wind() {
        this.r = new Random();
        this.speed = 20;
        switchDirection();
    }

    public void switchDirection() {
        this.direction = Direction.values()[r.nextInt(9)];
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }
}
