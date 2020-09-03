package com.mygdx.game;
import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MyGdxGame extends Game {

	SpriteBatch batch;
	ShapeRenderer shapeRenderer;
	BitmapFont font;
	ClientRead client;

	@Override
	public void create() {
		
		client = new ClientRead();
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont(Gdx.files.internal("IntroScreen/Intro.fnt")); //description font

		setScreen(new IntroAnimation(this));
		//setScreen(new Memory(this));
	}




	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}
	@Override
	public void resize(int width, int height) {
		System.out.println("resized");
		super.resize(width, height);
	}
}

