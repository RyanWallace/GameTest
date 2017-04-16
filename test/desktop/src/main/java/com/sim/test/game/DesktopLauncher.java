package com.sim.test.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sim.test.game.Main;

public class DesktopLauncher {
		public static void main (String[] arg) {
			
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.useGL30 = true;
		new LwjglApplication(new Main(), config);
	}
}
