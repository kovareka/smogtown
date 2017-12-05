package com.kovareka.smogtown.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.kovareka.smogtown.Smogtown;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(800, 800);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new Smogtown();
        }
}