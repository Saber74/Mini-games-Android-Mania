package com.mygdx.game;

/* RULES FOR 2 PLAYERS
 * 
 * - first one to get 3 chickens to the end wins
 * - OR last one to lose all lives wins
 */


import java.util.ArrayList;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.audio.Music;


public class ChickenCrossyRoad extends ScreenAdapter {

	OrthographicCamera cam;
	MyGdxGame game;
	SpriteBatch batch;
	ShapeRenderer sr;
	Texture bkg;
	BitmapFont font;

	Texture back;//black on sides of game

	Music music;

	//title page textures
	Texture title;
	boolean run = true;//to tell whether or not program is running game or on a title page
	boolean start = false;

	Chicken[] players;

	//related to bonus golden egg - if chicken collides with it, get extra points
	Texture bonusEgg;
	float ex, ey;
	boolean bonus = false;
	HopOn log;


	Home[] homes;

	//related to the home base nests
	Texture homeTexture;
	Texture nestTexture;
	static Rectangle[] nestRects;//array of nests at the end without chicken
	static Rectangle[] homeRects;//array of nests at the end with chicken (once it successfully reaches end)
	int home = 0;//number of chickens that made it to their home base

	//related to water portion of game
	Rectangle waterRect;//if player collides with waterRect, it dies


	ArrayList<BadGuy> BadGuys;//class of bad guy cars on the road
	ArrayList<HopOn> HopOns;//class of hop on objects on the water

	int count = 0;
	//I don't know how to keep track of the frames of the program, so I used a count variable,
	//and when count was divisible by a certain number (e.g., 2 or 5), then an event (e.g., alpha decreases 
	//or time decreases) occurs.


	//*********************************CREATE GAME VARIABLES*************************************
	public ChickenCrossyRoad(MyGdxGame game) {

		cam = new OrthographicCamera(1600,1000);

		cam.position.set(400,500,0);
		cam.update();

		this.game = game;

		//game.resize(1000,1000);

		//Gdx.graphics.setWindowedMode(800, 800);
		//each row is 50 pixels tall
		//System.out.println(Gdx.graphics.getWidth()+", "+Gdx.graphics.getHeight());

		batch = game.batch;
		sr = new ShapeRenderer();
		font = new BitmapFont();
		font.getData().setScale(2f);

		//music = Gdx.audio.newMusic(Gdx.files.internal("chicken dance song.mp3"));

		//initialize background and title pages
		bkg = new Texture("ChickenCrossyRoad/background.png");
		title = new Texture("ChickenCrossyRoad/title.png");
		back = new Texture("ChickenCrossyRoad/back.png");

		//assign a Rectangle object to the water portion of the game
		waterRect = new Rectangle(0, 400, 800, 400);
		//*NOTE: water rectangle is top half of screen even though path doesn't go up to top
		//because only once chicken reaches a nest does the coordinate reset. Chicken can never go past the row
		//of nests.

		players = new Chicken[]{new Chicken(0), new Chicken(1)};


		//initialize the lady chicken's texture
		bonusEgg = new Texture("ChickenCrossyRoad/bonus.png");

		homes = new Home[5];
		for (int i = 0; i < 5; i++) {
			homes[i] = new Home("ChickenCrossyRoad/nest.png", new Rectangle(50 + i * 150, 670, 75, 75));
		}


		//create bad guys (cars and animals on the road)
		String[][] badImages = {{"right_redcar.png"}, {"left_car.png"}, {"right_car.png"},
				{"left_pig1.png", "left_pig2.png", "left_pig3.png"},
				{"right_sheep1.png", "right_sheep2.png", "right_sheep3.png"}};

		BadGuys = new ArrayList<BadGuy>();

		//go through each image in the array
		for (int i = 0; i < badImages.length; i++) {
			//make 4 objects of each image
			for (int j = 0; j < 4; j++) {
				//random integer randomizes the starting x-coordinate of the obstacle
				int randInt = (int) (Math.random() * 200);
				if (i % 2 == 0) {//even-index images move right (start off screen)
					BadGuys.add(new BadGuy(badImages[i], -randInt + j * 300, 100 + 50 * i, 5));
				} else {//odd-index images move to the left (start off screen)
					BadGuys.add(new BadGuy(badImages[i], 800 - randInt + j * 300, 100 + 50 * i, -5));
				}
			}
		}

		//create the hop-on sprite objects
		HopOns = new ArrayList<HopOn>();
		int num = 0;

		String[][] hopImages = {{"egg1.png", "egg2.png", "egg3.png"}, {"swan.png"}, {"egg1.png", "egg2.png", "egg3.png"}, {"log.png"}, {"log.png"}};

		for (int i = 0; i < hopImages.length; i++) {
			for (int j = 0; j < 3; j++) {
				int randInt = (int) (Math.random() * 100);

				if (i % 2 == 0) {
					HopOns.add(new HopOn(hopImages[i], -randInt + j * 300, 400 + 50 * i, 5));
				} else {
					HopOns.add(new HopOn(hopImages[i], 800 - randInt + j * 300, 400 + 50 * i, -5));
				}

				//count number of log objects created - need this for the bonus egg game feature
				if (hopImages[i][0].contains("log")) {
					num++;
				}

			}
		}

		//Bonus Egg Game Feature
		//create Array of HopOn logs
		HopOn[] logs = new HopOn[num];

		for (int i = 0; i < HopOns.size(); i++) {
			if (HopOns.get(i).toString().contains("log")) {
				//since you randomly choose a log to place golden egg on, its index in the logs Array doesn't matter
				//therefore, instead of making another variable for index in logs, just decrease num each time
				logs[num - 1] = HopOns.get(i);
				num--;
			}
		}

		int randIndex = (int) (Math.random() * num);//choose a random index for log array

		log = logs[randIndex];//create designated log object

		ex = log.getX();
		ey = log.getY();

	}

