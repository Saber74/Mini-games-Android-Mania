package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

//class of HopOn objects for CHICKEN CROSSY ROAD game
public class HopOn {
	String name;//to check if it is a log or not
	int frame;//frame - as there are spritesheets used for the eggs in the nest
	Texture[] frameTextures;
	Sprite hopSprite;
	Rectangle hopRect;
	int dir;
	
	int count=0;//use counter to change alpha of eggs over time
	float a=1;//start with opaque eggs
	int aFrame = 0;//there is an array of alpha values - switch through them to create "diving eggs" effect
	Rectangle lastRect;//in terms of collision of chicken with water as eggs disappear, must save
	//last egg rectangle to check
	
	boolean floating;//check if chicken is floating on a HopOn object
	boolean swan;//special rectangle for swan
	boolean eggBlink;//only some of the egg nest HopOns will "blink"/"dive"/disappear and reappear

	//from Texture to Sprite to Rectangle (getBoundingRectangle)
	public HopOn(String[] names, int x, int y, int direction){
		name = names[0];//just need a name (first texture) to associate with the object
		frame = 0;
		dir = direction;
		frameTextures = new Texture[names.length];
		eggBlink = blink(names);//determine whether or not this specific egg object will blink or not
		
		//set each image as a texture
		for(int i=0; i<names.length; i++){
			frameTextures[i] = new Texture("android/assets/ChickenCrossyRoad/"+names[i]);
			
			if(names[i].contains("swan")){
				swan = true;
			}
			
		}
		
		hopSprite = new Sprite(frameTextures[frame]);//starts off at 0
		hopSprite.setPosition(x,y);	
		setRect();
	}
	
	public boolean blink(String[] name){
		//if the object is an egg nest, then randomly return true or false for blinking feature
		if(name[0].contains("egg")){
			int rand = (int) (Math.random()*4);
			if(rand==1){//25% of eggs will blink
				return true;
			}
			else{
				return false;
			}
		}
		
		else{
			return false;
		}
	}
	
	//for the egg, update its alpha value
	public void changeAlpha(){
		//if the object is a blinking/diving egg, then change its value
		if(eggBlink){
			//only changes value when count is a multiple of 10 (to slow down blinking time)
			if(count%10==0){
				//array of alpha transparency values 
				//(transition from opaque to translucent to transparent back to translucent and so on)
				float[] alphaFrames = {1,(float)0.5,0,(float)0.5};
				a = alphaFrames[aFrame%4];
				aFrame++;
			}
			count++;
		}
	}

	public boolean willSink(Rectangle goodRect){
		if(!floating && goodRect.overlaps(lastRect)){
			return true;//will sink
		}
		else{
			return false;
		}
	}
	
	public void setRect(){

		hopRect = hopSprite.getBoundingRectangle();
		hopSprite.setSize(hopRect.width*50/hopRect.height, 50);
		lastRect = hopRect;//most recent rectangle is saved
		//^^ used for the case where the chicken was on the egg nest before it fully disappears,
		//and it is still on the nest when it disappears - use the most recent values of the rectangle
		//to check if they collide
		floating = true;
		
		//if object is an egg nest and it is transparent, then there is no rectangle
		//chicken is no longer floating
		if(a==0){
			hopRect = new Rectangle(0,0,0,0);
			floating = false;
		}
		
		//since the chicken can't stand on the head of the swan, then start swan rectangle a 
		//few pixels to the right of the head and adjust the width so that rectangle ends at the end of swan
		if(swan){
			hopRect.width = 75;
			hopRect.x+=15;
		}
		
	}
	
	public void move(){
		hopSprite.translateX(dir);
		
		//move object back to initial side when it ends up off-screen
		if(dir>0 && hopRect.x>1000 || dir<0 && hopRect.x<-200){
			hopSprite.setPosition((-1)*(hopRect.x-800), hopRect.y);
			hopRect = hopSprite.getBoundingRectangle();
		}
		
	}
	
	public int getDir(){
		return dir;
	}
	
	public Rectangle getRect(){
		return hopRect;
	}
	
	public void draw(SpriteBatch sb){
		frame ++;
		hopSprite.setTexture(frameTextures[frame%frameTextures.length]);
		hopSprite.draw(sb,a);
	}
	
	public float getX(){
		return hopRect.x;
	}
	
	public float getY(){
		return hopRect.y;
	}
	
	public float getWidth(){
		return hopRect.width;
	}
	
	@Override
	public String toString(){
		return name;
	}
}
