package com.mygdx.game;

//6 wires: 1 wire will defuse the bomb. The first person to choose the correct wire WINS.
//OR the person who is up to choose once the time runs out loses

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class MegaWord extends ScreenAdapter {

	OrthographicCamera cam;
	MyGdxGame game;

	public static final int P1 = 1;
	public static final int P2 = 2;
	public static HashSet<String> dictionary;

	MyTextInputListener listener1;//for player 1 input
	MyTextInputListener listener2;//for player 2 input

	int score1;
	int score2;

	boolean isWord1;
	boolean isWord2;

	int rounds;

	boolean first1;
	boolean first2;

	Grid grid;

	SpriteBatch batch;
	ShapeRenderer sr;
	BitmapFont font;

	Timer timer;
	TimerTask task;
	String timeStamp;
	int time;



	public MegaWord(MyGdxGame game){

		cam = new OrthographicCamera(1000,600);

		cam.position.set(400,300,0);
		cam.update();

		this.game = game;
		batch = game.batch;


		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		font = new BitmapFont(Gdx.files.internal("android/assets/IntroScreen/Intro.fnt")); //description font

		score1 = 0;
		score2 = 0;

		isWord1 = false;
		isWord2 = false;

		rounds = 5;
		first1 = true;
		first2 = true;


		dictionary = new HashSet<String>();

		readIn("dictionary.txt");

		listen();


		grid = new Grid();

		System.out.println(grid);

		timeStamp = "";
		time = 15000;//15 seconds
		timer = new Timer();
		task = new TimerTask(){
			public void run(){
				if(time>0) {
					time--;
					int min = (int) (time / 60000);
					int sec = (int) ((time - min * 60000)/1000);
					timeStamp = String.format("%d:%d", min, sec);
					if(sec<10){
						timeStamp = String.format("%d:0%d", min, sec);
					}

				}
			}
		};

		timer.scheduleAtFixedRate(task,0,1);

	}

	//go through the status and if int==1, batch.draw the coloured wire. else, batch.draw the grey wire

	@Override
	public void render (float delta) {

		cam.update();
		batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/*
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.out.println(e);
		}*/


		if(rounds>0) {
			batch.begin();

			font.draw(batch, timeStamp, 350, 550);
			font.draw(batch, String.format("P1: %d", score1), 100, 550);
			font.draw(batch, String.format("P2: %d", score2), 600, 550);

			int col = 0;
			int row = 0;
			for (int i = 0; i < grid.getLength(); i++) {
				if (i % 5 == 0) {
					col++;
					row = 0;
				} else {
					row++;
				}
				font.draw(batch, "" + grid.getLetter(i), 240 + 70 * row, 470 - 70 * col);
			}

			if(!first1){
				isWord1 = listener1.isWord(grid, dictionary);
			}
			if(!first2){
				isWord2 = listener2.isWord(grid, dictionary);
			}


			if (isWord1) {
				font.draw(batch, listener1.getWord(), 100, 500);
				first1 = true;
			}

			if (isWord2) {
				font.draw(batch, listener2.getWord(), 600, 500);
				first2 = true;
			}

			batch.end();
		}


		if(time==0 && rounds>0 || isWord1 && isWord2 && rounds>0){
			String winner = "";

			batch.begin();
			if(listener1.getWord()==null && listener2.getWord()==null){
				font.draw(batch, "-", 100, 500);
				font.draw(batch, "-", 600, 500);
				winner = "TIE";
			}

			else if(listener1.getWord()==null && isWord2){//but listener2's word is not null
				score2++;
				font.draw(batch, "-", 100, 500);
				winner = "P2 WINS";
			}

			else if(listener2.getWord()==null && isWord1){
				score1++;
				font.draw(batch, "-", 600, 500);
				winner = "P1 WINS";
			}

			else if (isWord1 && isWord2 && listener1.getWord().length() == listener2.getWord().length()){
				winner = "TIE";
			}

			else if(isWord1 && isWord2 && listener1.getWord().length()>listener2.getWord().length()){
				score1++;
				winner = "P1 WINS";
			}

			else if(isWord1 && isWord2 && listener1.getWord().length()<listener2.getWord().length()){
				score2++;
				winner = "P2 WINS";
			}


			font.draw(batch,winner,300,500);

			batch.end();

			time = 15000;

			grid = new Grid();

			rounds--;

			first1 = false;
			first2 = false;

			if(rounds>0){
				listen();
			}

		}

		if(rounds==0){
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			String end = "";
			if(score2>score1){
				end = "PLAYER 2 WINS!";
			}

			if(score2<score1){
				end = "PLAYER 1 WINS!";
			}

			else{
				end = "TIE";
			}

			batch.begin();
			font.draw(batch,end,220,320);
			batch.end();

		}


	}

	//-------------read in file------------------
	public static void readIn(String filename){
		try{
			Scanner inFile = new Scanner(new BufferedReader(new FileReader(filename)));

			//read in each new word of a line
			while(inFile.hasNextLine()){
				dictionary.add(inFile.nextLine());
			}

		}

		//separate code and exceptions using try and catch
		catch(IOException ex){//if exception is found
			System.out.println("Umm, where is dictionary.txt");
		}
	}

	public void listen(){
		listener2 = new MyTextInputListener();
		Gdx.input.getTextInput(listener2, "Player 2", "", "");

		listener1 = new MyTextInputListener();
		Gdx.input.getTextInput(listener1, "Player 1", "", "");

	}

	public void show(){

		Gdx.input.setInputProcessor(new InputAdapter() {
			public boolean keyDown(int keycode) {
				if (keycode == Input.Keys.ESCAPE) {
					game.setScreen(new GamePickScreen(game));
				}
				return true;
			}
		});
	}

	@Override
	public void hide(){

	}
}
