package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Memory extends ScreenAdapter {
    OrthographicCamera cam;
    float stateTime;
    int memorizeTime;
    int recreateTime;
    Boolean gameFinished=false;
    Boolean gameStart=true;
    MyGdxGame game;

    BitmapFont font;

    ShapeRenderer shapes=new ShapeRenderer();
    private int[][] memorize= new int[7][7];
    private int[][] player1= new int[7][7];
    private int[][] player2=new int [7][7];
    private int currentp1x=0;
    private int currentp1y=0;
    private int currentp2x=0;
    private int currentp2y=0;

    Timer memorizeTimer;
    Timer recreateTimer;
    TimerTask memorizeTask;
    TimerTask recreateTask;
    String timeStamp;

    public Memory(MyGdxGame game)
    {

        /*
        cam = new OrthographicCamera(1600,1000);
        cam.position.set(600,800,0);
        cam.update();
        */
        Gdx.graphics.setWindowedMode(Gdx.graphics.getDisplayMode().width,Gdx.graphics.getDisplayMode().height);
        this.game = game;
        font = new BitmapFont(Gdx.files.internal("android/assets/IntroScreen/Intro.fnt"));

        for(int i=0;i<7;i++){
            for(int k=0;k<7;k++){
                memorize[i][k]=new Random().nextInt(2) ;
                player1[i][k]=0;
                player2[i][k]=0;
            }
        }

        timeStamp = "";
        memorizeTimer = new Timer();
        recreateTimer = new Timer();

        stateTime = 0f;
        memorizeTime = 15000;
        memorizeTask=new TimerTask() {
            @Override
            public void run() {
                if(memorizeTime>0) {
                    memorizeTime--;
                    int min = (int) (memorizeTime / 60000);
                    int sec = (int) ((memorizeTime- min * 60000)/1000);
                    timeStamp = String.format("%d:%d", min, sec);
                    if(sec<10){
                        timeStamp = String.format("%d:0%d", min, sec);
                    }
                }

                if (memorizeTime == 0){
                    gameStart=false;

                    recreateTimer.scheduleAtFixedRate(recreateTask, 0, 1);
                }
            }
        };

        memorizeTimer.scheduleAtFixedRate(memorizeTask, 0, 1);

        recreateTime = 20000;
        recreateTask = new TimerTask() {
            @Override
            public void run() {
                if(recreateTime>0) {
                    recreateTime--;
                    int min = (int) (recreateTime/ 60000);
                    int sec = (int) ((recreateTime - min * 60000)/1000);
                    timeStamp = String.format("%d:%d", min, sec);
                    if(sec<10){
                        timeStamp = String.format("%d:0%d", min, sec);
                    }
                    System.out.println(timeStamp);
                }

                if (recreateTime == 0){
                    gameFinished=true;
                }
            }
        };



    }

    @Override
    public void render(float delta) {

        //cam.update();
        //game.batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClearColor(0.3f, 0.1f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for(int i=0;i<7;i++){
            System.out.println(Arrays.toString(memorize[i]));
        }

        if(gameStart) {
            shapes.begin(ShapeRenderer.ShapeType.Filled);
            for (int i = 0; i < 7; i++) {
                for (int k = 0; k < 7; k++) {
                    if (memorize[i][k] == 1) {
                        shapes.setColor(0.2f, 0.5f, 0.1f, 0);
                        shapes.rect(Gdx.graphics.getWidth()/2-175 + (50 * k), 225 + 50 * i, 48, 48);
                    } else {
                        shapes.setColor(255, 255, 255, 0);
                        shapes.rect(Gdx.graphics.getWidth()/2-175 + (50 * k), 225 + 50 * i, 48, 48);
                    }
                }
            }
            shapes.end();

            game.batch.begin();
            font.draw(game.batch, timeStamp, Gdx.graphics.getWidth()/2-330, 600);
            game.batch.end();
         }

        else {
            if (!gameFinished) {
                game.batch.begin();
                font.draw(game.batch, "Now Recreate!", Gdx.graphics.getWidth()/2-450, 700);
                font.draw(game.batch, timeStamp, Gdx.graphics.getWidth()/2-330, 600);
                game.batch.end();

                shapes.begin(ShapeRenderer.ShapeType.Filled);
                for (int i = 0; i < 7; i++) {
                    for (int k = 0; k < 7; k++) {
                        if (player1[i][k] == 1) {
                            shapes.setColor(0.2f, 0.5f, 0.1f, 0);
                            shapes.rect(Gdx.graphics.getWidth()/2-600 + (50 * k), 225 + 50 * i, 48, 48);
                        } else {
                            shapes.setColor(255, 255, 255, 0);
                            shapes.rect(Gdx.graphics.getWidth()/2-600 + (50 * k), 225 + 50 * i, 48, 48);
                        }
                        if (player2[i][k] == 1) {
                            shapes.setColor(0.2f, 0.5f, 0.1f, 0);
                            shapes.rect(Gdx.graphics.getWidth()/2+200 + (50 * k), 225 + 50 * i, 48, 48);
                        } else {
                            shapes.setColor(255, 255, 255, 0);
                            shapes.rect(Gdx.graphics.getWidth()/2+200 + (50 * k), 225 + 50 * i, 48, 48);
                        }
                    }
                }
                shapes.setColor(0.3f, 0.1f, 0.1f, 0);
                shapes.rect(Gdx.graphics.getWidth()/2-600+12 + (50 * currentp1x), 235 + 50 * currentp1y, 24, 24);
                shapes.rect(Gdx.graphics.getWidth()/2+200+12 + (50 * currentp2x), 235 + 50 * currentp2y, 24, 24);
                shapes.end();


            }
            else{
                winnner();
            }
        }

    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if(keyCode == Input.Keys.ESCAPE){
                    game.setScreen(new GamePickScreen(game));
                }
                if (!gameFinished) {
                    if (keyCode == Input.Keys.RIGHT) {
                        if (currentp1x < 6) {
                            currentp1x += 1;
                        }
                    }
                    if (keyCode == Input.Keys.LEFT) {
                        if (currentp1x > 0) {
                            currentp1x -= 1;
                        }
                    }
                    if (keyCode == Input.Keys.UP) {
                        if (currentp1y < 6) {
                            currentp1y++;
                        }
                    }
                    if (keyCode == Input.Keys.DOWN) {
                        if (currentp1y > 0) {
                            currentp1y--;
                        }
                    }
                    if (keyCode == Input.Keys.SPACE) {
                        if (player1[currentp1y][currentp1x] == 0) {
                            player1[currentp1y][currentp1x] = 1;
                        } else {
                            player1[currentp1y][currentp1x] = 0;
                        }
                    }
                    if (keyCode == Input.Keys.A) {
                        if (currentp2x > 0) {
                            currentp2x -= 1;
                        }
                    }
                    if (keyCode == Input.Keys.D) {
                        if (currentp2x < 6) {
                            currentp2x += 1;
                        }
                    }
                    if (keyCode == Input.Keys.W) {
                        if (currentp2y < 6) {
                            currentp2y++;
                        }
                    }
                    if (keyCode == Input.Keys.S) {
                        if (currentp2y > 0) {
                            currentp2y--;
                        }
                    }
                    if (keyCode == Input.Keys.SHIFT_LEFT) {
                        if (player2[currentp2y][currentp2x] == 0) {
                            player2[currentp2y][currentp2x] = 1;
                        } else {
                            player2[currentp2y][currentp2x] = 0;
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
    public void winnner(){
        int p1Score=0;
        int p2Score=0;
        for(int i=0;i<7;i++) {
            for (int k = 0; k < 7; k++) {
                if (memorize[i][k] == player1[i][k]) {
                    p1Score++;
                }
                if (memorize[i][k] == player2[i][k]) {
                    p2Score++;
                }
            }
        }
        Gdx.gl.glClearColor(0.3f, 0.1f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.font.getData().setScale(2f);
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < 7; i++) {
            for (int k = 0; k < 7; k++) {
                if (player1[i][k] == 1) {
                    shapes.setColor(0.2f, 0.5f, 0.1f, 0);
                    shapes.rect(Gdx.graphics.getWidth()/2-600 + (50 * k), 225 + 50 * i, 48, 48);
                } else {
                    shapes.setColor(255, 255, 255, 0);
                    shapes.rect(Gdx.graphics.getWidth()/2-600 + (50 * k), 225 + 50 * i, 48, 48);
                }
                if (player2[i][k] == 1) {
                    shapes.setColor(0.2f, 0.5f, 0.1f, 0);
                    shapes.rect(Gdx.graphics.getWidth()/2+200 + (50 * k), 225 + 50 * i, 48, 48);
                } else {
                    shapes.setColor(255, 255, 255, 0);
                    shapes.rect(Gdx.graphics.getWidth()/2+200 + (50 * k), 225 + 50 * i, 48, 48);
                }
                if (memorize[i][k] == 1) {
                    shapes.setColor(0.2f, 0.5f, 0.1f, 0);
                    shapes.rect(Gdx.graphics.getWidth()/2-185 + (50 * k), 400 + 50 * i, 48, 48);
                } else {
                    shapes.setColor(255, 255, 255, 0);
                    shapes.rect(Gdx.graphics.getWidth()/2-185 + (50 * k), 400 + 50 * i, 48, 48);
                }
            }
        }
        shapes.end();

        game.batch.begin();
        font.draw(game.batch,"Original",Gdx.graphics.getWidth()/2-375,600);
        if(p1Score>p2Score){
            font.draw(game.batch,"Player one won",Gdx.graphics.getWidth()/2-400,700);
        }
        else if (p1Score<p2Score){
            font.draw(game.batch,"Player two won",Gdx.graphics.getWidth()/2-400,700);
        }
        else{
            font.draw(game.batch,"It's a draw",Gdx.graphics.getWidth()/2-400,700);
        }
        game.batch.end();
    }
}

