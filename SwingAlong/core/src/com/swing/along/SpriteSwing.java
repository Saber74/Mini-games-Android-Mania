/*
 * package com.swing.along;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Rectangle;


public class Player {
	//stores player's x and y value
	//stores the vine that the current player is on
	boolean onPlatform;
	Texture startPlayer;
	Texture[] jumpTextures;
	Texture currentFrame;
	float x,y;
	Vine vine;
	
	int frame = 0;
	
	Animation<Texture> jumpAnimation;
	
	//constructor method
	public Player(String name, int n, float x, float y){
		
		onPlatform = true;
		
		startPlayer = new Texture(String.format("%s%d.png", name,0));
		
		jumpTextures = new Texture[n-1];
		//don't include first image in play
		
		for(int i=1; i<jumpTextures.length; i++){
			jumpTextures[i] = new Texture(String.format("%s%d.png",name,i));
		}
		
		//player = new Sprite(playerTextures[0]);
		//player.setCenter(100, 100);
		
		jumpAnimation = new Animation<Texture>(0.12f, jumpTextures);
		
		//starting position on platform
		this.x = x;
		this.y = y;
		
		//player.setPosition(x, y);
	}
	
	public void setPlatform(boolean onPlat){
		onPlatform = onPlat;
	}
	
	public void setVine(Vine v){
		vine = v;
	}
	
	
	public boolean onVine(){
		if(vine!=null){
			return true;
		}
		
		return false;
	}
	
	public Vine getVine(){
		return vine;
	}
	
	//Method overloading	
	public void setPos(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	
	public void setPos(boolean right){
		float width = jumpTextures[0].getWidth();
		float height = jumpTextures[0].getHeight();
		if(vine.getRotation()>0){
			if(right){
				x = (float)(vine.getX()-width*0.72-vine.getHeight()*Math.cos(Math.toRadians(vine.getRotation()-88)));
				y = (float)(vine.getY()-height*0.72-vine.getHeight()*Math.sin(Math.toRadians(vine.getRotation()-88)));
			}
			
			else{
				x = (float)(vine.getX()-width*0.72-vine.getHeight()*Math.cos(Math.toRadians(vine.getRotation()-92)));
				y = (float)(vine.getY()-height*0.72-vine.getHeight()*Math.sin(Math.toRadians(vine.getRotation()-92)));
			}
		}
		
		else{
			if(right){
				x = (float)(vine.getX()-width*0.72-vine.getHeight()*Math.cos(Math.toRadians(vine.getRotation()-92)));
				y = (float)(vine.getY()-height*0.72-vine.getHeight()*Math.sin(Math.toRadians(vine.getRotation()-92)));
			}
			
			else{
				x = (float)(vine.getX()-width*0.72-vine.getHeight()*Math.cos(Math.toRadians(vine.getRotation()-88)));
				y = (float)(vine.getY()-height*0.72-vine.getHeight()*Math.sin(Math.toRadians(vine.getRotation()-88)));
			}
		}
	}
	
	public void translateX(int tx){
		x+=tx;
	}
	
	
	public void render(SpriteBatch batch){
		//player.draw(batch);
		if(onPlatform){
			batch.draw(startPlayer,x,y,110,110);
		}
		else{
			batch.draw(jumpTextures[0],x,y);
		}
		
	}
	
	public void renderAnimation(float time, SpriteBatch batch){
		currentFrame = jumpAnimation.getKeyFrame(time, true);
		/*
		if(onPlatform){
			x+=100;
		}*/
		//x+=100;
		//y+=50;
		/*
		batch.draw(currentFrame,x,y);
	}
	
	public boolean isFinishedAnimation(float stateTime){
		
		if(jumpAnimation.isAnimationFinished(stateTime)){
			return true;
		}
		return false;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public Rectangle getPlayerRect(){
		if(onPlatform){
			return new Rectangle(x,y,startPlayer.getWidth(),startPlayer.getHeight());
		}
		
		else{
			return new Rectangle(x,y,jumpTextures[0].getWidth(),jumpTextures[0].getHeight());
		}
	}
	
	public Rectangle getPosRect(){
		return new Rectangle(x,y,10,10);
	}
	
	public float getWidth(){
		if(onPlatform){
			return startPlayer.getWidth();
		}
		
		else{
			return jumpTextures[0].getWidth();
		}
	}
	
}
*/




