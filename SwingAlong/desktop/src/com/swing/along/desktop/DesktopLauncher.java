/*
 - intro screen
 - make crossy roads and
 */


package com.swing.along.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.swing.along.Intro;
import com.swing.along.SwingAlong;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Intro(), config);

		//check which game is picked
		//if statements open new configuration

	}

}
