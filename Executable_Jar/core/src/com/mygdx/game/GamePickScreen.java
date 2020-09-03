package com.mygdx.game;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GamePickScreen extends ScreenAdapter {

	ClientRead client;
    OrthographicCamera cam;
    MyGdxGame game;//use Game Class interface to go from one screen (class) to another
    BitmapFont font;

    ShapeRenderer sr;

    Texture swingAlong;
    Texture spaceInvaders;
    Texture megaBomb;
    Texture chickenCrossyRoad;
    Texture megaWord;
    Texture choose;

    Texture megaMemory;

    int gameNum;

    //constants to determine which game is being chosen
    private static final int SWING=1;
    private static final int ROAD=2;
    private static final int WORD=3;
    private static final int SPACE=4;
    private static final int BOMB=5;
    private static final int MEMORY=6;

    String[] games;
    
    int p1Score;
    int p2Score;


    public GamePickScreen(MyGdxGame game) {

    	
        cam = new OrthographicCamera(1000,800);

        cam.position.set(675,400,0);
        cam.update();

        this.game = game;
        client = game.client;
        font = new BitmapFont(Gdx.files.internal("IntroScreen/Intro.fnt"));
        
        sr = new ShapeRenderer();

        gameNum=1;//start with the first game being the chosen one

        swingAlong = new Texture("IntroScreen/swingalong.png");
        megaWord = new Texture("IntroScreen/word.png");
        megaMemory= new Texture("IntroScreen/Memory.png");
        spaceInvaders = new Texture("IntroScreen/spaceinvaders.png");
        megaBomb = new Texture("IntroScreen/bomb.png");
        chickenCrossyRoad = new Texture("IntroScreen/crossyroad.png");

        //white border drawn around the selected game
        choose = new Texture("IntroScreen/chooseBox.png");
	
        //array of game names
        games= new String[]{"Swing Along","Crossy Road","Mega Words","Space Invaders","Mega Bomb","Memory Game"};


    }

    @Override
    public void show() {
        //check which game will be selected
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {

                //end application if press ESCAPE
                if(keyCode == Input.Keys.ESCAPE){
                	client.close();
                    Gdx.app.exit();
                }

                //press SPACE to start the selected game
                if (keyCode == Input.Keys.SPACE) {
                    if (gameNum == SWING) {
                        game.setScreen(new SwingAlong(game));

                    } else if (gameNum == WORD) {
                        game.setScreen(new MegaWord(game));

                    } else if (gameNum == BOMB) {
                        game.setScreen(new MegaBomb(game));

                    } else if (gameNum == SPACE) {
                        game.setScreen(new Space_Main(game));

                    } else if (gameNum == ROAD) {
                        game.setScreen(new ChickenCrossyRoad(game));

                    } else if (gameNum == MEMORY) {
                        game.setScreen(new MemoryIntro(game));

                    }
                }

                //move left and right to go through games
                if (keyCode == Input.Keys.LEFT) {
                    if (gameNum > 1) {
                        gameNum -= 1;
                        System.out.println(gameNum);
                    }
                }
                if (keyCode == Input.Keys.RIGHT) {
                    if (gameNum < games.length) {
                        gameNum += 1;
                        System.out.println(gameNum);
                    }
                }

                return true;
            }
        });
    }

    @Override
    public void render(float delta) {

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClearColor(.4f, .25f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        //draw the white box behind the selected game
        if (gameNum == SWING) {
            game.batch.draw(choose,260,340,240,290);
        } else if (gameNum == ROAD) {
            game.batch.draw(choose,560,340,240,290);

        } else if (gameNum == WORD) {
            game.batch.draw(choose,860,340,240,290);

        } else if (gameNum == SPACE) {
            game.batch.draw(choose,260,60,240,290);

        } else if (gameNum == BOMB) {
            game.batch.draw(choose,560,60,240,290);

        } else if (gameNum == MEMORY){
            game.batch.draw(choose,860,60,240,290);
        }

        //draw the screenshot textures of the games on the screen
        game.batch.draw(swingAlong,280,360,200,250);
        game.batch.draw(chickenCrossyRoad,580,360,200,250);
        game.batch.draw(megaWord,880,360,200,250);
        game.batch.draw(spaceInvaders,280,80,200,250);
        game.batch.draw(megaBomb,580,80,200,250);
        game.batch.draw(megaMemory,880,80,200,250);
        font.draw(game.batch, "Press <SPACE> to select a game.", 350, 750);
        String currGame=games[gameNum-1];
        String text= String.format("Current Game: %d. %s",gameNum,currGame);
        font.draw(game.batch, text, 370, 700);
      
        game.batch.end();


    }
    

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

}
