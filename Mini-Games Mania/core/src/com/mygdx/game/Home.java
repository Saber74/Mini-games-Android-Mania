package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Home {
	Rectangle nestRect;
	Texture display;//either display an empty nest or full nest
	
	public Home(String pic, Rectangle nRect){
		display = new Texture(pic);
		nestRect = nRect;
	}
	
	public void setHome(String chicken){
		display = new Texture("ChickenCrossyRoad/"+chicken);
	}
	
	public Rectangle getRect(){
		return nestRect;
	}
}
