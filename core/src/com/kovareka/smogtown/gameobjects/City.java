package com.kovareka.smogtown.gameobjects;

import com.badlogic.gdx.math.Vector2;

import java.util.*;
import java.util.stream.Collectors;

public class City {
    private List<Vector2> positions;
    private List<Building> buildings;
    private List<Factory> factories;
    private Vector2 upperLeftCorner;
    private Vector2 upperRightCorner;
    private Vector2 lowerRightCorner;
    private Vector2 lowerLeftCorner;

    private Random r;

    public City() {
        this.r = new Random();
        createCity();
    }

    private void createCity() {
        this.upperLeftCorner = new Vector2(0, 800);
        this.upperRightCorner = new Vector2(0, 0);
        this.lowerRightCorner = new Vector2(0, 0);
        this.lowerLeftCorner = new Vector2(800, 0);
        this.positions = new ArrayList<>();
        this.buildings = new ArrayList<>();
        this.factories = new ArrayList<>();

        buildings.add(new Building(new Vector2(355, 361), true));
        positions.add(new Vector2(355, 361));
        checkBorderBuildings(new Vector2(355, 361));
        buildings.add(new Building(getNewPosition(), true));
        buildings.add(new Building(getNewPosition(), true));
        factories.add(new Factory(getNewFactoryPosition(), true));
    }

    private Vector2 getNewPosition() {
        while (true) {
            Building b = buildings.get(r.nextInt(buildings.size()));
            List<Vector2> neighbours = getNeighbours(b.getPosition());
            if (neighbours.size() > 0) {
                Vector2 v = neighbours.get(r.nextInt(neighbours.size()));
                positions.add(v);
                checkBorderBuildings(v);
                return v;
            }
        }
    }

    private Vector2 getNewFactoryPosition() {
        while (true) {
            Building b = buildings.get(r.nextInt(buildings.size()));
            List<Vector2> neighbours = getNeighbours(b.getPosition());
            while (neighbours.size() > 0) {
                Vector2 v = neighbours.get(r.nextInt(neighbours.size()));
                List<Vector2> n = getNeighbours(v);
                if (n.size() > 0) {
                    Vector2 v1 = n.get(r.nextInt(n.size()));
                    positions.add(v1);
                    checkBorderBuildings(v1);
                    return v1;
                }
            }
        }
    }

    private List<Vector2> getNeighbours(Vector2 start) {
        float x = start.x;
        float y = start.y;
        List<Vector2> result = new ArrayList<>();

        result.add(new Vector2(x + 45, y - 26));
        result.add(new Vector2(x + 45, y + 26));
        result.add(new Vector2(x - 45, y + 26));
        result.add(new Vector2(x - 45, y - 26));

        return result.stream().filter(v -> !positions.contains(v)).collect(Collectors.toList());
    }

    public void onClick(int screenX, int screenY) {
        for (Factory f : factories) {
            if (f.isCompleted() && f.isClickable() && screenX > f.getX() + 20 && screenX < f.getX() + 65
                    && screenY > f.getY() + 20 && screenY < f.getY() + 65) {
                f.switchFactory();
                break;
            }
        }
    }

    public void addNewCell() {
        if (buildings.size() != 3 && buildings.size() % 3 == 0 && factories.size()*3 != buildings.size()) {
            factories.add(new Factory(getNewPosition(), false));
        } else {
            buildings.add(new Building(getNewPosition(), false));
        }
    }

    private void checkBorderBuildings(Vector2 position) {
        if (position.y < upperLeftCorner.y) {
            upperLeftCorner.x = position.x;
            upperLeftCorner.y = position.y;
        } else if (position.y > lowerRightCorner.y) {
            lowerRightCorner.x = position.x;
            lowerRightCorner.y = position.y;
        }

        if (position.x > upperRightCorner.x) {
            upperRightCorner.x = position.x;
            upperRightCorner.y = position.y;
        }  else if (position.x < lowerLeftCorner.x) {
            lowerLeftCorner.x = position.x;
            lowerLeftCorner.y = position.y;
        }
    }

    public boolean checkBorderCity(Vector2 position) {
        return position.y < upperLeftCorner.y - 140 || position.x > upperRightCorner.x + 180
                || position.y > lowerRightCorner.y + 140  || position.x < lowerLeftCorner.x - 180;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public List<Factory> getFactories() {
        return factories;
    }

    public List<Vector2> getPositions() {
        return positions;
    }
}
