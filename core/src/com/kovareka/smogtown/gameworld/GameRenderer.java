package com.kovareka.smogtown.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.kovareka.smogtown.gameobjects.*;
import com.kovareka.smogtown.helpers.AssetLoader;

import java.util.*;

public class GameRenderer {
    private GameWorld world;

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private static TextureRegion building, factory, cloud1, cloud2;
    private static Animation factoryAnimation, constructionAnimation;

    public GameRenderer(GameWorld world) {
        this.world = world;
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(true, 800, 800);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        initAssets();
    }

    private void initAssets() {
        building = AssetLoader.building;
        factory = AssetLoader.factory;
        cloud1 = AssetLoader.cloud1;
        cloud2 = AssetLoader.cloud2;
        factoryAnimation = AssetLoader.factoryAnimation;
        constructionAnimation = AssetLoader.constructionAnimation;
    }

    public void render(float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(105 / 255.0f, 235 / 255.0f, 240 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 800, 800);
        shapeRenderer.end();

        batch.begin();
        drawCity(runTime);
        drawCloud();
        batch.end();
    }

    private void drawCloud() {
        for (Cloud c : world.getClouds()) {
            batch.draw(c.getInd() == 0 ? cloud1 : cloud2,
                    c.getX(),
                    c.getY(),
                    c.getWidth(),
                    c.getHeight());
        }
    }

    private void drawCity(float runTime) {
        List<Vector2> positions = world.getCity().getPositions();
        positions.sort((c1, c2) -> {
            if (c1.y > c2.y) {
                return 1;
            } else if (c1.y < c2.y){
                return -1;
            } else {
                return 0;
            }
        });

        for (Vector2 v : positions) {
            for (int i = 0; i < world.getCity().getBuildings().size(); i++) {
                Building b = world.getCity().getBuildings().get(i);
                if (v.equals(b.getPosition())) {
                    if (b.isCompleted()) {
                        batch.draw(building,
                                world.getCity().getBuildings().get(i).getX(),
                                world.getCity().getBuildings().get(i).getY(),
                                world.getCity().getBuildings().get(i).getWidth(),
                                world.getCity().getBuildings().get(i).getHeight());
                    } else {
                        batch.draw((TextureRegion)constructionAnimation.getKeyFrame(runTime),
                                world.getCity().getBuildings().get(i).getX(),
                                world.getCity().getBuildings().get(i).getY(),
                                world.getCity().getBuildings().get(i).getWidth(),
                                world.getCity().getBuildings().get(i).getHeight());
                    }
                }
            }
            for (int i = 0; i < world.getCity().getFactories().size(); i++) {
                Factory f = world.getCity().getFactories().get(i);
                if (v.equals(f.getPosition())) {
                    if (f.isCompleted()) {
                        batch.draw(f.isWork() ? (TextureRegion)factoryAnimation.getKeyFrame(runTime) : factory,
                                world.getCity().getFactories().get(i).getX(),
                                world.getCity().getFactories().get(i).getY(),
                                world.getCity().getFactories().get(i).getWidth(),
                                world.getCity().getFactories().get(i).getHeight());
                    } else {
                        batch.draw((TextureRegion)constructionAnimation.getKeyFrame(runTime),
                                world.getCity().getFactories().get(i).getX(),
                                world.getCity().getFactories().get(i).getY(),
                                world.getCity().getFactories().get(i).getWidth(),
                                world.getCity().getFactories().get(i).getHeight());
                    }
                }
            }
        }
    }
}
