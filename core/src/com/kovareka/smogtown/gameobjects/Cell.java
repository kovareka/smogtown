package com.kovareka.smogtown.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Cell {
    private Vector2 position;
    private int width, height;
    private long timeCreated;
    private boolean completed;

    Cell(Vector2 position, boolean completed) {
        this.width = 90;
        this.height = 77;
        this.position = position;
        this.timeCreated = System.currentTimeMillis();
        this.completed = completed;
    }

    public boolean isCompleted() {
        if (completed) {
            return true;
        } else if (System.currentTimeMillis() - timeCreated >= 3000) {
            completed = true;
            return true;
        } else {
            return false;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public Vector2 getPosition() {
        return position;
    }
}
