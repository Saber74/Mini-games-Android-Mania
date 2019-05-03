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