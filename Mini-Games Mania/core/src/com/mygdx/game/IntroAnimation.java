package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class IntroAnimation extends ScreenAdapter {

    private OrthographicCamera cam;

    int animationCounter =0;
    MyGdxGame game;
    float stateTime;
    Texture[] introTextures;
    Animation<Texture> introAnimation;
    Texture currentFrame;

    BitmapFont font;

    public IntroAnimation(MyGdxGame game){

        cam = new OrthographicCamera(1500,1200);

        cam.position.set(675,400,0);
        cam.update();

        this.game=game;
        stateTime = 0f;

        introTextures = new Texture[27];

        for(int i=0; i<introTextures.length; i++){
            String pic = String.format("IntroScreen/pingpong/%d.png",i);
            introTextures[i] = new Texture(pic);
        }

        introAnimation = new Animation<Texture>(0.05f, introTextures);

        font = new BitmapFont(Gdx.files.internal("IntroScreen/Intro.fnt")); //description font

    }
    @Override
    public void render(float delta){

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClearColor(255, 255, 255,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        game.batch.begin();
        currentFrame = introAnimation.getKeyFrame(stateTime, true);
        game.batch.draw(currentFrame,450,300);
        font.draw(game.batch,"Loading...",600,200);
        game.batch.end();
        if(introAnimation.isAnimationFinished(stateTime)){
            animationCounter++;
            stateTime = 0;
        }

        if(animationCounter == 4){
            game.setScreen(new TitleScreen(game));
        }

    }
    @Override
    public void show() {

    }
    @Override
    public void hide() {

    }
}
