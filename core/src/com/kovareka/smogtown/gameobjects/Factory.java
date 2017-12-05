package com.kovareka.smogtown.gameobjects;

public class Factory extends Cell {
    private boolean isWork;
    private long startTime = 0;
    private long workTime = 0;

    public Factory(int index, boolean completed) {
        super(index, completed);
        this.isWork = false;
    }

    public void switchFactory() {
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
