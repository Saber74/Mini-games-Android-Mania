package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class TitleScreen extends ScreenAdapter {

    OrthographicCamera cam;

    MyGdxGame game;
    Texture logo= new Texture("IntroScreen/logo.png");

    public TitleScreen(MyGdxGame game) {

        cam = new OrthographicCamera(1000,800);

        cam.position.set(675,400,0);
        cam.update();

        this.game = game;


    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new GamePickScreen(game));
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
        game.batch.draw(logo,175,0);
        game.font.draw(game.batch, "Press <SPACE> to select a game.", 350, 150);


        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

}