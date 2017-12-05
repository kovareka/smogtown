package com.kovareka.smogtown.gameworld;

import com.kovareka.smogtown.gameobjects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameWorld {
    private City city;
    private Wind wind;
    private List<Cloud> clouds;
    private long startTime;
    private Random r;
    private int sec;

    public GameWorld() {
        this.city = new City();
        this.wind = new Wind();
        this.clouds = new ArrayList<>();
        this.startTime = System.currentTimeMillis();
        this.r = new Random();
        this.sec = r.nextInt(25)+15;
    }

    public void update(float delta) {
        long temp = System.currentTimeMillis();

        if (temp - startTime >= sec*1000) {
            changeDirection();
            startTime = temp;
            sec = r.nextInt(15)+15;
        }

        for (int i = 0; i < city.getFactories().size(); i++) {
            Factory f = city.getFactories().get(i);
            if (f.isWork() && f.checkCloud()) {
                createCloud(f.getX(), f.getY());
            }
            if (f.isWork() && f.checkWork()) {
                city.addNewCell();
            }
        }

        for (int i = 0; i < clouds.size(); i++) {
            if (clouds.get(i).getX() < 10 || clouds.get(i).getX() > 700
                    || clouds.get(i).getY() < 10 || clouds.get(i).getY() > 700) {
                clouds.remove(i);
                i--;
                continue;
            }
            clouds.get(i).update(delta, wind.getDirection());
        }
    }

    private void changeDirection() {
        wind.switchDirection();
    }

    private void createCloud(float x, float y) {
        clouds.add(new Cloud(x, y, wind.getDirection(), wind.getSpeed()));
    }

    public City getCity() {
        return city;
    }

    public List<Cloud> getClouds() {
        return clouds;
    }
}
