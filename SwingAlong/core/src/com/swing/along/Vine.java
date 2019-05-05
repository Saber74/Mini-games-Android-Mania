package com.swing.along;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Vine {
	//stores coordinates of vine, angle of rotation
	//setOrigin
	//setPos
	
	Sprite vine;
	float x,y;
	int angle;
	
	public Vine(String file){
		vine = new Sprite(new Texture(file));
		vine.setSize(vine.getWidth(), 200);
		vine.setOrigin(0,0);
		angle = 90;
	}
	
	public void setPos(float x, float y){
		this.x = x;
		this.y = y;
		
		vine.setPosition(x, y);
	}
	
	public void setRotation(int angle){
		this.angle = angle;
		vine.setRotation(angle);
	}
	
	public void changeX(int shift){
		x += shift;
	}
	
	public void render(SpriteBatch batch){
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
	
	
	
}