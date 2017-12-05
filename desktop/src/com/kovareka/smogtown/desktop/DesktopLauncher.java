package com.kovareka.smogtown.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kovareka.smogtown.Smogtown;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Smogtown";
		config.width = 800;
		config.height = 800;
		new LwjglApplication(new Smogtown(), config);
	}
}
