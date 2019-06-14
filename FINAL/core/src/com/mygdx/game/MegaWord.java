package com.mygdx.game;

//NOTE: input box shows up behind the main game

//Objective: a grid of letters appears on screen
//players will input the longest possible word they can make using the letters
//in the grid (can only use each letter in grid once)

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class MegaWord extends ScreenAdapter {
	
	String fromserver;
    ClientRead client;

	OrthographicCamera cam;
	MyGdxGame game;
	
	boolean play = false;

	//read in all words in dictionary.txt file
	//store all words efficiently in HashSet
	public static HashSet<String> dictionary;

	MyTextInputListener listener1;//for player 1 input
	MyTextInputListener listener2;//for player 2 input

	//whether the word entered by each player is a valid word
	boolean isWord1;
	boolean isWord2;

	Grid grid;//grid of letters to use

	SpriteBatch batch;
	ShapeRenderer sr;
	BitmapFont font;

	//timed game
	Timer timer;
	TimerTask task;
	String timeStamp;
	int time;


	//constructor method
	public MegaWord(MyGdxGame game){

		//set game to full screen, but a separate window,
		//because the input box for users to enter word is behind the game window
		Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width,Gdx.graphics.getDisplayMode().height);
		
		this.game = game;
		batch = game.batch;
		
		client=game.client;


		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		font = new BitmapFont(Gdx.files.internal("IntroScreen/Intro.fnt")); //description font
		font.getData().setScale(2f);

		isWord1 = false;
		isWord2 = false;

		dictionary = new HashSet<String>();

		//read in all words in text file
		readIn("dictionary.txt");

		
		//create new grid of letters 
		grid = new Grid();

		//timer for game
		timeStamp = "";
		time = 15000;//20 seconds
		timer = new Timer(){};
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
		
	}


	@Override
	public void render (float delta) {
		
		//fromServer=client.read();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
		//rules and objective displayed first
		if(time>0 && !play){
			batch.begin();
			font.draw(batch, "Create a word using any of the letters given!", 100, 850);
			font.draw(batch, "Player with the LONGEST \n   word WINS the round!",450, 650);
			font.draw(batch, "Press <SPACE> to begin", 450, 300);
			batch.end();
			
		}


		//game play
		if(time>=0 && play) {
			batch.begin();

			font.draw(batch, timeStamp, 880, 850);
			font.draw(batch, "P1", 300, 850);
			font.draw(batch, "P2", 1400, 850);
		
			int col = 0;
			int row = 0;
			for (int i = 0; i < grid.getLength(); i++) {
				if (i % 5 == 0) {
					col++;
					row = 0;
				} else {
					row++;
				}
				font.draw(batch, "" + grid.getLetter(i), 720 + 100 * row, 700 - 100 * col);
			}

			//check if the word is valid or not
			isWord1 = listener1.isWord(grid, dictionary);
			isWord2 = listener2.isWord(grid, dictionary);
			
			if (listener1.isWord(grid, dictionary)) {
				font.draw(batch, listener1.getWord(), 300, 775);
			}

			if (listener2.isWord(grid, dictionary)) {
				font.draw(batch, listener2.getWord(), 1400, 775);
			}

			batch.end();
		}


		//if time runs out OR both players have inputted a word
		if(time==0 || isWord1 && isWord2){
			time = 0;
			String winner = "";

			//determine winner
			batch.begin();
			
			//neither player entered a word --> TIE
			if(listener1.getWord()==null && listener2.getWord()==null){
				font.draw(batch, "-", 300, 775);
				font.draw(batch, "-", 1400, 775);
				winner = "TIE";
			}

			//only player 2 entered a valid word --> P2 WINS
			else if(listener1.getWord()==null && isWord2){//but listener2's word is not null
				font.draw(batch, "-", 300, 775);
				winner = "P2 WINS";
			}

			//only player 1 entered a valid word --> P1 WINS
			else if(listener2.getWord()==null && isWord1){
				font.draw(batch, "-", 1400, 775);
				winner = "P1 WINS";
			}

			//both players' words same length --> TIE
			else if (isWord1 && isWord2 && listener1.getWord().length() == listener2.getWord().length()){
				winner = "It's a TIE";
			}

			//player 1 has longer word --> P1 WINS
			else if(isWord1 && isWord2 && listener1.getWord().length()>listener2.getWord().length()){
				winner = "P1 WINS";
			}

			//player 2 has longer word --> P2 WINS
			else if(isWord1 && isWord2 && listener1.getWord().length()<listener2.getWord().length()){
				winner = "P2 WINS";
			}


			font.draw(batch,winner,800,1000);
			
			batch.end();
			

		}

	}
	

	//-------------read in file------------------
	public static void readIn(String filename){
		try{
			//read in file
			Scanner inFile = new Scanner(new BufferedReader(new FileReader(filename)));

			//read in each new word of a line
			while(inFile.hasNextLine()){
				//add each word to the HashSet
				dictionary.add(inFile.nextLine());
			}
			
			inFile.close();

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
				//press <ESCAPE> at any time to return to game screen
				if (keycode == Input.Keys.ESCAPE) {
					game.setScreen(new GamePickScreen(game));
				}
				//press <SPACE> to start game
				else if(keycode == Input.Keys.SPACE && !play){
					play = true;
					timer.scheduleAtFixedRate(task,0,1);
					listen();
				}
				return true;
			}
		});
	}

	@Override
	public void hide(){

	}
}
