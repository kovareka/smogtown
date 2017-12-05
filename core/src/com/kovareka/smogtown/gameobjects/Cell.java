package com.kovareka.smogtown.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Cell {
    private Vector2 position;
    private int width, height;
    private int index;
    private long timeCreated;
    private boolean completed;

    public Cell(int index, boolean completed) {
        this.width = 90;
        this.height = 77;
        this.position = new Vector2((index%20)*45 - 100, (index/20)*52 + (index%20)*26 - 380);
        this.index = index;
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
}
