package com.kovareka.smogtown.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Factory extends Cell {
    private boolean isWork;
    private long startTime = 0;
    private long workTime = 0;

    Factory(Vector2 position, boolean completed) {
        super(position, completed);
        this.isWork = false;
    }

    void switchFactory() {
        long time = System.currentTimeMillis();
        if (!isWork) {
            startTime = time + startTime;
            workTime = time + workTime;
            isWork = true;
        } else {
            startTime = startTime - time;
            workTime = workTime - time;
            isWork = false;
        }
    }

    public boolean checkWork() {
        if (System.currentTimeMillis() - workTime >= 5000) {
            workTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean checkCloud() {
        if (System.currentTimeMillis() - startTime >= 7000) {
            startTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean isWork() {
        return isWork;
    }
}
