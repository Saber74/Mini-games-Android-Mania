//ICS4U FSE
//ANITA HU? / NIZAR ALRIFAI
//Code for intro to the memory game filled with instructions
package com.mygdx.game;
import com.ClientRead;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
public class MemoryIntro extends ScreenAdapter {
    String fromserver;
    ClientRead client;
    MyGdxGame game;
    public MemoryIntro(MyGdxGame game) {
        this.game = game;
        client=game.client;
    }
    @Override
    public void render(float delta) { //using game we set up a new screen
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //displayig new background and outputing text
        game.batch.begin();
        game.font.getData().setScale(1.5f);
        game.font.draw(game.batch,"Memory Grids",475,700);
        game.font.getData().setScale(0.5f);
        game.font.draw(game.batch,"Remember the grid shown as best as you can then try to replicate it",350,400);
        game.font.draw(game.batch,"Press space to begin",575,100);
        game.batch.end();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) { //if space is pressed move on to the actual game screen
                    game.setScreen(new Memory(game));
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
