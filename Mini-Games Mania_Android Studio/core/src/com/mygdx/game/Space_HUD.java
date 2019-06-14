package com.mygdx.game;

/*
    Author: Anita Hu, Nizar Alrifai
    Class Name: HUD
    Purpose: this will be in charge of all the function necessary for a heads up display. This class will display things
             such a the health, points, and which powerups they are able to use.

        */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.xml.soap.Text;

public class Space_HUD {
    private Texture powerups_hud; // the background for the powerups HUD
    private Texture points_hud; // the COOL text for the points
    private Texture heart; // the texture for the heart to display the players life
    private Texture heart2;
    private BitmapFont font = new BitmapFont(Gdx.files.internal("android/assets/SpaceInvaders/one/impact.fnt"), false); // will load in a font for the points
    private LinkedList<Texture> powerup_sprites = new LinkedList<Texture>(); //will store the powerup texture
    private LinkedList<Texture> powerup_sprites2= new LinkedList<Texture>();

    public Space_HUD() {// will load in various textures
        powerups_hud = new Texture("android/assets/SpaceInvaders/powerupsHUD.png");
        points_hud = new Texture("android/assets/SpaceInvaders/points.png");
        heart = new Texture("android/assets/SpaceInvaders/heart.png");
        heart2= new Texture("android/assets/SpaceInvaders/heartp2.png");
    }

    public void render(SpriteBatch batch) { // this will render all the information that is to be displayed
        batch.draw(powerups_hud, Space_Main.WIDTH - powerups_hud.getWidth(), Space_Main.HEIGHT - powerups_hud.getHeight()); // draws the powerup HUD background
        batch.draw(points_hud, 0, Space_Main.HEIGHT - points_hud.getHeight()); // draws the COOL text for points
        font.draw(batch, "" + Space_Main.player.getPoints()+" vs "+Space_Main.player2.getPoints(), 235, Space_Main.HEIGHT - 25); // draws the point value on screen
        if (powerup_sprites.size() > 0) batch.draw(powerup_sprites.get(0), 725, Space_Main.HEIGHT - 105); // draws the powerup sprites on screen
        if(powerup_sprites2.size()>0) batch.draw(powerup_sprites2.get(0),925,Space_Main.HEIGHT-105);
        for (int i = 0; i < Space_Main.player.getLives(); i++) { // draws the players life represented by hearts at the top of the screen
            batch.draw(heart, 500 + i * (heart.getWidth() + 10), Space_Main.HEIGHT - heart.getHeight() - 25);
        }
        font.draw(batch,"VS",800,Space_Main.HEIGHT-50);
        for(int i= 0; i<Space_Main.player2.getLives();i++){
            batch.draw(heart2,500+i*(heart.getWidth()+10),Space_Main.HEIGHT-heart.getHeight()-75);
        }
    }

    public void update(SpriteBatch batch) { // calls the render method only in this case
        this.render(batch);

    }

    public void addPowerup(Texture img,int player) { // this will add a powerup so that it can be displayed
        if(player==1){
            powerup_sprites.add(img);
        }
        else{
            powerup_sprites2.add(img);
        }
    }

    public void removePowerup(int player) { // removes a powerup once it has been used
        if(player==1){
            powerup_sprites.remove(0);
        }
        else{
            powerup_sprites2.remove(0);
        }
    }
}

