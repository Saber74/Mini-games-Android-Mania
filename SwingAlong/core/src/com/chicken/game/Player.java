package com.chicken.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player {
	int num; //which player number: 0 or 1
	
	int home; //number of player's chickens that have reached a home
	
	int score;
	
	Texture win;
	
	Texture[][] goodTextures;
	Rectangle goodRect;
	float gx,gy;
	float moveX;
	
	int lives;
	
	Sprite feather;
	float fx,fy;
	float a;
	
	boolean dead;
	
	int goodIndex;
	int goodFrame;
	
	boolean disableKeys;
	boolean overlap;
	//check if chicken is overlapping an object on the water. 
	//if it is overlapping, then will not count chicken overlapping water as a death
	boolean first;//flag, so you only add coordinates of object to log the first time a chicken collides with an object 
	
	Texture homeTexture;
	
	//need goodTextures
	
	public Player(int num){
		
		disableKeys = false;
		
		home = 0;
		score = 0;
	
		this.num = num;
		
		win = new Texture("ChickenCrossyRoad/"+String.format("p%dwin.png", num+1));
		
		goodTextures = new Texture[4][3];
		String[] goodImages = {"_back","_front","_left","_right"};
		
		for(int i=0; i<goodImages.length; i++){
			for(int j=0; j<3; j++){
				goodTextures[i][j] = new Texture("ChickenCrossyRoad/"+String.format("good%d%s%d.png",num+1,goodImages[i],j+1));
			}
		}
		
		goodIndex = 0;
		goodFrame = 0;
		
		
		homeTexture = new Texture("ChickenCrossyRoad/"+String.format("home%d.png", num+1));
		
		lives = 5;
				
		gx = (800-50)/2-100+200*num;
		gy = 50;
		
		goodRect = new Rectangle(gx,gy,50,50);
		
		//initialize the feather (which pops up when the chicken is hit)
		feather = new Sprite(new Texture("ChickenCrossyRoad/feather.png"));
		
		dead = false;
	}
	
	public boolean won(){
		return home==3 ? true : false;
		//if player has 3 chickens home, return true. else, return false
	}
	
	public boolean isAlive(){
		return lives>0 ? true : false;
	}
	
	public boolean isOffScreen(){
		if(gx<25 || gx>750){
			return true;
		}
		return false;
	}
	
	public void addToScore(int points){
		score += points;
	}
	
	public void addHome(){
		home++;
	}
	
	public void setOverlap(boolean b){
		overlap = b;
	}
	
	public boolean getFirst(){
		return first;
	}
	
	public void setFirst(boolean b){
		first = b;
	}
	
	public void setIndex(int i){
		goodIndex = i;
	}
	
	public void increaseFrame(){
		goodFrame++;
	}
	
	public void setX(float x){
		gx = x;
	}
	
	public void setY(float y){
		gy = y;
	}
	
	public void translateX(float x){
		gx += x;
	}
	
	public void translateY(float y){
		gy += y;
	}

	public void setMoveX(float mx){
		moveX = mx;
	}
	
	public float getMoveX(){
		return moveX;
	}
	
	public void setRect(float x, float y, float width, float height){
		goodRect = new Rectangle(x,y,width,height);
	}
	
	public void updateRect(float x, float y){
		goodRect.x = x;
		goodRect.y = y;
	}
	
	public Rectangle getRect(){
		return goodRect;
	}
	
	public void setLives(int l){
		lives = l;
	}
	
	
	public void render(SpriteBatch batch){
		batch.draw(goodTextures[goodIndex][goodFrame%3],goodRect.x,goodRect.y);
	}
	
	public void renderLives(SpriteBatch batch, int index){
		for(int i=0; i<lives-1; i++){
			batch.draw(goodTextures[1][1], 50*i + 600*index, 0, 50,50);
		}
	}
	
	public void die(){
		//set feather coordinates equal to last location where chicken died
		fx = gx;
		fy = gy;
		dead = true;
		//set chicken to position off screen until feather disappears
		gy = -100;
		a=1;
		setKeysDisable(true);
	}
	
	public void setKeysDisable(boolean b){
		disableKeys = b;
	}
	
	public boolean areKeysDisabled(){
		return disableKeys;
	}
	
	public void updateFeather(SpriteBatch batch, int count){
		//increase feather coordinates (gives the effect that feather is rising up)
		fx++;
		fy++;
		feather.setPosition(fx,fy);
		feather.draw(batch,a);
		if(count%10==0){
			a-=0.5;//make feather transparent over time
		}
		//once feather fully disappears, reset chicken position
		if(a==0){
			gx = (800-50)/2-100+200*num;
			gy = 50;
			lives--;
			goodIndex = 0;
			dead = false;
		}
	}
	
	public float getAlpha(){
		return a;
	}
	
	public float getX(){
		return gx;
	}
	
	public float getY(){
		return gy;
	}
	
	public boolean isDead(){
		return dead;
	}
}
