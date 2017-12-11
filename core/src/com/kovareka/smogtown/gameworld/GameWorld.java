package com.kovareka.smogtown.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.kovareka.smogtown.gameobjects.*;
import com.kovareka.smogtown.helpers.Direction;

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
    private float population;
    private GameState currentState;

    public enum GameState {
        READY, RUNNING, GAMEOVER
    }

    public GameWorld() {
        startNewGame();
        this.currentState = GameState.READY;
    }

    public void update(float delta) {
        switch (currentState) {
            case READY:
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case GAMEOVER:
                break;
            default:
                break;
        }
    }

    public void updateRunning(float delta) {
        population += 0.13;

        checkWind();
        checkFactories();
        checkResourcesForNewBuilding();

        for (int i = 0; i < clouds.size(); i++) {
            if (city.checkBorderCity(clouds.get(i).getPosition())) {
                clouds.remove(i);
                i--;
                continue;
            }
            clouds.get(i).update(delta, wind.getDirection());
        }
        checkDissatisfied();

        if ((int)((getDissatisfied() / (population-20))*100) >= 100) {
            currentState = GameState.GAMEOVER;
        }
    }

    private void startNewGame() {
        this.city = new City();
        this.wind = new Wind();
        this.clouds = new ArrayList<>();
        this.windTime = System.currentTimeMillis();
        this.r = new Random();
        this.sec = r.nextInt(25)+15;
        this.resourcesForBuilding = city.getBuildings().size()*90*0.31;
        this.population = this.city.getBuildings().size()*100;
    }

    public void restart() {
        startNewGame();
        this.currentState = GameState.RUNNING;
    }

    private void checkDissatisfied() {
        label: for (int i = 0; i < city.getBuildings().size(); i++) {
            for (int k = 0; k < clouds.size(); k++) {
                if (city.getBuildings().get(i).isCompleted()) {
                    if (Intersector.overlaps(clouds.get(k).getRect(), city.getBuildings().get(i).getRect())) {
                        city.getBuildings().get(i).addDissatisfied(1);
                        continue label;
                    }
                }
            }
            if (city.getBuildings().get(i).getDissatisfied() != 0) {
                city.getBuildings().get(i).reduceDissatisfied();
            }
        }
    }

    private void checkFactories() {
        for (int i = 0; i < city.getFactories().size(); i++) {
            Factory f = city.getFactories().get(i);
            if (f.isWork() && f.checkTimeWork()) {
                createCloud(f.getX(), f.getY());
                resources += 33;
            } else if (!f.isWork()) {
                if (f.checkTimeOff()) {
                    createCloud(f.getX(), f.getY());
                }
            }
        }
    }

    private void checkResourcesForNewBuilding() {
        double temp = city.getBuildings().size()*90*0.31;
        if (resources - temp >= 0) {
            resources -= temp;
            city.addNewCell();
            resourcesForBuilding = city.getBuildings().size()*90*0.31;
        }
    }

    private void checkWind() {
        long temp = System.currentTimeMillis();

        if (temp - windTime >= sec*1000) {
            wind.switchDirection();
            windTime = temp;
            sec = r.nextInt(15);
            if (wind.getDirection() != Direction.NONE) sec += 10;
        }
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

    public float getPopulation() {
        return population;
    }

    public int getDissatisfied() {
        float result = 0;

        for (int i = 0; i < city.getBuildings().size(); i++) {
            result += city.getBuildings().get(i).getDissatisfied();
        }

        float t = (int)(population - city.getBuildings().size())/100;

        return (int)(result + t*2);
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }
}
