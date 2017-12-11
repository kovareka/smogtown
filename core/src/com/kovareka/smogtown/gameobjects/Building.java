package com.kovareka.smogtown.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Building extends Cell{
    private float dissatisfied;
    private Rectangle rect;

    public Building(Vector2 position, boolean completed) {
        super(position, completed);
        this.dissatisfied = 0;
        this.rect = new Rectangle(position.x+25, position.y+6, 40, 50);
    }

    public void addDissatisfied(float f) {
        if (dissatisfied < 125) {
            dissatisfied += 0.15*f;
        }
    }

    public void reduceDissatisfied() {
        if (dissatisfied > 0) {
            dissatisfied -= 0.11;
            return;
        }
        dissatisfied = 0;
    }

    public float getDissatisfied() {
        return dissatisfied;
    }

    public Rectangle getRect() {
        return rect;
    }
}
