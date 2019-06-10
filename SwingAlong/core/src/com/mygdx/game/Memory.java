package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
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
    float stateTime;
    int time;
    Boolean gamefinished=false;
    Boolean gameStart=true;
    MyGdxGame game;
    ShapeRenderer shapes=new ShapeRenderer();
    private int[][] memorize= new int[7][7];
    private int[][] player1= new int[7][7];
    private int[][] player2=new int [7][7];
    private int currentp1x=0;
    private int currentp1y=0;
    private int currentp2x=0;
    private int currentp2y=0;
    public Memory(MyGdxGame game)
    {
        this.game = game;
        stateTime = 0f;
        time = 15000;//10 seconds - 600 seconds -> 10 minutes
        for(int i=0;i<7;i++){
            for(int k=0;k<7;k++){
                memorize[i][k]=new Random().nextInt(2) ;
                player1[i][k]=1;
                player2[i][k]=1;
            }
        }
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for(int i=0;i<7;i++){
            System.out.println(Arrays.toString(memorize[i]));
        }
        game.batch.begin();
        shapes.begin(ShapeRenderer.ShapeType.Filled);
        if(gameStart) {
            for (int i = 0; i < 7; i++) {
                for (int k = 0; k < 7; k++) {
                    if (memorize[i][k] == 1) {
                        shapes.setColor(0.2f, 0.5f, 0.1f, 0);
                        shapes.rect(525 + (50 * k), 225 + 50 * i, 48, 48);
                    } else {
                        shapes.setColor(255, 255, 255, 0);
                        shapes.rect(525 + (50 * k), 225 + 50 * i, 48, 48);
                    }
                }
            }
            TimerTask task=new TimerTask() {
                @Override
                public void run() {
                    gameStart=false;
                }
            };
            new Timer().scheduleAtFixedRate(task, 10000, 10000);
         }
        else {
            if (!gamefinished) {
                game.font.draw(game.batch, "Now Recreate!", 550, 400);
                for (int i = 0; i < 7; i++) {
                    for (int k = 0; k < 7; k++) {
                        if (player1[i][k] == 1) {
                            shapes.setColor(0.2f, 0.5f, 0.1f, 0);
                            shapes.rect(200 + (50 * k), 225 + 50 * i, 48, 48);
                        } else {
                            shapes.setColor(255, 255, 255, 0);
                            shapes.rect(200 + (50 * k), 225 + 50 * i, 48, 48);
                        }
                        if (player2[i][k] == 1) {
                            shapes.setColor(0.2f, 0.5f, 0.1f, 0);
                            shapes.rect(800 + (50 * k), 225 + 50 * i, 48, 48);
                        } else {
                            shapes.setColor(255, 255, 255, 0);
                            shapes.rect(800 + (50 * k), 225 + 50 * i, 48, 48);
                        }
                    }

                }
                shapes.setColor(0.3f, 0.1f, 0.1f, 0);
                shapes.rect(200 + (50 * currentp1x), 225 + 50 * currentp1y, 28, 28);
                shapes.rect(800 + (50 * currentp2x), 225 + 50 * currentp2y, 28, 28);
                TimerTask task=new TimerTask() {
                    @Override
                    public void run() {
                        gamefinished=true;
                    }
                };
                new Timer().scheduleAtFixedRate(task, 2000, 10000);
            }
            else{
                winnner();
            }
        }
        shapes.end();
        game.batch.end();
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
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
        for(int i=0;i<7;i++){
            for(int k=0;k<7;k++){
                if(memorize[i][k]==player1[i][k]){
                    p1Score++;
                }
                if(memorize[i][k]==player2[i][k]){
                    p2Score++;
                }
            }
        }
        Gdx.gl.glClearColor(.4f, .25f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.font.getData().setScale(1f);
        for (int i = 0; i < 7; i++) {
            for (int k = 0; k < 7; k++) {
                if (player1[i][k] == 1) {
                    shapes.setColor(0.2f, 0.5f, 0.1f, 0);
                    shapes.rect(100 + (50 * k), 50 + 50 * i, 48, 48);
                } else {
                    shapes.setColor(255, 255, 255, 0);
                    shapes.rect(100 + (50 * k), 50 + 50 * i, 48, 48);
                }
                if (player2[i][k] == 1) {
                    shapes.setColor(0.2f, 0.5f, 0.1f, 0);
                    shapes.rect(900 + (50 * k), 50 + 50 * i, 48, 48);
                } else {
                    shapes.setColor(255, 255, 255, 0);
                    shapes.rect(900 + (50 * k), 50 + 50 * i, 48, 48);
                }
                if (memorize[i][k] == 1) {
                    shapes.setColor(0.2f, 0.5f, 0.1f, 0);
                    shapes.rect(475 + (50 * k), 300 + 50 * i, 48, 48);
                } else {
                    shapes.setColor(255, 255, 255, 0);
                    shapes.rect(475 + (50 * k), 300 + 50 * i, 48, 48);
                }
            }
        }
        if(p1Score>p2Score){
            game.font.draw(game.batch,"Player one won",575,1000);
        }
        else if (p1Score<p2Score){
            game.font.draw(game.batch,"Player two won",575,1000);
        }
        else{
            game.font.draw(game.batch,"It's a draw",575,1000);
        }
    }
}

