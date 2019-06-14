package com.chicken.game;

import java.util.ArrayList;


import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.audio.Music;


public class ChickenCrossyRoad extends ApplicationAdapter implements InputProcessor{
	SpriteBatch batch;
	ShapeRenderer sr;
	Texture bkg;
	BitmapFont font;
	
	Music music; 
	
	//title page textures
	Texture title;
	Texture win;
	Texture lose;
	boolean game = false;//to tell whether or not program is running game or on a title page
	boolean start = false;
	
	int score;
	
	//related to "good guy" chicken player
	int goodIndex=0;//in an array of chicken textures, index indicates which way chicken is facing
	int goodFrame=0;
	//since the chicken is from a sprite sheet, the chicken has 3 frames for each of the directions it's facing
	static Texture[][] goodTextures;//2D array for chicken direction and then frame
	//static variable because it belongs to the class rather than the object
	float gx=0,gy=0;//coordinates of the chicken
	float moveX;//used when chicken is on an object in the water and must move with the object
	
	//related to bonus golden egg - if chicken collides with it, get extra points
	Texture bonusEgg;
	float ex,ey;
	boolean bonus = false;
	HopOn log;
	
	boolean disableKeys = false;
	
	//related to the home base nests
	Texture homeTexture;
	Texture nestTexture;
	static Rectangle[] nestRects;//array of nests at the end without chicken
	static Rectangle[] homeRects;//array of nests at the end with chicken (once it successfully reaches end)
	int home = 0;//number of chickens that made it to their home base
	
	//related to water portion of game
	Rectangle waterRect;//if player collides with waterRect, it dies
	boolean first = true;
	//flag, so you only add coordinates of object to log the first time a chicken collides with an object 
	boolean overlap = false;
	//check if chicken is overlapping an object on the water. 
	//if it is overlapping, then will not count chicken overlapping water as a death

	
	ArrayList<BadGuy>BadGuys;//class of bad guy cars on the road
	ArrayList<HopOn>HopOns;//class of hop on objects on the water
	
	Sprite feather;//appears when chicken dies - sprite because alpha changes
	float fx,fy;//coordinates of feather (it floats up over time)
	
	int lives = 3;//you have 3 lives for each chicken
	float a;//transparency
	boolean dead = false;//whether or not a chicken has died
	
	int count = 0;
	//I don't know how to keep track of the frames of the program, so I used a count variable,
	//and when count was divisible by a certain number (e.g., 2 or 5), then an event (e.g., alpha decreases 
	//or time decreases) occurs.
	
	Rectangle timeRect;//timer of the game
	
