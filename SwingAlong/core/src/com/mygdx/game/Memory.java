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
            new Timer().scheduleAtFixedRate(task, 10000, 1);
         }
        else{
            game.font.draw(game.batch,"Now Recreate!",550,400);
            for (int i = 0; i < 7; i++) {
                for (int k = 0; k < 7; k++) {
                    if (player1[i][k] == 1) {
                        shapes.setColor(0.2f, 0.5f, 0.1f, 0);
                        shapes.rect(200 + (50 * k), 225 + 50 * i, 48, 48);
                    }
                    else {
                        shapes.setColor(255, 255, 255, 0);
                        shapes.rect(200 + (50 * k), 225 + 50 * i, 48, 48);
                    }
                    if(player2[i][k]==1){
                        shapes.sedatColor(0.2f, 0.5f, 0.1f, 0);
                        shapes.rect(800 + (50 * k), 225 + 50 * i, 48, 48);
                    }
                    else {
                        shapes.setColor(255, 255, 255, 0);
                        shapes.rect(800 + (50 * k), 225 + 50 * i, 48, 48);
                    }
                }

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
                    if (currentp1x < 7) {
                        currentp1x += 1;
                    }
                }
                if (keyCode == Input.Keys.LEFT) {
                    if (currentp1x > 0) {
                        currentp1x -= 1;
                    }
                }
                if (keyCode == Input.Keys.UP) {
                    if (currentp1y < 7) {
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
                    if (currentp2x < 7) {
                        currentp2x += 1;
                    }
                }
                if (keyCode == Input.Keys.W) {
                    if (currentp2y < 7) {
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
}

