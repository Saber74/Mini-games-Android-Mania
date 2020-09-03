package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Vine object for SWING ALONG game
public class Vine {
	//stores coordinates of vine, angle of rotation
	//setOrigin
	//setPos
	
	Sprite vine;
	double height;
	float x,y;
	int angle;
	
	//constructor
	public Vine(String file, float x, float y, double h){
		
		//each vine is a separate sprite
		//has own coordinates and starts at an angle of 90 degrees
		vine = new Sprite(new Texture(file));
		height = h;
		vine.setSize(vine.getWidth(), (float)height);
		vine.setOrigin(0,0);
		angle = 90;
		
		this.x = x;
		this.y = y;
	}
	
	//set position of vine
	public void setPos(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	//update angle of rotation of vine as it swings
	public void setRotation(int angle){
		this.angle = angle;
		vine.setRotation(angle);
	}
	
	//as background translates horizontally, move vine with background
	public void translateX(int tx){
		x += tx;
	}
	
	
	//draw each vine on screen
	public void render(SpriteBatch batch){
		vine.setPosition(x, y);
		vine.draw(batch);
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getWidth(){
		return vine.getWidth();
	}
	
	public float getHeight(){
		return vine.getHeight();
	}
	
	public int getRotation(){
		return angle;
	}
	
	public Sprite getVineSprite(){
		return vine;
	}
	
}
