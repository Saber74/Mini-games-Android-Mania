
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
import com.space.Space_Main;
import com.swing.along.Intro;
import com.swing.along.SwingAlong;
import com.chicken.game.ChickenCrossyRoad;
import com.mega.bomb.MegaBomb;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MegaBomb(), config);

		//check which game is picked
		//if statements open new configuration

	}

}
