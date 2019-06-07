package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class IntroAnimation extends ScreenAdapter {
    int animatiomcounter =0;
    MyGdxGame game;
    float stateTime;
    Texture[] introTextures;
    Animation<Texture> introAnimation;
    Texture currentFrame;
    public IntroAnimation(MyGdxGame game){
        this.game=game;
        stateTime = 0f;

        introTextures = new Texture[27];

        for(int i=0; i<introTextures.length; i++){
            String pic = String.format("IntroScreen/pingpong/%d.png",i);
            introTextures[i] = new Texture(pic);
        }

        introAnimation = new Animation<Texture>(0.05f, introTextures);
    }
    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(255, 255, 255,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        game.batch.begin();
        currentFrame = introAnimation.getKeyFrame(stateTime, true);
        game.batch.draw(currentFrame,450,300);
        game.batch.end();
        if(introAnimation.isAnimationFinished(stateTime)){
            System.out.println("done");
        }

    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE||animatiomcounter==2) {
                    game.setScreen(new TitleScreen(game));
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