	//****************************************RUN GAME**************************************
	@Override
	public void render(float delta) {

		cam.update();
		batch.setProjectionMatrix(cam.combined);
		//music.play();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Gdx.gl.glEnable(GL20.GL_BLEND);//enable alpha with GL_BLEND
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		//PLAYER 1 WINS GAME!
		//got 3 of their chickens home OR other player lost all their lives
		if (players[0].won() || !players[1].isAlive()) {

			run = false;
			batch.begin();
			batch.draw(players[0].win, 0, 0, 800, 1000);
			font.draw(batch, "Player 1 Score: " + players[0].score, 280, 600);
			font.draw(batch, "Player 2 Score: " + players[1].score, 280, 500);
			batch.end();
		}

		//PLAYER 2 WINS GAME!
		if (players[1].won() || !players[0].isAlive()) {
			run = false;
			batch.begin();
			batch.draw(players[1].win, 0, 0, 800, 1000);
			font.draw(batch, "Player 1 Score: " + players[0].score, 280, 600);
			font.draw(batch, "Player 2 Score: " + players[1].score, 280, 500);
			batch.end();
		}

		//RUN THE GAME
		if (players[0].isAlive() && run || players[1].isAlive() && run) {

			//----------------------move objects and check overlaps-------------------------------

			count++;

			for (Chicken p : players) {
				p.updateRect(p.getX(), p.getY());
			}

			for (int i = 0; i < 2; i++) {
				bonusEgg(players[i]);//move the golden egg with the log
			}

			//go through each bad guy object
			for (BadGuy b : BadGuys) {
				b.move();//move the object

				//if the object collides with the chicken, then chicken dies
				for (Chicken p : players) {
					if (b.getRect().overlaps(p.getRect())) {
						p.die();
					}
				}
			}

			//go through each hop on object on the water
			for (HopOn h : HopOns) {
				h.changeAlpha();//update alpha value of disappearing eggs
				h.setRect();//update rectangles of objects
				h.move();//move objects			

				//if chicken  was on an egg nest BEFORE it turns transparent
				//and is still on the nest when it disappears, then it will die
				for (Chicken p : players) {
					if (h.willSink(p.getRect())) {
						p.die();
					}


					//if chicken reaches one of the sides of the screen, it will die.
					//25 and 750
					if (p.isOffScreen() && p.areKeysDisabled()) {
						p.die();
					}

					//check if chicken overlaps a HopOn and is within 15 pixels of either side
					//(so that it is still technically on the object)
					overlapHopOn(p, h.getRect());
				}

			}

			for (Chicken p : players) {
				nestOverlap(p);//check if chicken reached a nest
				if (!p.overlap && waterRect.overlaps(p.getRect())) {
					p.die();
				}
			}

			//goes outside of loop b/c only runs if the player is on NONE of the floating objects
			//must not let this override hop on rectangle collision

			//-----------------------draw objects onto screen-------------------------
			batch.begin();
			batch.draw(bkg, 0, 0, 800, 1000);

			//draw chickens in bottom left corner (represents # of lives player has left)
			for (int i = 0; i < players.length; i++) {
				players[i].renderLives(batch, i);
			}

			//draw bad guy obstacles
			for (BadGuy b : BadGuys) {
				b.draw(batch);
			}

			//draw hop on objects
			for (HopOn h : HopOns) {
				h.draw(batch);
			}

			//draw the nests at the end
			for (int i = 0; i < homes.length; i++) {
				batch.draw(homes[i].display, homes[i].getRect().x, homes[i].getRect().y, homes[i].getRect().width, homes[i].getRect().height);
			}

			//draw the golden egg
			batch.draw(bonusEgg, ex, ey);

			//draw chicken player last (so that most forward on screen)
			for (int i = 0; i < 2; i++) {
				players[i].render(batch);
			}

			//display score on top left of screen
			font.setColor(1, 1, 1, 1);
			font.draw(batch, "" + players[0].score, 30, 850);
			font.setColor(0, 0, 0, 1);
			font.draw(batch, "" + players[1].score, 700, 850);

			//if chicken dies
			for (Chicken p : players) {
				if (p.isDead()) {
					p.updateFeather(batch, count);
					if (p.getAlpha() == 0) {
						p.setKeysDisable(false);
					}
				}
			}

			batch.end();


		}

		batch.begin();
		//draw in black border around game
		batch.draw(back,-480,0,480,1440);
		batch.draw(back,800,0,480,1440);
		batch.end();

		//to make the spritesheet animals look like they're moving slower,
		//momentarily pause the program 
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			System.out.println(e);
		}


	}

	//move the bonus egg and check for overlap with chicken
	public void bonusEgg(Chicken p) {
		Rectangle bonusRect = new Rectangle(ex, ey, 50, 50);

		if (!bonus) {//as long as egg has not yet been collected, update egg coords so that moves with log
			ex = log.getX() + 30;
			ey = log.getY();
		}

		if (bonusRect.overlaps(p.getRect())) {
			ex = -500;//set egg coordinates outside of screen
			ey = -500;
			p.addToScore(10000);//increase score by 10000 if collect the golden egg
			bonus = true;
		}
	}

	//check if chicken is on an object in the water
	public void overlapHopOn(Chicken p, Rectangle hRect) {
		if (hRect.overlaps(p.getRect()) && hRect.x - p.getX() <= 15 &&
				p.getX() + p.getRect().width - (hRect.x + hRect.width) <= 15) {

			if (p.getFirst()) {
				//flag when player initially moves onto the HopOn,
				//so the x matches with the chicken's previous x-value
				p.setMoveX(p.getX() - hRect.x);
				p.setFirst(false);
			}

			p.setX(p.getMoveX() + hRect.x);
			p.setY(hRect.y);

			p.setOverlap(true);//set overlap = true to prevent from detecting that chicken overlapping waterRect			
		}
	}

	//check if player has reached one of the nest home bases
	public void nestOverlap(Chicken p) {
		for (int i = 0; i < homes.length; i++) {
			//if chicken reaches an empty nest at the end
			if (homes[i].getRect().overlaps(p.getRect())) {

				p.setOverlap(true);//prevent from detecting that chicken is overlapping waterRect
				p.addToScore(50);//increase score by 50 when chicken reaches the end
				p.addHome();
				p.setLives(3);
				homes[i].setHome(String.format("home%d.png", p.num + 1));
				//homeRects[i] = nestRects[i];//set coordinates of homeRect to be same as the corresponding
				//nest that the chicken just reached
				//display image of chicken on nest at the end instead of empty nest

				p.setX((800 - 50) / 2 - 100 + 200 * p.num);
				p.setY(50);

			}
		}
	}


	@Override
	public void show() {

		Gdx.input.setInputProcessor(new InputAdapter() {

			public boolean keyDown(int keycode) {

				if(keycode == Keys.ESCAPE){
					Gdx.app.exit();
				}

				//prevents player from using keys to change position of chicken when it is dead
				if (players[0].areKeysDisabled() == false) {
					players[0].setOverlap(false);
					players[0].setFirst(true);

					//arrow keys for player 1
					if (keycode == Keys.UP) {
						players[0].increaseFrame();
						players[0].setIndex(0);//index of chicken sprite
						players[0].translateY(50);
						players[0].addToScore(10);//as chicken advances 1 row, increase score by 10
					} else if (keycode == Keys.DOWN) {
						if (players[0].getY() > 50) {//prevent chicken from moving out of bottom boundary
							players[0].increaseFrame();
							players[0].setIndex(1);
							players[0].translateY(-50);
						}
					} else if (keycode == Keys.LEFT) {
						players[0].increaseFrame();
						players[0].setIndex(2);
						players[0].translateX(-50);
					} else if (keycode == Keys.RIGHT) {
						players[0].increaseFrame();
						players[0].setIndex(3);
						players[0].translateX(50);
					}
				}


				if (players[1].areKeysDisabled() == false) {
					players[1].setOverlap(false);
					players[1].setFirst(true);

					//arrow keys for player 1
					if (keycode == Keys.W) {
						players[1].increaseFrame();
						players[1].setIndex(0);//index of chicken sprite
						players[1].translateY(50);
						players[1].addToScore(10);//as chicken advances 1 row, increase score by 10
					} else if (keycode == Keys.S) {
						if (players[1].getY() > 50) {//prevent chicken from moving out of bottom boundary
							players[1].increaseFrame();
							players[1].setIndex(1);
							players[1].translateY(-50);
						}
					} else if (keycode == Keys.A) {
						players[1].increaseFrame();
						players[1].setIndex(2);
						players[1].translateX(-50);
					} else if (keycode == Keys.D) {
						players[1].increaseFrame();
						players[1].setIndex(3);
						players[1].translateX(50);
					}
				}

				return true;
			}

			public boolean keyUp(int keycode) {
				if (keycode == Keys.UP || keycode == Keys.DOWN ||
						keycode == Keys.LEFT || keycode == Keys.RIGHT) {
					players[0].increaseFrame();//give the effect that for every move, chicken sprite moves a frame
				}

				if (keycode == Keys.A || keycode == Keys.S ||
						keycode == Keys.A || keycode == Keys.D) {
					players[1].increaseFrame();//give the effect that for every move, chicken sprite moves a frame
				}

				return true;
			}
		});

	}

	@Override
	public void hide () {
		Gdx.input.setInputProcessor(null);
	}
	@Override
	public void resize(int width, int height) {
		System.out.println("resized");
		super.resize(width, height);
	}
}

