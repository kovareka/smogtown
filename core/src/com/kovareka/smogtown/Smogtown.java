package com.kovareka.smogtown;

import com.badlogic.gdx.Game;
import com.kovareka.smogtown.helpers.AssetLoader;
import com.kovareka.smogtown.screens.GameScreen;

public class Smogtown extends Game {
	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new GameScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