	//*********************************CREATE GAME VARIABLES*************************************
	@Override
	public void create () {
		Gdx.graphics.setWindowedMode(800,800);
		//each row is 50 pixels tall
		//System.out.println(Gdx.graphics.getWidth()+", "+Gdx.graphics.getHeight());
		
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		font = new BitmapFont();
		font.getData().setScale(2f);
		
		music = Gdx.audio.newMusic(Gdx.files.internal("chicken dance song.mp3"));
		
		//initialize background and title pages
		bkg = new Texture("background.png");
		title = new Texture("title.png");
		win = new Texture("win.png");
		lose = new Texture("lose.png");
		
		score = 0;
		
		//assign a Rectangle object to the water portion of the game
		waterRect = new Rectangle(0,400,800,400);	
		//*NOTE: water rectangle is top half of screen even though path doesn't go up to top
		//because only once chicken reaches a nest does the coordinate reset. Chicken can never go past the row
		//of nests.
		
		//initialize the chicken's textures
		goodTextures = new Texture[4][3];
		
		String[] goodImages = {"good_back","good_front","good_left","good_right"};
		
		for(int i=0; i<goodImages.length; i++){
			for(int j=0; j<3; j++){
				goodTextures[i][j] = new Texture(goodImages[i]+(j+1)+".png");
			}
		}
				
		gx = (800-50)/2;
		gy = 50;
		
		//initialize the feather (which pops up when the chicken is hit)
		feather = new Sprite(new Texture("feather.png"));
		
		//initialize the lady chicken's texture
		bonusEgg = new Texture("bonus.png");
		
		//initialize the locations of the nests (home bases) at the top of the screen
		nestTexture = new Texture("nest.png");
		nestRects = new Rectangle[5];
		for(int i=0; i<5; i++){
			nestRects[i] = new Rectangle(50+i*150,650,75,75);
		}
		
		//initialize the locations of the nests with chicken at top of screen (all non-existent initially)
		homeRects = new Rectangle[5];
		for(int i=0; i<5; i++){
			homeRects[i] = new Rectangle(0,0,0,0);
		}
		homeTexture = new Texture("home.png");
		
		//create bad guys (cars and animals on the road)
		String[][] badImages = {{"right_redcar.png"},{"left_car.png"},{"right_car.png"},
				{"left_pig1.png","left_pig2.png","left_pig3.png"},
				{"right_sheep1.png","right_sheep2.png","right_sheep3.png"}};

		BadGuys = new ArrayList<BadGuy>();
		
		//go through each image in the array
		for(int i=0; i<badImages.length; i++){
			//make 4 objects of each image
			for(int j=0; j<4; j++){
				//random integer randomizes the starting x-coordinate of the obstacle
				int randInt = (int) (Math.random()*200);
				if(i%2==0){//even-index images move right (start off screen)
					BadGuys.add(new BadGuy(badImages[i],-randInt+j*300,100+50*i,5));
				}
				else{//odd-index images move to the left (start off screen)
					BadGuys.add(new BadGuy(badImages[i],800-randInt+j*300, 100+50*i,-5));
				}
			}
		}
		
		//create the hop-on sprite objects
		HopOns = new ArrayList<HopOn>();
		int num = 0;

		String[][] hopImages = {{"egg1.png","egg2.png","egg3.png"},{"swan.png"},{"egg1.png","egg2.png","egg3.png"},{"log.png"},{"log.png"}};
		
		for(int i=0; i<hopImages.length; i++){
			for(int j=0; j<3; j++){
				int randInt = (int) (Math.random()*100);

				if(i%2==0){
					HopOns.add(new HopOn(hopImages[i],-randInt+j*300, 400+50*i,5));
				}
				else{
					HopOns.add(new HopOn(hopImages[i],800-randInt+j*300,400+50*i,-5));
				}
				
				//count number of log objects created - need this for the bonus egg game feature
				if(hopImages[i][0].contains("log")){
					num++;
				}
				
			}
		}
		
		//Bonus Egg Game Feature
		//create Array of HopOn logs
		HopOn[] logs = new HopOn[num];
		
		for(int i=0; i<HopOns.size(); i++){
			if(HopOns.get(i).toString().contains("log")){
				//since you randomly choose a log to place golden egg on, its index in the logs Array doesn't matter
				//therefore, instead of making another variable for index in logs, just decrease num each time
				logs[num-1] = HopOns.get(i);
				num--;
			}
		}
		
		int randIndex = (int)(Math.random()*num);//choose a random index for log array

		log = logs[randIndex];//create designated log object
		
		ex = log.getX();
		ey = log.getY();
		
		Gdx.input.setInputProcessor(this);
		
	}
	
