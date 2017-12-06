package com.kovareka.smogtown.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
    private static Texture texture;

    public static TextureRegion building, factory, construction, cloud1, cloud2;
    public static TextureRegion arrowN, arrowNW, arrowW, arrowSW, arrowS, arrowSE, arrowE, arrowNE;
    public static Animation factoryAnimation, constructionAnimation;
    public static TextureRegion factoryUp, factoryDown, constructionUp, constructionDown;
    public static BitmapFont font;

    public static void load() {
        texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        building = new TextureRegion(texture, 0, 48, 90,77);
        building.flip(false, true);

        factory = new TextureRegion(texture, 540, 48, 90, 77);
        factory.flip(false, true);

        factoryUp = new TextureRegion(texture, 630, 48, 90, 77);
        factoryUp.flip(false, true);

        factoryDown = new TextureRegion(texture, 720, 48, 90, 77);
        factoryDown.flip(false, true);

        construction = new TextureRegion(texture, 270, 48, 90, 77);
        construction.flip(false, true);

        constructionUp = new TextureRegion(texture, 360, 48, 90, 77);
        constructionUp.flip(false, true);

        constructionDown = new TextureRegion(texture, 450, 48, 90, 77);
        constructionDown.flip(false, true);

        cloud1 = new TextureRegion(texture, 90, 48, 90, 77);
        cloud1.flip(false, true);

        cloud2 = new TextureRegion(texture, 180, 48, 90, 77);
        cloud2.flip(false, true);

        arrowN = new TextureRegion(texture, 48, 0, 48, 48);
        arrowN.flip(false, true);

        arrowNW = new TextureRegion(texture, 858, 77, 48, 48);
        arrowNW.flip(false, true);

        arrowW = new TextureRegion(texture, 810, 77, 48, 48);
        arrowW.flip(false, true);

        arrowSW = new TextureRegion(texture, 144, 0, 48, 48);
        arrowSW.flip(false, true);

        arrowS = new TextureRegion(texture, 906, 77, 48, 48);
        arrowS.flip(false, true);

        arrowSE = new TextureRegion(texture, 0, 0, 48, 48);
        arrowSE.flip(false, true);

        arrowE = new TextureRegion(texture, 954, 77, 48, 48);
        arrowE.flip(false, true);

        arrowNE = new TextureRegion(texture, 96, 0, 48, 48);
        arrowNE.flip(false, true);

        TextureRegion[] temp = { factoryUp, factoryDown };
        factoryAnimation = new Animation(0.5f, temp);
        factoryAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        TextureRegion[] temp1 = { construction, constructionUp, constructionDown };
        constructionAnimation = new Animation(0.3f, temp1);
        constructionAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        font = new BitmapFont(Gdx.files.internal("data/font.fnt"));
        font.getData().setScale(1f, -1f);
        font.setColor(Color.BLACK);
    }

    public static void dispose() {
        texture.dispose();
        font.dispose();
    }
}
