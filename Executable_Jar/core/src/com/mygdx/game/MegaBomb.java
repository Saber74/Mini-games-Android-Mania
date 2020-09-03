
package com.mygdx.game;

//6 wires: 1 wire will defuse the bomb. 
//The first person to choose the correct wire WINS.
//OR the person who is up to choose once the time runs out loses

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.MyGdxGame;

import java.util.Timer;
import java.util.TimerTask;


//instead of ApplicationAdapter, use ScreenAdapter
//class set up is same as create()

public class MegaBomb extends ScreenAdapter{
	
	String fromserver;
    ClientRead client;

	
	OrthographicCamera cam;
	MyGdxGame game;
	SpriteBatch batch;
	
	boolean play = false;

	//constants to check whether wire has been cut or not and
	//whose player's turn it is
	public static final int UNCUT = 11;
	public static final int CUT = 10;
	public static final int P1 = 1;
	public static final int P2 = 2;

	int player;//which player's turn it is

	ShapeRenderer sr;
	BitmapFont fontPlayer;//font for player's turn
	BitmapFont fontTime;//font for time displayed
	float stateTime;

	Texture bomb;
	Sprite lightSprite;
	int[] wireStatus;//6 wires: constant 11 for uncut, 10 for cut wire
	int[][] wireCoords;//coordinates of displaying wire texture on screen
	Texture[] wiresUncut;//6 coloured wires (uncut)
	Texture[] wiresCut;//6 grey wires (indicates that wire has been cut)

	String timeStamp;//display time left before bomb diffuses
	int time;

	int explode;//which wire is actually connected to bomb
	//and will lead to explosion
	
	Animation<Texture> explodeAnimation;//explosion animation
	Texture currentFrame;//frame of animation

	Timer timer;
	TimerTask task;
	
	
	//Constructor method
	public MegaBomb(MyGdxGame game) {

		//create camera to zoom and centre game at fullscreen
		cam = new OrthographicCamera(800,600);

		cam.position.set(400,300,0);
		cam.update();
		
		client=game.client;


		this.game = game;
		batch = game.batch;

		sr = new ShapeRenderer();
		
		//set fonts for the game
		fontPlayer = new BitmapFont(Gdx.files.internal("IntroScreen/Intro.fnt")); //description font
		fontTime = new BitmapFont();
		fontTime.getData().setScale(2f);

		stateTime = 0f;

		player = P1;//player 1 starts

		//initialize textures of bomb and flashing light
		bomb = new Texture("MegaBomb/bomb.png");
		lightSprite = new Sprite(new Texture("MegaBomb/light.png"));
		lightSprite.setSize(100,100);
		lightSprite.setPosition(380,290);

		//initialize wires as all uncut
		wireStatus = new int[]{UNCUT, UNCUT, UNCUT, UNCUT, UNCUT, UNCUT};
		wireCoords = new int[][]{{180,280}, {200,310},{270,310},{280,140},{235,100},{160,50}};


		wiresUncut = new Texture[wireStatus.length];
		wiresCut = new Texture[wireStatus.length];

		for(int i=0; i<wireStatus.length; i++){
			wiresUncut[i] = new Texture(String.format("MegaBomb/wire%d_colour.png",i+1));
			wiresCut[i] = new Texture(String.format("MegaBomb/wire%d_grey.png",i+1));
		}

		
		//explosion animation
		Texture[] explodeTextures = new Texture[73];

		for(int i=0; i<explodeTextures.length; i++){
			explodeTextures[i] = new Texture(String.format("MegaBomb/EXPLOSION/%d.png",i));
		}

		explodeAnimation = new Animation<Texture>(0.12f, explodeTextures);

		//choose a random wire to be the actual wire responsible for the bomb
		explode = (int)(Math.random()*wireStatus.length);
		
		//game lasts for 15 seconds
		time = 15000;
		timeStamp = "";
		//start a timer at beginning of game
		timer = new Timer();
		task = new TimerTask(){
			public void run(){
				//run as long as time is greater than 0
				if(time>0) {
					time--;//subtract one second from time
					
					//to display the time in text,
					//calculate the minutes and seconds
					int min = (int) (time / 60000);
					int sec = (int) ((time - min * 60000)/1000);
					timeStamp = String.format("%d:%d", min, sec);
					
					//when reach single-digit time, add 0 before the sec
					if(sec<10){
						timeStamp = String.format("%d:0%d", min, sec);
					}
				}
			}
		};

	}

