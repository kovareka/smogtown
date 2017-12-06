package com.kovareka.smogtown.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Factory extends Cell {
    private boolean isWork;
    private long startTime = 0;

    Factory(Vector2 position, boolean completed) {
        super(position, completed);
        this.isWork = false;
    }

    void switchFactory() {
        long time = System.currentTimeMillis();
        if (!isWork) {
            startTime = time + startTime;
            isWork = true;
        } else {
            startTime = startTime - time;
            isWork = false;
        }
    }

    public boolean checkCloud() {
        if (System.currentTimeMillis() - startTime >= 3000) {
            startTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean isWork() {
        return isWork;
    }
}
