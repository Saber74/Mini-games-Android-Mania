//May 10, 2019

//update vines as bkg moves
//arrow keys to move player
//sprite frames for megaman

package com.swing.along;

import java.util.*;
import com.badlogic.gdx.ApplicationAdapter;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;


public class SwingAlong extends ApplicationAdapter implements InputProcessor{
	SpriteBatch batch;
	ShapeRenderer sr;
	int angle = 90;
	int bkgX1, bkgX2;
	
	boolean animation = false;
	
	boolean right = true;//vines start swinging right
	
	Texture bkg;
	LinkedList<Vine> vines;
	Player p1, p2;
	
	float stateTime;
	
	@Override
	public void create () {
		Gdx.graphics.setWindowedMode(1000,800);
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		
		stateTime = 0f;
		
		bkgX1 = 0;
		bkgX2 = 0;
		
		bkg = new Texture("bkg.png");
		
		vines = new LinkedList<Vine>();
		
		
		
		//15 vines
		//each vine is 500 pixels apart
		for(int i=0; i<15; i++){
			vines.add(new Vine("vine.png", 350+i*350, 800));
		}
		
		String[] p1Frames = {"megaman_1.png","megaman_2.png","megaman_3.png","megaman_4.png"};
		p1 = new Player(p1Frames,50,600);
		//p1.setVine();
		
		Gdx.input.setInputProcessor(this);
		
	}

	@Override
	public void render () {
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stateTime += Gdx.graphics.getDeltaTime();
		
		
		batch.begin();
		
		//upper background
		batch.draw(bkg, bkgX1, 400, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/2);
		
		//lower background
		batch.draw(bkg, bkgX2, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/2);
		updateBkg();
		
		updateAngle();

		for(Vine v : vines){
			if(p1.getPlayerRect().overlaps(v.getVine().getBoundingRectangle())){
				p1.setVine(v);
				if(p1.onPlatform){
					p1.setPlatform(false);
				}
			}
		}		
		
		if(animation){
			p1.renderAnimation(stateTime, batch);
			if(p1.isFinishedAnimation(stateTime)){
				animation = false;
			}
		}
		
		else{
			if(p1.onVine()){
				
				p1.setPos(right);
			}
			
			p1.render(batch);
		}
		
		for(Vine v : vines){
			
			if(vines.indexOf(v)%2==0){
				v.setRotation(angle);
			}
			
			else{
				v.setRotation(-angle);
			}
			
			v.render(batch);
		}
		

		batch.end();
		
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.GREEN);
		
		//PENDULUM RECTANGLE 
		sr.rect(p1.getPosRect().x, p1.getPosRect().y, p1.getPosRect().width, p1.getPosRect().height);
		
		//sr.rect(p1.getOriginX()+p1X, p1.getOriginY()+p1Y, 10, 10);
		
		//Note: position of sprite starts at bottom left
		
		//Rectangle pRect = new Rectangle(p1X,p1Y,p1.getWidth(),p1.getHeight());
		
		//sr.setColor(Color.RED);
		//sr.rect(pRect.x, pRect.y, pRect.width, pRect.height);
		
		sr.end();
		
		try{
			Thread.sleep(100);
		}
		
		catch(InterruptedException e){
			System.out.println(e);
		}
	}
	
	public void updateAngle(){
		if(angle==250){
			right = false;
		}
		
		if(angle==110){
			right = true;
		}
		
		if(right){
			angle += 10;
		}
		
		else{
			angle -= 10;
		}
	}
	
	public void updateBkg(){
		if(bkgX1<0){
			for(int i=0; i<bkgX1/-1000+1; i++){
				batch.draw(bkg, 1000*(i+1)+bkgX1, 400, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/2);		
			}
		}
	}

	//implement ALL methods of InputProcessor
	public boolean keyDown(int keycode){
		if(keycode == Keys.RIGHT){
			//p1.changePos(50,0);
			//p1.updateFrame();
			
			animation = true;
			stateTime = 0f;

		}
		return true;
	}
	
	public boolean keyUp(int keycode){
		if(animation){
			bkgX1-=350;
			for(Vine v : vines){
				v.translateX(-350);
			}
		}

		return true;
	}
	
	public boolean keyTyped (char character) {
	    return false;
	}

	public boolean touchDown (int x, int y, int pointer, int button) {
		return false;
	}

	public boolean touchUp (int x, int y, int pointer, int button) {
		return false;
	}

	public boolean touchDragged (int x, int y, int pointer) {
		return false;
	}

	public boolean mouseMoved (int x, int y) {
	    return false;
	}

	public boolean scrolled (int amount) {
	    return false;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}