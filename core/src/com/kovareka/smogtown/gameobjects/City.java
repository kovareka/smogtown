package com.kovareka.smogtown.gameobjects;

import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class City {
    private Map<Integer, Building> buildings;
    private Map<Integer, Factory> factories;
    private List<Integer> indexes;
    private Random r;

    public City() {
        this.buildings = new HashMap<Integer, Building>();
        this.factories = new HashMap<Integer, Factory>();
        this.indexes = new ArrayList<Integer>();
        createCity();
    }

    private void createCity() {
        this.r = new Random();

        for (int i = 0; i < 3; i++) {
            addNewBuildings();
        }

        addNewFactories();
    }

    private void addNewBuildings() {
        if (buildings.isEmpty()) {
            buildings.put(189, new Building(189, true)); //24
            indexes.add(189);
        } else {
            List<Integer> temp = new ArrayList<Integer>();
            temp.addAll(buildings.keySet());
            while (true) {
                int currentIndex = temp.get(r.nextInt(temp.size()));
                int index = checkNeighbours(currentIndex);
                if (index != -1) {
                    buildings.put(index, new Building(index, true));
                    indexes.add(index);
                    break;
                }
            }
        }
    }

    private void addNewFactories() {
        List<Integer> temp = new ArrayList<Integer>();
        temp.addAll(buildings.keySet());
        while (true) {
            int currentIndex = temp.get(r.nextInt(temp.size()));
            int index = checkNeighboursFactory(currentIndex);
            if (index != -1) {
                factories.put(index, new Factory(index, true));
                indexes.add(index);
                break;
            }
        }
    }

    private int checkNeighbours(int index) {
        if (!check(index - 1)) {
            return index - 1;
        } else if (!check(index - 19)) {
            return index - 19;
        } else if (!check(index + 1)) {
            return index + 1;
        } else if (!check(index + 19)) {
            return index + 19;
        } else {
            return -1;
        }
    }

    private int checkNeighboursFactory(int index) {
        if (!check(index - 1)) {
            return index - 2;
        } else if (!check(index - 19)) {
            return index - 39;
        } else if (!check(index + 1)) {
            return index + 2;
        } else if (!check(index + 19)) {
            return index + 39;
        } else {
            return -1;
        }
    }

    private boolean check(int index) {
        return buildings.containsKey(index) || buildings.containsKey(index);
    }

    public void onClick(int screenX, int screenY) {
        for (Cell f : factories.values()) {
            if (f instanceof Factory) {
                if (f.isCompleted() && screenX > f.getX() + 25 && screenX < f.getX() + 70
                        && screenY > f.getY() + 25 && screenY < f.getY() + 70) {
                    ((Factory)f).switchFactory();
                    break;
                }
            }
        }
    }

    public void switchBuilding(int index) {
        if (buildings.containsKey(index)) {
            buildings.put(index, new Building(index, false));
        } else if (factories.containsKey(index)) {
            factories.put(index, new Factory(index, false));
        }
    }

    public void createConstruction() {
        List<Integer> temp = new ArrayList<Integer>();
        temp.addAll(buildings.keySet());
        while (true) {
            int currentIndex = temp.get(r.nextInt(temp.size()));
            if (buildings.size() != 3 && buildings.size() % 3 == 0 && factories.size()*3 != buildings.size()) {
                int index = checkNeighboursFactory(currentIndex);
                if (!indexes.contains(index)) {
                    if (index != -1) {
                        factories.put(index, new Factory(index, false));
                        indexes.add(index);
                        break;
                    }
                }
            } else {
                int index = checkNeighbours(currentIndex);
                if (!indexes.contains(index)) {
                    if (index != -1) {
                        buildings.put(index, new Building(index, false));
                        indexes.add(index);
                        break;
                    }
                }
            }
        }
    }

    public Map<Integer, Building> getBuildings() {
        return buildings;
    }

    public Map<Integer, Factory> getFactories() {
        return factories;
    }

    public List<Integer> getIndexes() {
        return indexes;
    }
}
