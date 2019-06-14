package com.mygdx.game;

/*
    Author: Anita Hu, Nizar Alrifai
    Class Name: PowerUp
    Purpose: will create objects that the player will be able to collide with which will give the player the option
             to use the said powerups based on the type of the powerup object

        */
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;
import java.util.Random;

public class Space_PowerUp {
    private int x, y, type, fallSpeed;
    private Random rand = new Random();
    private Texture powerup_sprite;
    private Sprite powerup;

    Rectangle rect;

    public Space_PowerUp() {
        // texture arraylist which stores the textures of the different powerups
        Texture[] powerups = {new Texture("SpaceInvaders/spiritBomb.png"), new Texture("SpaceInvaders/invincible.png"), new Texture("SpaceInvaders/Mirror.png"), new Texture("SpaceInvaders/powerupHeart.png")};
        type = rand.nextInt(powerups.length); // will get a random type
        powerup_sprite = powerups[type]; // will create a texture out of the random type
        powerup = new Sprite(powerup_sprite); // creates a sprite
        fallSpeed = rand.nextInt(10) + 5; // will randomly determine the falling speed of the powerups
        x = rand.nextInt(Space_Main.WIDTH - powerup_sprite.getWidth()); // will determine a random x
        y = Space_Main.HEIGHT; // sets the y to the height of the screen
        powerup.setX(x); // sets the x to the random x value
        powerup.setY(y); // sets the y to the height of the screen
        // creates a rectangle based on the powerup sprite values
        rect = new Rectangle((int) powerup.getX(), (int) powerup.getY(), (int) powerup.getWidth(), (int) powerup.getHeight());
    }

    public void render(SpriteBatch batch) { // draws the powerup on the screen
        powerup.draw(batch);
    }

    public void update(SpriteBatch batch) {
        powerup.setY(powerup.getY() - fallSpeed); // will decrease the y according to the falling speed
        // updates the rectangle depending on the new y of the powerup sprite
        rect = new Rectangle((int) powerup.getX(), (int) powerup.getY(), (int) powerup.getWidth(), (int) powerup.getHeight());
        this.render(batch); // calls the render method
    }

    public Rectangle getRect() {
        return rect;
    } // returns the rect

    public int getType() {
        return type;
    } // returns the type

}