/*
 * package com.swing.along;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Player {
	public int x,y;
	public World world;
	public Body man;
	public Vector2 position;
	public Sprite manSprite;
	
	public Player(World world) {
	    this.world = world;
	    defineSprite();
	}
	public void defineSprite(){
		
		BodyDef bodyDef = new BodyDef();
        
        //man - swinging on end of vine
        bodyDef.type = BodyType.DynamicBody;
        //bodyDef.position.set(1000,80);
        bodyDef.position.set(-2f,5);
        
        man = world.createBody(bodyDef); 
        
        //create a sprite to hang at end of vine
        manSprite = new Sprite(new Texture("Megaman.png"));
        manSprite.setOrigin(manSprite.getWidth()/2, manSprite.getHeight()/2);
        manSprite.setPosition(0,0);
        man.setUserData(manSprite);
        
        //position = man.getPosition();

	   
	}
	
	// Then we can add a simple render method to our player class.
	public void render(SpriteBatch batch) {
	    // First we position and rotate the sprite correctly
	    float posX = (man.getWorldCenter().x * 10f)+400;
	    float posY = (man.getWorldCenter().y * 10f)+300;
	    manSprite.setPosition(posX, posY);
	    
	    System.out.println(posX+" "+posY);
	    //manSprite.setRotation(MathUtils.radiansToDegrees * man.getAngle());
	    
	    man.setUserData(manSprite);

	    // Then we simply draw it as a normal sprite.
	    manSprite.draw(batch);
	}
	
	public Body getBody(){
		return man;
	}
	
	public float getX() {
	    return manSprite.getOriginX();
	}
	public float getY() {
	    return manSprite.getOriginY();
	}
	public Vector2 getPosition() {
	    return position;
	}
	
	
	
}

 */
/* Testing out the WORLD
import com.badlogic.gdx.ApplicationAdapter;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;


public class SwingAlong extends ApplicationAdapter{
	SpriteBatch batch;
	ShapeRenderer sr;
	Camera camera;
	World world;
	Box2DDebugRenderer debugRenderer;
	Sprite vine;
	Body treetop;
	RopeJointDef jointDef;
	Player player;
	
	@Override
	public void create () {
		Gdx.graphics.setWindowedMode(1000,800);
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / 10, Gdx.graphics.getHeight() / 10);
		
		//Create a physics world, the heart of the simulation.  The Vector 
        //passed in is gravity
        world = new World(new Vector2(0, -98f), true);
        
        
        player = new Player(world);//create a player in the world
        
        
        //treetop
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(-10f,10);
        //bodyDef.position.set(500,800);
        
        treetop = world.createBody(bodyDef);
        
        //create a sprite to hang at end of vine
        vine = new Sprite(new Texture("vine.png"));
        treetop.setUserData(vine);
        
        jointDef = new RopeJointDef ();
		jointDef.bodyA = treetop;
		jointDef.bodyB = player.getBody();
		jointDef.collideConnected = true;
		jointDef.maxLength = 10;
		jointDef.localAnchorA.set(0,30f);
		jointDef.localAnchorB.set(0,0);
		
		
		world.createJoint(jointDef);
		
		
		
		//System.out.println(treetop.getJointList().get(0));

	}

	@Override
	public void render () {
		world.step(1/60f, 6, 2);//update the world objects through time
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		player.render(batch);

	    
	    //System.out.println(treetop.getJointList());
	    //manSprite.setRotation(MathUtils.radiansToDegrees * man.getAngle());
	    
		batch.end();
		
		//System.out.println(jointDef.bodyB.getWorldCenter());
		
		debugRenderer.render(world, camera.combined);
	
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}
*/