package com.swing.along;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Player {
	//stores player's x and y value
	//stores the vine that the current player is on
	Sprite player;
	float x,y;
	Vine vine;
	
	//constructor method
	public Player(String file, float x, float y){
		player = new Sprite(new Texture(file));
		//starting position on platform
		this.x = x;
		this.y = y;
		player.setPosition(x, y);
	}
	
	public void setVine(Vine v){
		vine = v;
	}
	
	public boolean onVine(){
		if(vine!=null){
			return true;
		}
		
		return false;
	}
	
	public void setPos(){
		x = -80+(float)(vine.getX()-vine.getWidth()/2-vine.getHeight()*Math.cos(Math.toRadians(vine.getRotation()-90)));
		y = -30+(float)(vine.getY()-vine.getHeight()/2-vine.getHeight()*Math.sin(Math.toRadians(vine.getRotation()-90)));
		
		player.setPosition(x, y);
	}
	
	public void setRotation(int angle){
		player.setRotation(angle);
	}
	
	public void render(SpriteBatch batch){
		player.draw(batch);
	}
	
	
	
}
