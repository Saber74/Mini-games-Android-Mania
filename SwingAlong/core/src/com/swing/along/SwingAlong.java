package com.swing.along;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class SwingAlong extends ApplicationAdapter{
	SpriteBatch batch;
	ShapeRenderer sr;
	//Texture img;
	
	@Override
	public void create () {
		Gdx.graphics.setWindowedMode(1000,800);
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		//img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//batch.draw(img, 0, 0);
		batch.end();
		
		sr.begin(ShapeType.Line);
		sr.identity();
		sr.translate(20, 12, 2);
		sr.rotate(0, 0, 1, 90);
		sr.rect(200,200,400,400);
		sr.end();
		
		
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}
