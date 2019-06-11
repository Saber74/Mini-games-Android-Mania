package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class GamePickScreen extends ScreenAdapter {

    OrthographicCamera cam;
    MyGdxGame game;

    ShapeRenderer sr;

    float stateTime;


    Texture swingAlong;
    //Texture stickfight;
    Texture spaceInvaders;
    Texture megaBomb;
    Texture chickenCrossyRoad;
    Texture megaWord;
    Texture choose;

    Texture megaMemory;

    int gameNum;

    private static final int SWING=1;
    private static final int ROAD=2;
    private static final int WORD=3;
    private static final int SPACE=4;
    private static final int BOMB=5;
    private static final int MEMORY=6;

    String[] games;


    public GamePickScreen(MyGdxGame game) {

        cam = new OrthographicCamera(1000,800);

        cam.position.set(675,400,0);
        cam.update();

        this.game = game;

        sr = new ShapeRenderer();

        gameNum=1;

        swingAlong = new Texture("IntroScreen/swingalong.png");
        megaWord = new Texture("IntroScreen/word.png");
        megaMemory= new Texture("IntroScreen/Memory.png");
        spaceInvaders = new Texture("IntroScreen/spaceinvaders.png");
        megaBomb = new Texture("IntroScreen/bomb.png");
        chickenCrossyRoad = new Texture("IntroScreen/crossyroad.png");

        choose = new Texture("IntroScreen/chooseBox.png");
	

        games= new String[]{"Swing Along","Crossy Road","Mega Words","Space Invaders","Mega Bomb","Memory Game"};


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {

                if(keyCode == Input.Keys.ESCAPE){
                    Gdx.app.exit();
                }

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
        stateTime += Gdx.graphics.getDeltaTime();

        game.batch.begin();

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

        game.batch.draw(swingAlong,280,360,200,250);
        game.batch.draw(megaWord,580,360,200,250);
        game.batch.draw(megaMemory,880,80,200,250);
        game.batch.draw(spaceInvaders,880,360,200,250);
        game.batch.draw(megaBomb,280,80,200,250);
        game.batch.draw(chickenCrossyRoad,580,80,200,250);
        game.font.draw(game.batch, "Press <SPACE> to select a game.", 350, 750);
        String currGame=games[gameNum-1];
        String text= String.format("Current Game: %d. %s",gameNum,currGame);
        game.font.draw(game.batch, text, 370, 700);
        game.batch.end();


    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

}
