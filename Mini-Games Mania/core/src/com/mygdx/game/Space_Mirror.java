package com.mygdx.game;

/*
    Author: Anita Hu, Nizar Alrifai
    Class Name: Space_Mirror
    Purpose: creates a mirror which would reflect the enemy bullets back towards the player

        */
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class Space_Mirror {
    private Sprite mirror = new Sprite(new Texture("android/assets/SpaceInvaders/Mirror/0.png")); // creates a new sprite
    private Rectangle rect;
    private int timesHit = 0; // will store the amount of times the mirror has been hit
    private boolean isBroken = false; // will check if the mirror is broken

    public Space_Mirror() { // creates a rectange object
        rect = new Rectangle((int) mirror.getX(), (int) mirror.getY(), (int) mirror.getWidth(), (int) mirror.getHeight());
    }

    public void render(SpriteBatch batch, int num) { // renders the mirrors depending on which number they are out of the three mirror
        mirror.setX(200 + (mirror.getWidth() + 100) * num); // sets the x of the mirrors
        mirror.setY(125); // sets the y of the mirrors
        // creates a new rectangle
        rect = new Rectangle((int) mirror.getX(), (int) mirror.getY(), (int) mirror.getWidth(), (int) mirror.getHeight());
        mirror.draw(batch); // draws the mirror on the screen

    }

    public void update(SpriteBatch batch, int num) { // will render the mirrors if they haven't been broken
        if (!isBroken) this.render(batch, num);
    }

    public Rectangle getRect() { // returns the rectangle
        return rect;
    }

    public boolean isCollidesWith(Space_Bullet bullet){ // will check if the bullets are colliding with the mirror and the mirror is not broken
        return this.getRect().intersects(bullet.getRect()) && !isBroken;
    }

    public void hit(){ // will add to the times the mirror has been hit
        timesHit += 1;
        if (timesHit >= 3){ // if the mirror has been hit at least 3 times
            isBroken = true; // the mirror is set to being broken
        }
    }

    public boolean isBroken() { // returns if the mirror is broken
        return isBroken;
    }
}

