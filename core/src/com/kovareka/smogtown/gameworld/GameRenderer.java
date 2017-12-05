package com.kovareka.smogtown.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kovareka.smogtown.gameobjects.*;
import com.kovareka.smogtown.helpers.AssetLoader;

import java.util.Collections;

public class GameRenderer {
    private GameWorld world;

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private static TextureRegion building, factory, construction, cloud1, cloud2;
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
        construction = AssetLoader.construction;
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
        Collections.sort(world.getCity().getIndexes());

        for (Integer i : world.getCity().getIndexes()) {
            if (world.getCity().getBuildings().containsKey(i)) {
                Cell c = world.getCity().getBuildings().get(i);
                if (c.isCompleted()) {
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
            } else if (world.getCity().getFactories().containsKey(i)) {
                Cell c = world.getCity().getFactories().get(i);
                if (c.isCompleted()) {
                    batch.draw(((Factory)c).isWork() ? (TextureRegion)factoryAnimation.getKeyFrame(runTime) : factory,
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
