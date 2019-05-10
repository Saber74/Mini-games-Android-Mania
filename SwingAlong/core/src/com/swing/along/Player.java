package com.swing.along;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
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
	boolean onPlatform;
	Texture startPlayer;
	Texture[] playerTextures;
	Texture currentFrame;
	float x,y;
	Vine vine;
	
	int frame = 0;
	
	Animation<Texture> jumpAnimation;
	
	//constructor method
	public Player(String[] frames, float x, float y){
		
		//player = new Sprite(new Texture("megaman_0.png"));
		onPlatform = true;
		
		startPlayer = new Texture("megaman_0.png");
		
		playerTextures = new Texture[frames.length];
		
		for(int i=0; i<frames.length; i++){
			playerTextures[i] = new Texture(frames[i]);
		}
		
		//player = new Sprite(playerTextures[0]);
		//player.setCenter(100, 100);
		
		jumpAnimation = new Animation<Texture>(0.1f, playerTextures);
		
		//starting position on platform
		this.x = x;
		this.y = y;
		
		//player.setPosition(x, y);
	}
	
	public void setPlatform(boolean onPlat){
		onPlatform = onPlat;
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
	
	public Vine getVine(){
		return vine;
	}
	
	public void changePos(float shiftX, float shiftY){
		x += shiftX;
		y += shiftY;
		
		//player.setPosition(x, y);
	}
	
	
	public void setPos(boolean right){
		float width = playerTextures[0].getWidth();
		float height = playerTextures[0].getHeight();
		if(vine.getRotation()>0){
			if(right){
				x = (float)(vine.getX()-width*0.72-vine.getHeight()*Math.cos(Math.toRadians(vine.getRotation()-80)));
				y = (float)(vine.getY()-height*0.72-vine.getHeight()*Math.sin(Math.toRadians(vine.getRotation()-80)));
			}
			
			else{
				x = (float)(vine.getX()-width*0.72-vine.getHeight()*Math.cos(Math.toRadians(vine.getRotation()-100)));
				y = (float)(vine.getY()-height*0.72-vine.getHeight()*Math.sin(Math.toRadians(vine.getRotation()-100)));
			}
		}
		
		else{
			if(right){
				x = (float)(vine.getX()-width*0.72-vine.getHeight()*Math.cos(Math.toRadians(vine.getRotation()-100)));
				y = (float)(vine.getY()-height*0.72-vine.getHeight()*Math.sin(Math.toRadians(vine.getRotation()-100)));
			}
			
			else{
				x = (float)(vine.getX()-width*0.72-vine.getHeight()*Math.cos(Math.toRadians(vine.getRotation()-80)));
				y = (float)(vine.getY()-height*0.72-vine.getHeight()*Math.sin(Math.toRadians(vine.getRotation()-80)));
			}
		}
	}
	
	
	public void render(SpriteBatch batch){
		//player.draw(batch);
		if(onPlatform){
			batch.draw(startPlayer,x,y);
		}
		else{
			batch.draw(playerTextures[0],x,y);
		}
		
	}
	
	public void renderAnimation(float time, SpriteBatch batch){
		currentFrame = jumpAnimation.getKeyFrame(time, true);
		
		if(onPlatform){
			x+=100;
		}
		//x+=100;
		//y+=50;
		
		batch.draw(currentFrame,x,y);
	}
	
	public boolean isFinishedAnimation(float stateTime){
		
		if(jumpAnimation.isAnimationFinished(stateTime)){
			return true;
		}
		return false;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public Rectangle getPlayerRect(){
		return new Rectangle(x,y,playerTextures[0].getWidth(),playerTextures[0].getHeight());
	}
	
	public Rectangle getPosRect(){
		return new Rectangle(x,y,10,10);
	}
	
}
