package com.upc.desarrollo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.upc.desarrollo.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.width = Config.GAME_WIDTH;
		//config.height = Config.GAME_HEIGHT;
		new LwjglApplication(new Main(), config);
	}
}
