package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

//class of Home Nests for CHICKEN CROSSY ROAD game
public class Home {
	Rectangle nestRect;//rectangle of home to check if collides with player
	Texture display;//either display an empty nest or full nest

	//constructor method
	public Home(String pic, Rectangle nRect){
		//set the display of an empty nest and coordinates of rectangle
		display = new Texture(pic);
		nestRect = nRect;
	}

	//if player reaches end, set display texture to be nest with chicken in it
	public void setHome(String chicken){
		display = new Texture("android/assets/ChickenCrossyRoad/"+chicken);
	}
	
	public Rectangle getRect(){
		return nestRect;
	}
}
