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
import com.swing.along.Intro;
import com.swing.along.SwingAlong;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new SwingAlong(), config);

		//check which game is picked
		//if statements open new configuration

	}

}