	@Override
	public void render (float delta) {

		cam.update();
		batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//display objective of game before starting
		if(!play && time>0){
			batch.begin();
			fontPlayer.draw(batch, "Take turns cutting a wire\n  to DEFUSE THE BOMB!", 150, 350);
			fontPlayer.draw(batch, "Press <SPACE> to begin",150, 200);
			batch.end();
		}

		//start game
		if(play){
			playGame();
	
		}
		
		//if the correct wire has been cut, bomb has been DEFUSED!
		if(wireStatus[explode] == CUT){
			play = false;
			batch.begin();
			
			//draw the bomb on the screen
			batch.draw(bomb, 250, 225, 300,150);
	
			//draw each wire on the screen
			for(int i=0; i<wireStatus.length; i++){
				float width = wiresUncut[i].getWidth();
				float height = wiresUncut[i].getHeight();
				//if wire is uncut, display the coloured wire
				if(wireStatus[i]==UNCUT){
					batch.draw(wiresUncut[i], wireCoords[i][0], wireCoords[i][1],(float)(width*0.3), (float)(height*0.3));
				}
	
				//if wire is cut, display the grey wire
				else{
					batch.draw(wiresCut[i], wireCoords[i][0], wireCoords[i][1],(float)(width*0.3), (float)(height*0.3));
				}
			}
			//timer ends, winner screen appears
			time = -1;       
			fontTime.getData().setScale(1.5f);
			fontTime.draw(batch, "DEFUSED", 325, 305);
			fontPlayer.draw(batch,"Player "+player+" wins!",30,550);
			
			batch.end();

		}
		
		//if time runs out, bomb EXPLODES!
		if(time==0){
			play = false;//no longer running game
			batch.begin();
			//explosion animation
			stateTime += Gdx.graphics.getDeltaTime();
			currentFrame = explodeAnimation.getKeyFrame(stateTime, false);
			batch.draw(currentFrame,100,70,500,500);

			//display losing text in front of explosion
			fontPlayer.draw(batch,String.format("Player %d did not", player,player),250,350);
			fontPlayer.draw(batch,"defuse the bomb in time!",250,300);
			fontPlayer.draw(batch,String.format("Player %d LOSES!", player),175,200);
			
			batch.end();
		}


	}
	
	public void playGame(){
		batch.begin();
		
		//draw the bomb on the screen
		batch.draw(bomb, 250, 225, 300,150);

		//draw each wire on the screen
		for(int i=0; i<wireStatus.length; i++){
			float width = wiresUncut[i].getWidth();
			float height = wiresUncut[i].getHeight();
			//if wire is uncut, display the coloured wire
			if(wireStatus[i]==UNCUT){
				batch.draw(wiresUncut[i], wireCoords[i][0], wireCoords[i][1],(float)(width*0.3), (float)(height*0.3));
			}

			//if wire is cut, display the grey wire
			else{
				batch.draw(wiresCut[i], wireCoords[i][0], wireCoords[i][1],(float)(width*0.3), (float)(height*0.3));
			}
		}
		
		//display whose player's turn it is and time
		fontPlayer.draw(batch, String.format("Player %d's turn",player), 10, 550);
		fontTime.draw(batch, timeStamp, 350, 310);
		
		//when 10 seconds left, light starts flashing
		if(time<10000 && (time/100)%10==0){
			lightSprite.setAlpha(1f);
		}
		//when 5 seconds left, light flashes more frequently
		else if(time<5000 && (time/20)%10 == 0){
			lightSprite.setAlpha(1f);
		}
		//only go through the if statements to set light to appear 
		//(alpha 1) at certain values of time. Other times, set
		//alpha of sprite to 0 for the flashing effect.
		else{
			lightSprite.setAlpha(0f);
		}
		lightSprite.draw(batch);

		batch.end();
	}

	@Override
	public void show(){
		//check which keys are being pressed
		Gdx.input.setInputProcessor(new InputAdapter(){
			public boolean keyDown(int keycode) {
				//return to game selection screen with <ESCAPE>
				if(keycode == Input.Keys.ESCAPE){
					game.setScreen(new GamePickScreen(game));
				}
				
				//press <SPACE> to start game
				else if(keycode == Input.Keys.SPACE && time>0 && !play){
					play = true;
					timer.scheduleAtFixedRate(task,0,1);
				}
				//only check for number key pressed if game is running
				else if(play){
					if (keycode == Input.Keys.NUM_1) {//||fromserer.equals("1")
						wireStatus[0] = CUT;
						//next player's turn
						if (wireStatus[explode] == UNCUT) {
							player = player == P1 ? P2 : P1;
						}
					}
					if (keycode == Input.Keys.NUM_2) {//||fromserer.equals("2")
						wireStatus[1] = CUT;
						if (wireStatus[explode] == UNCUT) {
							player = player == P1 ? P2 : P1;
						}
					}
					if (keycode == Input.Keys.NUM_3) {//||fromserer.equals("3")
						wireStatus[2] = CUT;
						if (wireStatus[explode] == UNCUT) {
							player = player == P1 ? P2 : P1;
						}
					}
					if (keycode == Input.Keys.NUM_4) {//||fromserer.equals("4")
						wireStatus[3] = CUT;
						if (wireStatus[explode] == UNCUT) {
							player = player == P1 ? P2 : P1;
						}
					}
					if (keycode == Input.Keys.NUM_5) {//||fromserer.equals("5")
						wireStatus[4] = CUT;
						if (wireStatus[explode] == UNCUT) {
							player = player == P1 ? P2 : P1;
						}
					}
					if (keycode == Input.Keys.NUM_6) {//||fromserer.equals("6")
						wireStatus[5] = CUT;
						if (wireStatus[explode] == UNCUT) {
							player = player == P1 ? P2 : P1;
						}
					}
	
				}				

				return true;
			}

		});
	}


	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}
}


