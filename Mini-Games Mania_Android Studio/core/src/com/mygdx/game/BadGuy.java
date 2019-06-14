package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

//class of BadGuys for CHICKEN CROSSY ROAD game
//the cars and animals that walk across the screen
//if player hits one, minus one life
public class BadGuy {
	int frame;//frame of the image
	Texture[] frameTextures;//stores the images (various spritesheet frames) as textures
	Sprite badSprite;//current frame is set to be the sprite
	Rectangle badRect;//get rectangle of current sprite
	int dir;//whether object is traveling left or right
	

	//from String (name) to Texture (frameTexture) to Sprite (badSprite) to Rectangle (use getBoundingRectangle)
	//take in the array of image names, the x and y coords of object, and its direction (-5 or 5)
	public BadGuy(String[] names, int x, int y, int direction){
		frame = 0;
		dir = direction;
		//create a texture for each image name
		frameTextures = new Texture[names.length];
		for(int i=0; i<names.length; i++){
			frameTextures[i] = new Texture("android/assets/ChickenCrossyRoad/"+names[i]);
		}
		
		badSprite = new Sprite(frameTextures[frame]);//starts off at 0th frame
		badSprite.setPosition(x,y);
		badRect = badSprite.getBoundingRectangle();
		
	}
	
	//move the object
	public void move(){
		badSprite.translateX(dir);
		badRect = badSprite.getBoundingRectangle();
		
		//once object is off-screen, move it back to starting side to travel across screen again
		if(dir>0 && badRect.x>1000 || dir<0 && badRect.x<-200){
			badSprite.setPosition((-1)*(badRect.x-800), badRect.y);
			badRect = badSprite.getBoundingRectangle();
		}

	}
	
	//return the rectangle of the object
	public Rectangle getRect(){
		return badRect;
	}
	
	//draw the objects on the screen
	public void draw(SpriteBatch sb){
		frame ++;
		badSprite.setTexture(frameTextures[frame%frameTextures.length]);
		badSprite.setSize(badRect.width*50/badRect.height, 50);
		//^^change rectangle size
		badSprite.draw(sb);
	}
}
