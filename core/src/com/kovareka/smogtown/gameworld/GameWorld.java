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
        this.clouds = new ArrayList<Cloud>();
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

        /*
        for (Factory f : city.getFactories().values()) {
            if (f.isWork() && f.checkCloud()) {
                createCloud(f.getX(), f.getY());
            }
            if (f.isWork() && f.checkWork()) {
                createConstruction();
            }
        }
         */

        for (int i = 0; i < city.getIndexes().size(); i++) {
            if (city.getFactories().containsKey(city.getIndexes().get(i))) {
                Factory f = city.getFactories().get(city.getIndexes().get(i));
                if (f.isWork() && f.checkCloud()) {
                    createCloud(f.getX(), f.getY());
                }
                if (f.isWork() && f.checkWork()) {
                    createConstruction();
                }
            }
        }

        /*
        for (Integer i : city.getIndexes()) {
            if (city.getFactories().containsKey(i)) {
                Cell c = city.getFactories().get(i);
                if (c instanceof Construction) {
                    if (((Construction)c).checkComplete()) {
                        city.switchBuilding(i);
                    }
                }
            } else if (city.getBuildings().containsKey(i)) {
                Cell c = city.getBuildings().get(i);
                if (c instanceof Construction) {
                    if (((Construction)c).checkComplete()) {
                        city.switchBuilding(i);
                    }
                }
            }

        }
         */
        /*
        for (Cell c : city.getBuildings().values()) {
            if (c instanceof Construction) {
                if (((Construction) c).checkComplete()) {
                    city.switchBuilding(c.getIndex());
                }
            }
        }

        for (Cell c : city.getFactories().values()) {
            if (c instanceof Construction) {
                if (((Construction) c).checkComplete()) {
                    city.switchBuilding(c.getIndex());
                }
            }
        }
        */

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

    private void createConstruction() {
        city.createConstruction();
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
