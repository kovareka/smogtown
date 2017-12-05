package com.kovareka.smogtown.gameobjects;

public class Construction extends Cell {
    private long start;

    public Construction(int index, boolean completed) {
        super(index, completed);
        this.start = System.currentTimeMillis();
    }

    public boolean checkComplete() {
        return System.currentTimeMillis() - start >= 3000;
    }

    public long getStart() {
        return start;
    }
}
