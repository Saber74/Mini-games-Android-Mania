package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class GamePickScreen extends ScreenAdapter {

    MyGdxGame game;

    float stateTime;

    Texture[] introTextures;
    Animation<Texture> introAnimation;
    Texture currentFrame;


    Texture swingalong=new Texture("swingalong.png");
    Texture stickfight= new Texture("stickfight.png");
    Texture spaceinvaders= new Texture("spaceinvaders.png");
    Texture bombgame= new Texture("bombgame.jpg");
    Texture chickencrossyroad=new Texture("road.jpg");
    int gamenum=1;
    private static final int SWING=1;
    private static final int STICK=2;
    private static final int SPACE=3;
    private static final int BOMB=4;
    private static final int ROAD=5;
    String[] games={"Swing Along","Stick Fight","Space Invaders","Bomb game","Crossy Road"};
    public GamePickScreen(MyGdxGame game) {
        this.game = game;

        stateTime = 0f;

        introTextures = new Texture[27];

        for(int i=0; i<introTextures.length; i++){
            String pic = String.format("pingpong/%d.png",i);
            introTextures[i] = new Texture(pic);
        }

        introAnimation = new Animation<Texture>(0.12f, introTextures);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    if (gamenum == SWING) {
//                game.setScreen(new Swing);
                    } else if (gamenum == STICK) {
                        //                game.setScreen(new Swing);

                    } else if (gamenum == BOMB) {
                        //                game.setScreen(new Swing);

                    } else if (gamenum == SPACE) {
                        //                game.setScreen(new Swing);

                    } else if (gamenum == ROAD) {
                        //                game.setScreen(new Swing);

                    }
                }
                if (keyCode == Input.Keys.LEFT) {
                    if (gamenum > 1) {
                        gamenum -= 1;
                        System.out.println(gamenum);
                    }
                }
                if (keyCode == Input.Keys.RIGHT) {
                    if (gamenum < 5) {
                        gamenum += 1;
                        System.out.println(gamenum);
                    }
                }

                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.4f, .25f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += Gdx.graphics.getDeltaTime();

        game.batch.begin();
        game.batch.draw(swingalong,100,550);
        game.batch.draw(stickfight,1000,550);
        game.batch.draw(spaceinvaders,100,300);
        game.batch.draw(bombgame,1000,300);
        game.batch.draw(chickencrossyroad,550,100);
        game.font.draw(game.batch, "Press space to select a game.", 100+Gdx.graphics.getWidth() * .25f, -100+Gdx.graphics.getHeight() * .25f);
        String currGame=games[gamenum-1];
        String text= String.format("Current Game: "+"%d.%s",gamenum,currGame);
        game.font.draw(game.batch, text, -65+Gdx.graphics.getWidth() * .25f+100, 700);


        currentFrame = introAnimation.getKeyFrame(stateTime, true);
        game.batch.draw(currentFrame,450,300);

        game.batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