	//****************************************RUN GAME**************************************
	@Override
	public void render () {
		music.play(); 
		
		Gdx.gl.glClearColor(1,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Gdx.gl.glEnable(GL20.GL_BLEND);//enable alpha with GL_BLEND
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		//System.out.println("H");
		//title page!
		if(!game && !start){
			count++;
			batch.begin();
			batch.draw(title, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			if(count%2==0){
				font.draw(batch, "Press <ENTER> and use the Arrow Keys to play!", 90, 100); 
			}
			batch.end();
		}
		
		//WIN GAME!
		if(home==5){
			
			game = false;
			batch.begin();
			batch.draw(win, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			font.draw(batch, "Score: "+score, 300,150);
			font.draw(batch, "Press <ENTER> to play again!", 200, 100); 
			batch.end();
		}
		
		//LOSE GAME
		if(lives==0 && home<5 || 800-count*0.5<=0){
			game = false;
			batch.begin();
			batch.draw(lose, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			font.draw(batch, "Press <ENTER> to try again!", 200, 100); 
			batch.end();
		}
		
		//RUN THE GAME
		if(lives>0 && 800-count*0.5>0 && game){
			count++;//acts as counter of frames per second
	
			//----------------------move objects and check overlaps-------------------------------
			
			Rectangle goodRect = new Rectangle(gx,gy,50,50);//chicken rectangle
			timeRect = new Rectangle((float)(count*0.5),770,(float)(800-count*0.5),30);
			//time rectangle continues to decrease in length			
			
			bonusEgg(goodRect);//move the golden egg with the log
			
			//go through each bad guy object
			for(BadGuy b : BadGuys){
				b.move();//move the object
			
				//if the object collides with the chicken, then chicken dies
				if(b.getRect().overlaps(goodRect)){
					die(gx,gy);
				}
			}
			
			//go through each hop on object on the water
			for(HopOn h : HopOns){
				h.changeAlpha();//update alpha value of disappearing eggs
				h.setRect();//update rectangles of objects
				h.move();//move objects			
				
				//if chicken  was on an egg nest BEFORE it turns transparent
				//and is still on the nest when it disappears, then it will die
				if(h.willSink(goodRect)){
					die(gx,gy);
				}
				
				//if chicken reaches one of the sides of the screen, it will die.
				if(gx<25 && !disableKeys || gx>750 && !disableKeys){
					die(gx,gy);
				}
				
				//check if chicken overlaps a HopOn and is within 15 pixels of either side
				//(so that it is still technically on the object)
				overlapHopOn(goodRect,h.getRect());
				
			}
			
			nestOverlap(goodRect);//check if chicken reached a nest
			
			//goes outside of loop b/c only runs if the player is on NONE of the floating objects
			//must not let this override hop on rectangle collision
			if(!overlap && waterRect.overlaps(goodRect)){
				die(gx,gy);		
			}
			
			//-----------------------draw objects onto screen-------------------------
			batch.begin();
			batch.draw(bkg, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			
			//draw chickens in bottom left corner (represents # of lives player has left)
			for(int i=0; i<lives-1; i++){
				batch.draw(goodTextures[1][1], 50*i, 0, 50,50);
			}
				
			//draw bad guy obstacles
			for(BadGuy b : BadGuys){
				b.draw(batch);
			}
			
			//draw hop on objects
			for(HopOn h : HopOns){
				h.draw(batch);
			}
			
			//draw the empty nests that chicken must still reach to go home
			for(int i=0; i<nestRects.length; i++){
				batch.draw(nestTexture, nestRects[i].x, nestRects[i].y, nestRects[i].width, nestRects[i].height);
			}
			
			//draw the nests with chickens
			for (int i=0; i<homeRects.length; i++){
				batch.draw(homeTexture, homeRects[i].x, homeRects[i].y, homeRects[i].width, homeRects[i].height);	
			}
			
			//draw the golden egg
			batch.draw(bonusEgg, ex, ey);
			
			//draw chicken player last (so that most forward on screen)
			batch.draw(goodTextures[goodIndex][goodFrame%3],goodRect.x,goodRect.y);
			
			//display score on top left of screen
			font.draw(batch, ""+score, 30, 755); 
			
			//if chicken dies
			if(dead){
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
					gx = (800-50)/2;
					gy = 50;
					goodIndex = 0;
					dead = false;
					disableKeys = false;
				}
			}	
			
			batch.end();
			
			//display time rectangle at top of screen
			sr.begin(ShapeType.Filled);
			sr.setColor(Color.RED);
			sr.rect(timeRect.x, timeRect.y, timeRect.width, timeRect.height);
			sr.end();
			
			
		}
		
		//to make the spritesheet animals look like they're moving slower,
		//momentarily pause the program 
		try{
			Thread.sleep(100);
		}
		
		catch(InterruptedException e){
			System.out.println(e);
		}
		
	
	}
	
	//move the bonus egg and check for overlap with chicken
	public void bonusEgg(Rectangle goodRectangle){
		Rectangle bonusRect = new Rectangle(ex, ey, 50, 50);
		
		if(!bonus){//as long as egg has not yet been collected, update egg coords so that moves with log
			ex = log.getX()+30;
			ey = log.getY();
		}
		
		if(bonusRect.overlaps(goodRectangle)){
			ex = -500;//set egg coordinates outside of screen
			ey = -500;
			score+=10000;//increase score by 10000 if collect the golden egg
			bonus = true;
		}
	}
	
	//check if chicken is on an object in the water
	public void overlapHopOn(Rectangle goodRectangle, Rectangle hRect){
		if(hRect.overlaps(goodRectangle) && hRect.x-gx<=15 && 
				gx+goodRectangle.width-(hRect.x+hRect.width)<=15){
			
			System.out.println("HERE");
			
			if(first){
				//flag when player initially moves onto the HopOn,
				//so the x matches with the chicken's previous x-value
				moveX = gx-hRect.x;
				first = false;
			}
			gx = moveX+hRect.x;//add the x-value of the moving HopOn onto the original chicken's x-coord
			gy = hRect.y;
			overlap = true;//set overlap = true to prevent from detecting that chicken overlapping waterRect			
		}
	}
	
	//check if player has reached one of the nest home bases
	public void nestOverlap(Rectangle goodRectangle){
		for(int i=0; i<nestRects.length; i++){
			//if chicken reaches an empty nest at the end
			if(nestRects[i].overlaps(goodRectangle)){
				overlap = true;//prevent from detecting that chicken is overlapping waterRect
				score+=50;//increase score by 50 when chicken reaches the end
				home++;
				lives = 3;
				homeRects[i] = nestRects[i];//display image of chicken on nest at the end instead of empty nest
				gx = (800-50)/2;
				gy = 50;
				if(home==5){
					score+=1000;
				}
			}
		}
	}
	
	//change variables once a chicken dies
	public void die(float x, float y){
		//set feather coordinates equal to last location where chicken died
		disableKeys = true;
		fx = x;
		fy = y;
		lives--;
		dead = true;
		//set chicken to position off screen until feather disappears
		gx = -100;
		gy = -100;
		a=1;
	}
	
	//reset variables once the game ends, so you can play again
	public void start(){
		start = true;
		disableKeys = false;
		count=0;
		lives=3;
		home=0;
		gx = (800-50)/2;
		gy = 50;
		fx = -100;
		fx = -100;
		for(int i=0; i<5; i++){
			homeRects[i] = new Rectangle(0,0,0,0);
		}
		score = 0;
		goodIndex = 0;
	}
	
	//implement ALL methods of InputProcessor
	public boolean keyDown(int keycode){
		overlap = false;
		first = true;
		
		if(!game){
			if(keycode == Keys.ENTER){
				//reset values to start/restart game
				start();
				game = true;
			}
		}
		
		//prevents player from using keys to change position of chicken when it is dead
		if(!disableKeys){
			
			if(keycode == Keys.UP){
				goodFrame++;
				goodIndex=0;//index of chicken sprite
				gy+=50;
				score+=10;//as chicken advances 1 row, increase score by 10
			}
			else if(keycode == Keys.DOWN){
				if(gy>50){//prevent chicken from moving out of bottom boundary
					goodFrame++;
					goodIndex=1;
					gy-=50;
				}
			}
			else if(keycode == Keys.LEFT){
				goodFrame++;
				goodIndex=2;
				gx-=50;
			}
			else if(keycode == Keys.RIGHT){
				goodFrame++;
				goodIndex=3;
				gx+=50;
			}
		}

		return true;
	}
	
	public boolean keyUp(int keycode){
		if(keycode == Keys.UP || keycode == Keys.DOWN ||
		keycode == Keys.LEFT || keycode == Keys.RIGHT){
			goodFrame++;//give the effect that for every move, chicken sprite moves a frame
		}

		return true;
	}
	
	public boolean keyTyped (char character) {
	    return false;
	}

	public boolean touchDown (int x, int y, int pointer, int button) {
		return false;
	}

	public boolean touchUp (int x, int y, int pointer, int button) {
		return false;
	}

	public boolean touchDragged (int x, int y, int pointer) {
		return false;
	}

	public boolean mouseMoved (int x, int y) {
	    return false;
	}

	public boolean scrolled (int amount) {
	    return false;
	}
}

//class of bad guy car and animal objects
class BadGuy{
	
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
			frameTextures[i] = new Texture(names[i]);
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

//class of hop on objects on water
class HopOn{
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
			frameTextures[i] = new Texture(names[i]);
			
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