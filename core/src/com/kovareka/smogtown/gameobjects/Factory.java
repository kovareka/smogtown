package com.kovareka.smogtown.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Factory extends Cell {
    private boolean isWork;
    private long startTime = 0;
    private long startOff;
    private int fullness = 0;
    private boolean isClickable;

    Factory(Vector2 position, boolean completed) {
        super(position, completed);
        this.isWork = false;
        this.startOff = System.currentTimeMillis();
        this.isClickable = true;
    }

    void switchFactory() {
        long time = System.currentTimeMillis();
        if (!isWork) {
            startTime = time + startTime;
            isWork = true;
        } else {
            startTime = startTime - time;
            startOff = System.currentTimeMillis();
            isWork = false;
        }
    }

    public boolean checkTimeWork() {
        if (System.currentTimeMillis() - startTime >= 3000) {
            if (fullness != 0) {
                fullness = fullness <= 0 ? 0 : fullness - 1;
                if (fullness == 3) isClickable = true;
            }
            startTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean checkTimeOff() {
        long temp = System.currentTimeMillis() - startOff;
        if (temp >= 3000) {
            fullness += 1;
            startOff = System.currentTimeMillis();
        }

        if (fullness >= 6) {
            isClickable = false;
            switchFactory();
            return true;
        } else {
            return false;
        }
    }

    public boolean isWork() {
        return isWork;
    }

    public int getFullness() {
        return fullness;
    }

    public boolean isClickable() {
        return isClickable;
    }
}
