package com.mygdx.game.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class HUD {
    private Texture powerups_hud; // the background for the powerups HUD
    private Texture heart; // the texture for the heart to display the players life
    private ArrayList<Texture> powerup_sprites = new ArrayList<Texture>(); //will store the powerup texture

    public HUD() {// will load in various textures
        powerups_hud = new Texture("Assets/powerupsHUD.png");
        heart = new Texture("Assets/heart.png");
    }

    public void render(SpriteBatch batch) { // this will render all the information that is to be displayed
        batch.draw(powerups_hud, Main.WIDTH - powerups_hud.getWidth(), Main.HEIGHT - powerups_hud.getHeight()); // draws the powerup HUD background
    }

    public void update(SpriteBatch batch) { // calls the render method only in this case
        this.render(batch);

    }

    public void addPowerup(Texture img) { // this will add a powerup so that it can be displayed
        powerup_sprites.add(img);
    }

    public void removePowerup() { // removes a powerup once it has been used
        powerup_sprites.remove(0);
    }
}