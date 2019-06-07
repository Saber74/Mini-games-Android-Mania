
/*
 - intro screen
 - make crossy roads and space invaders 2 player
 	- **crossy roads - 2 chickens
 	- **recolour sprites - Stick Fight
 - detect
 */


package com.swing.along.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.mygdx.game.MyGdxGame;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());

		config.fullscreen = true;

		new LwjglApplication(new MyGdxGame(), config);


		//check which game is picked
		//if statements open new configuration

	}

}
