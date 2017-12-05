package com.kovareka.smogtown.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.kovareka.smogtown.helpers.Direction;

import java.util.Random;

public class Cloud {
    private Vector2 position;
    private Vector2 velocity;
    private Direction direction;
    private int speed;
    private int ind;

    private int width, height;

    public Cloud(float x, float y, Direction direction, int speed) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2();
        this.direction = direction;
        this.speed = speed;
        this.width = 90;
        this.height = 77;
        this.ind = new Random().nextInt(2);
    }

    public void update(float delta, Direction direction) {
        this.direction = direction;
        setVelocity();
        position.add(velocity.cpy().scl(delta));
    }

    private void setVelocity() {
        switch (direction) {
            case NORTH:
                velocity.x = 0;
                velocity.y = -speed;
                break;
            case NORTEAST:
                velocity.x = speed;
                velocity.y = -speed;
                break;
            case EAST:
                velocity.x = speed;
                velocity.y = 0;
                break;
            case SOUTHEAST:
                velocity.x = speed;
                velocity.y = speed;
                break;
            case SOUTH:
                velocity.x = 0;
                velocity.y = speed;
                break;
            case SOUTHWEST:
                velocity.x = -speed;
                velocity.y = speed;
                break;
            case WEST:
                velocity.x = -speed;
                velocity.y = 0;
                break;
            case NORTHWEST:
                velocity.x = -speed;
                velocity.y = -speed;
                break;
            case NONE:
                velocity.x = 0;
                velocity.y = 0;
                break;
            default:
                break;
        }
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getInd() {
        return ind;
    }
}
