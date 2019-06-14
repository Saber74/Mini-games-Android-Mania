package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Rectangle;

//Player class for SWING ALONG game
public class Player {
	//stores player's x and y value
	//stores the vine that the current player is on
	boolean onPlatform;
	Texture startPlayer;
	Texture[] jumpTextures;
	Texture currentFrame;
	float x,y;
	Vine vine;
	
	int frame = 0;
	
	Animation<Texture> jumpAnimation;
	
	//constructor method
	public Player(String name, int n, float x, float y){
		
		//player starts on platform at beginning of map
		onPlatform = true;
		startPlayer = new Texture(String.format("%s%d.png", name,0));
		
		//initialize the animation textures for jumping player animation
		jumpTextures = new Texture[n-1];
		//don't include first image in play
		
		for(int i=0; i<jumpTextures.length; i++){
			String pic = String.format("%s%d.png",name,i+1);
			jumpTextures[i] = new Texture(pic);
		}
		
		jumpAnimation = new Animation<Texture>(0.12f, jumpTextures);
		
		//starting position on platform
		this.x = x;
		this.y = y;
		
	}
	
	//set whether or not player is on the platform
	public void setPlatform(boolean onPlat){
		onPlatform = onPlat;
	}
	
	//set which vine in the primitive array the player is on
	public void setVine(Vine v){
		vine = v;
	}
	
	//check if player is on a vine or not
	public boolean onVine(){
		if(vine!=null){
			return true;
		}
		
		return false;
	}
	
	public Vine getVine(){
		return vine;
	}
	
	//set position of player on the vine
	//position varies depending on whether player is swinging 
	//to the right or left due to the fact that the player's position
	//is based on the bottom left corner
	public void setPos(boolean right){
		//get width and height of the player's texture
		float width = jumpTextures[0].getWidth();
		float height = jumpTextures[0].getHeight();
		
		//if player has positive angle rotation
		if(vine.getRotation()>0){
			//set position according to player's direction of swinging 
			if(right){
				//used trig to extend from the unit circle along the
				//length of the vine, so player is swinging 
				//at the very tip end of the vine
				x = (float)(vine.getX()-width*0.72-vine.getHeight()*Math.cos(Math.toRadians(vine.getRotation()-88)));
				y = (float)(vine.getY()-height*0.72-vine.getHeight()*Math.sin(Math.toRadians(vine.getRotation()-88)));
			}
			
			else{
				x = (float)(vine.getX()-width*0.72-vine.getHeight()*Math.cos(Math.toRadians(vine.getRotation()-92)));
				y = (float)(vine.getY()-height*0.72-vine.getHeight()*Math.sin(Math.toRadians(vine.getRotation()-92)));
			}
		}
		
		else{
			if(right){
				x = (float)(vine.getX()-width*0.72-vine.getHeight()*Math.cos(Math.toRadians(vine.getRotation()-92)));
				y = (float)(vine.getY()-height*0.72-vine.getHeight()*Math.sin(Math.toRadians(vine.getRotation()-92)));
			}
			
			else{
				x = (float)(vine.getX()-width*0.72-vine.getHeight()*Math.cos(Math.toRadians(vine.getRotation()-88)));
				y = (float)(vine.getY()-height*0.72-vine.getHeight()*Math.sin(Math.toRadians(vine.getRotation()-88)));
			}
		}
	}
	
	//move player forward as the background moves backward
	public void translateX(int tx){
		x+=tx;
	}
	
	//draw the player
	public void render(SpriteBatch batch){
		//standing image if player is on platform
		if(onPlatform){
			batch.draw(startPlayer,x,y,110,110);
		}
		//swinging pose if player is on a vine
		else{
			batch.draw(jumpTextures[0],x,y);
		}
		
	}
	
	//as player jumps, render the animation
	public void renderAnimation(float time, SpriteBatch batch){
		currentFrame = jumpAnimation.getKeyFrame(time, true);
		
		batch.draw(currentFrame,x,y);
	}
	
	//only run jumping animation once per jump
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
		return new Rectangle(x,y,jumpTextures[0].getWidth(),jumpTextures[0].getHeight());
	}
	
	public Rectangle getPosRect(){
		return new Rectangle(x,y,10,10);
	}
	
	public float getWidth(){
		return jumpTextures[0].getWidth();
	}
	
}
