package com.kovareka.smogtown.gameworld;

import com.kovareka.smogtown.gameobjects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameWorld {
    private long windTime;
    private Random r;
    private int sec;

    private City city;
    private Wind wind;
    private List<Cloud> clouds;
    private double resources = 0;
    private double resourcesForBuilding;

    public GameWorld() {
        this.city = new City();
        this.wind = new Wind();
        this.clouds = new ArrayList<>();
        this.windTime = System.currentTimeMillis();
        this.r = new Random();
        this.sec = r.nextInt(25)+15;
        this.resourcesForBuilding = city.getBuildings().size()*90*0.2;
    }

    public void update(float delta) {
        long temp = System.currentTimeMillis();

        if (temp - windTime >= sec*1000) {
            changeDirection();
            windTime = temp;
            sec = r.nextInt(15)+15;
        }

        for (int i = 0; i < city.getFactories().size(); i++) {
            Factory f = city.getFactories().get(i);
            if (f.isWork() && f.checkTimeWork()) {
                createCloud(f.getX(), f.getY());
                resources += 44;
            } else if (!f.isWork()) {
                if (f.checkTimeOff()) {
                    createCloud(f.getX(), f.getY());
                }
            }
        }

        checkResourcesForNewBuilding();

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

    private void checkResourcesForNewBuilding() {
        double temp = city.getBuildings().size()*90*0.2;
        if (resources - temp >= 0) {
            resources -= temp;
            city.addNewCell();
            resourcesForBuilding = city.getBuildings().size()*90*0.2;
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

    public Wind getWind() {
        return wind;
    }

    public double getResources() {
        return resources;
    }

    public double getResourcesForBuilding() {
        return resourcesForBuilding;
    }
}
