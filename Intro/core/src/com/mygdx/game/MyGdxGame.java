package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MyGdxGame extends Game {
//	SpriteBatch batch;
//	Texture img;
//	Texture introTextures[]=new Texture[27];
//	Animation<Texture> anm;
//	float stateTime;
//	Boolean start=true;
////		for(int i=0; i<introTextures.length; i++){
////			String pic = String.format("%d.gif",i);
////			introTextures[(i)] = new Texture(pic);
////		}
////		anm=new Animation<Texture>(0.12f,introTextures);
//		stateTime = 0f;
//		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
////		if(start) {
////			Texture currentFrame = anm.getKeyFrame(stateTime, false);
////			batch.draw(currentFrame, 0, 0);

	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	BitmapFont font;

	@Override
	public void create() {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont(Gdx.files.internal("Intro.fnt")); //description font

		setScreen(new TitleScreen(this));
	}




	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}
}

