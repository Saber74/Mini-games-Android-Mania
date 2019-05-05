//May 10, 2019

//update vines as bkg moves
//arrow keys to move player
//sprite frames for megaman

package com.swing.along;


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
	
	boolean right = true;//vines start swinging right
	
	Texture bkg;
	Vine[] vines;
	Player p1, p2;
	
	@Override
	public void create () {
		Gdx.graphics.setWindowedMode(1000,800);
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		
		bkgX1 = 0;
		bkgX2 = 0;
		
		bkg = new Texture("bkg.png");
		
		vines = new Vine[15];
		
		for(int i=0; i<vines.length; i++){
			vines[i] = new Vine("vine.png");
			vines[i].setPos(400+i*500,800);
			
		}
		
		p1 = new Player("Megaman.png",100,600);
		//p1.setVine();
		
		Gdx.input.setInputProcessor(this);
		
	}

	@Override
	public void render () {
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.begin();
		
		//upper background
		batch.draw(bkg, bkgX1, 400, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/2);
		
		//lower background
		batch.draw(bkg, bkgX2, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/2);
		updateBkg();
		
		updateAngle();
		
		if(p1.onVine()){
			p1.setRotation(angle-180);
			p1.setPos();
		}

		p1.render(batch);
		
		for(int i=0; i<vines.length; i++){
			
			if(i%2==0){
				vines[i].setRotation(angle);
			}
			
			else{
				vines[i].setRotation(-angle);
			}
			
			vines[i].render(batch);
		}
		

		batch.end();
		
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.GREEN);
		
		//sr.rect(p1.getOriginX()+p1X, p1.getOriginY()+p1Y, 10, 10);
		
		//Note: position of sprite starts at bottom left
		
		//Rectangle pRect = new Rectangle(p1X,p1Y,p1.getWidth(),p1.getHeight());
		
		//sr.setColor(Color.RED);
		//sr.rect(pRect.x, pRect.y, pRect.width, pRect.height);
		
		sr.end();
		
		try{
			Thread.sleep(50);
		}
		
		catch(InterruptedException e){
			System.out.println(e);
		}
	}
	
	public void updateAngle(){
		if(angle<=270 && right){
			angle += 10;
		}
		
		if(angle==270){
			right = false;
		}
		
		if(angle<=270 && !right){
			angle -= 10;
		}
		
		if(angle==90 && !right){
			right = true;
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
			bkgX1 -= 20;
			for(Vine vine : vines){
				vine.changeX(-20);
			}
		}
		return true;
	}
	
	public boolean keyUp(int keycode){
		//if(keycode == Keys.UP || keycode == Keys.DOWN ||
		
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