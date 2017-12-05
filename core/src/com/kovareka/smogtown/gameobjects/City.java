package com.kovareka.smogtown.gameobjects;

import com.badlogic.gdx.math.Vector2;

import java.util.*;
import java.util.stream.Collectors;

public class City {
    private List<Vector2> positions;
    private List<Building> buildings;
    private List<Factory> factories;
    private Random r;

    public City() {
        this.r = new Random();
        createCity();
    }

    private void createCity() {
        this.positions = new ArrayList<>();
        this.buildings = new ArrayList<>();
        this.factories = new ArrayList<>();

        buildings.add(new Building(new Vector2(355, 361), true));
        positions.add(new Vector2(355, 361));
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
            if (f.isCompleted() && screenX > f.getX() + 20 && screenX < f.getX() + 65
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
