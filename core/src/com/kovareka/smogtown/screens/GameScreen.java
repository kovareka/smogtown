package com.kovareka.smogtown.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.kovareka.smogtown.gameworld.GameRenderer;
import com.kovareka.smogtown.gameworld.GameWorld;
import com.kovareka.smogtown.helpers.InputHandler;

public class GameScreen implements Screen {
    private GameWorld world;
    private GameRenderer renderer;
    private float runTime = 0;

    public GameScreen() {
        this.world = new GameWorld();
        this.renderer = new GameRenderer(world);
        Gdx.input.setInputProcessor(new InputHandler(world));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(runTime);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
