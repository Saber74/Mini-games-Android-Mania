/*
    Author: Anita Hu, Nizar Alrifai
    Class Name: Main
    Purpose: The main class is responsible for all the core functionality of the game.

        */
package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static com.badlogic.gdx.Gdx.graphics;
public class Space_Main extends ScreenAdapter {
    MyGdxGame game;
    private boolean soundPlayed=false;
    private boolean end = false; //indicating the end of introduction
    Music intro_music; //main theme
    private static SpriteBatch batch;
    boolean starting1 = false; //boolean to switch to 2nd phase of intro
    boolean starting2 = false; //boolean to switch to 3rd phase of intro
    Texture background;
    BitmapFont font2, font3, font4; //fonts used
    Texture intro_player;
    Texture bullet;
    Texture powerup1, powerup2, powerup3; //pictures of pu
    Texture enemy1, enemy2, enemy3;
    Texture ship;
    Texture intro_explosion;
    Texture bulletside;
    Music start;//sound effects in "Music" to use the setOncompletionlistner which is not available for sounds
    Music start2;
    Music start0;
    Music music;
    Texture bg;
    public static LinkedList<Space_Bullet> enemybullets = new LinkedList<Space_Bullet>(); // arraylist that stores the enemy bullets
    public static LinkedList<Space_Bullet> bullets = new LinkedList<Space_Bullet>(); // this is the arraylist that stores the player bullets
    LinkedList<Space_PowerUp> powerups = new LinkedList<Space_PowerUp>(); // this stores the powerups
    public static ArrayList<ArrayList<Space_Enemy>> enemies = new ArrayList<ArrayList<Space_Enemy>>(); // this stores all the enemies
    public static final int WIDTH = 1024; // this is the width of the screen
    public static final int HEIGHT = 1024; // this sets the height of the screen

    public static Space_Player player; // this is the player object and it is static so that it can be accessed from different classes
    public static Space_HUD hud; // this is the heads up display

    public static Texture[] explosion = new Texture[73]; // this array stores the sprites for the explosion for when an enemy dies

    private boolean playerAlive = true; // this will store a true or false value depending on whether the player is alive
    private boolean gameStarted = false; // this will store whether the game is started or not
    private int aliveEnemies; // this will store the number of alive enemies
    private BitmapFont diedFont; // just a font
    //----------------------------------------*-------------------------------------------------------
    //create method
    public Space_Main(MyGdxGame game) { // create method is used for loading various assets needed
        this.game=game;
        // loading music
        start0 = Gdx.audio.newMusic(Gdx.files.internal("SpaceInvaders/Sound/start0.mp3")); //first sound in intro
        start = Gdx.audio.newMusic(Gdx.files.internal("SpaceInvaders/Sound/start.mp3")); //2nd
        start2 = Gdx.audio.newMusic(Gdx.files.internal("SpaceInvaders/Sound/start2.mp3")); //3rd
        // loading a bunch of images
        intro_player = new Texture("SpaceInvaders/0.png");
        bullet = new Texture("SpaceInvaders/1.png");
        bulletside = new Texture("SpaceInvaders/1rotated.png");
        ship = new Texture("SpaceInvaders/2.png");
        enemy1 = new Texture("SpaceInvaders/Enemies/0.png");
        enemy2 = new Texture("SpaceInvaders/Enemies/1.png");
        enemy3 = new Texture("SpaceInvaders/Enemies/2.png");
        powerup1 = new Texture("SpaceInvaders/mirror.png");
        powerup2 = new Texture("SpaceInvaders/spiritBomb.png");
        powerup3 = new Texture("SpaceInvaders/invincible.png");
        intro_explosion = new Texture("SpaceInvaders/Explosion/29.png");
        // loading a bunch of fonts
        font2 = new BitmapFont(Gdx.files.internal("SpaceInvaders/one/intro.fnt")); //description font
        font3 = new BitmapFont(Gdx.files.internal("SpaceInvaders/one/sub.fnt")); //description but smaller
        font4 = new BitmapFont(Gdx.files.internal("SpaceInvaders/one/sub.fnt")); //for instructions
        diedFont = new BitmapFont(Gdx.files.internal("SpaceInvaders/one/died.fnt"));
        font3.getData().setScale(2f);
        font2.getData().setScale(0.8f);
        background = new Texture("SpaceInvaders/start.jpg"); // background for intro screen
        intro_music = Gdx.audio.newMusic(Gdx.files.internal("SpaceInvaders/1.mp3"));
        intro_music.play();
        //next up are assets used in main game
        music = Gdx.audio.newMusic(Gdx.files.internal("SpaceInvaders/Sound/main.mp3"));
        bg = new Texture("SpaceInvaders/jpgs/space-1.jpg");
        batch = game.batch; // initialized the new batch
        player = new Space_Player(0, 50); // initializes the player and sets the x and y
        createEnemies(); // creates enemies

        hud = new Space_HUD(); // initializes the heads up display

        for (int i = 0; i < 73; i++) { // this will load in the images for the explosion animation
            explosion[i] = new Texture("SpaceInvaders/EXPLOSION/" + i + ".png"); // assigns a part of the array to a certain image
        }
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // sets the background colour to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin(); // begins the batch which will allow for items to be drawn upon it so that it can be seen on the screen
        intro(); // this will run the intro
        aliveEnemies = numOfAliveEnemies(); // this will get the number of alive enemies
        if (playerAlive && gameStarted && aliveEnemies > 0) { // will keep on running as long as the intro has ended, the player is alive, and there are still enemies alive
            System.out.println(aliveEnemies);
            music.play(); // starts playing music
            music.setOnCompletionListener(new Music.OnCompletionListener() {//once the song is over repeat it!
                @Override
                public void onCompletion(Music music) {
                    music.play();
                }
            });
            batch.draw(bg, 0, 0); // draws the background
            player.update(batch); // this will update the player's position and powerups the play has activated and received
            dropPowerup(); // will randomly drop a powerup
            bulletsUpdate(); // will update the bullets and remove them if they move off-screen
            enemiesUpdate(); // this will check if the enemy is shot, as well as makes the enemies move properly
            enemiesShoot(); // will choose if enemies get to shoot and will remove the enemy bullets if they move off-screen
            isPlayerShot(); // this will check if the player is shot and will take away a life (unless a powerup prevents that)
            areEnemiesCloseToGround(); // this will check if the enemies are low enough so that they can touch the player
            isPlayerDead(); // this will check if the player is dead
            hud.update(batch); // this will update the heads up display
        }
        else { // if one of the conditions for running the game are not true
            if (aliveEnemies > 0 && !playerAlive) { // if the enemies are alive but the player is not then the player loses
                youDied(); // shows some text on the screen after you die and will play a sound
            } else if (playerAlive && aliveEnemies == 0){ // if the player is alive but the enemies aren't
                youWin(); // this will display some text and some sound will be played
            }
        }
        batch.end(); // ends the batch
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                    player.goLeft(); // player will go left when left arrow key pressed
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                    player.goRight(); // player will go right when right arrow key pressed
                // if the left or right shift is pressed, then the player will use a powerup
                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT))
                    player.usePowerup();
                // if the player presses the space button and the player is not shooting, the player shoots a bullet
                if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !player.isShooting() && bullets.size() == 0)
                    bullets.add(player.shootBullet());
                return true;

            }
        });
    }

//                if (keycode == Input.Keys.LEFT) {
//                    player.goLeft(); // player will go left when left arrow key pressed
//                }
//                if (keycode == Input.Keys.RIGHT) {
//                    player.goRight(); // player will go right when right arrow key pressed
//                }
//                if (keycode == Input.Keys.SHIFT_LEFT) {
//                    player.usePowerup(); // if the leftshift is pressed, then the player will use a powerup
//
//                }
//                if (keycode == Input.Keys.SPACE && !player.isShooting() && bullets.size() == 0) {
//                    // if the player presses the space button and the player is not shooting, the player shoots a bullet
//                    bullets.add(player.shootBullet());
//                }
//
//
//            return true;
//        }
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
    private int numOfAliveEnemies() { // this will count up the amount of enemies alive
        int counter = 0;
        for (int i = 0; i < enemies.size(); i++) {
            for (int n = 0; n < enemies.get(i).size(); n++) {
                if (!enemies.get(i).get(n).isDead()) counter++; // if the enemy is not dead then the counter will go up by one
            }
        }
        return counter; // returns the amount of dead enemies
    }

    private void inverseShipDirection() { // this will make the ship go left, right, and down when called. Also detects if collision with the player is made
        for (int i = 0; i < enemies.size(); i++) {
            for (int n = 0; n < enemies.get(i).size(); n++) {
                enemies.get(i).get(n).setY(enemies.get(i).get(n).getRect().y - 25); // this will make the ships go down by 25
                enemies.get(i).get(n).inverseSpeed(); // this will inverse the ships speeds so that they will travel in the other direction
            }
        }
    }

    private void areEnemiesCloseToGround(){ // checks whether the enemies are close to the ground
        for (int i = 0; i < enemies.size(); i++) {
            for (int n = 0; n < enemies.get(i).size(); n++) {
                if (enemies.get(i).get(n).getRect().y <= 125 && !enemies.get(i).get(n).isDead()){
                    player.kill(); // kills the player immediately
                }
            }
        }
    }

    private void youWin(){ // displays text and will
        music.stop(); // stops the music
        Music win= Gdx.audio.newMusic(Gdx.files.internal("SpaceInvaders/Sound/win.mp3")); // will load the win audio
        if(soundPlayed==false) { // plays the sound once
            win.play();
            soundPlayed = true;
        }
        diedFont.draw(batch,"YOU WON!!!",300,612); // displays "YOU WON" on the screen
        diedFont.draw(batch,"Continue?",335,512); // displays "YOU WON" on the screen
        diedFont.draw(batch,"Yes - Y",385,412); // displays "YOU WON" on the screen
        diedFont.draw(batch,"No - N",385,312); // displays "YOU WON" on the screen
        if (Gdx.input.isKeyPressed(Input.Keys.Y)){
            restart(); // restarts the game if yes
        }else if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            System.exit(0); // exits the game if no
        }
    }

    private void youDied(){ // this will display choice words and a sound when the player loses
        diedFont.draw(batch, "YOU DIED.....", 350, 950);
        diedFont.draw(batch, "YOU FAILED THE GALAXY",150, 800);
        diedFont.draw(batch, "YOU FAILED HUMANITY", 180,660);
        diedFont.draw(batch, "Frankly speaking, YOU SUCK!",100,500);
        diedFont.draw(batch,"Yes - Y",385,400); // displays "YOU WON" on the screen
        diedFont.draw(batch,"No - N",385,300); // displays "YOU WON" on the screen
        if (Gdx.input.isKeyPressed(Input.Keys.Y)){
            restart();// restarts the game if yes
        }else if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            System.exit(0); // exits the game if no
        }
    }

    public void restart(){ // this method will restart the game
        playerAlive = true; // this will store a true or false value depending on whether the player is alive
        enemybullets = new LinkedList<Space_Bullet>(); // arraylist that stores the enemy bullets
        bullets = new LinkedList<Space_Bullet>(); // this is the arraylist that stores the player bullets
        powerups = new LinkedList<Space_PowerUp>(); // this stores the powerups
        enemies = new ArrayList<ArrayList<Space_Enemy>>(); // this stores all the enemies
        player = new Space_Player(0, 50);

        createEnemies(); // creates enemies

        hud = new Space_HUD(); // initializes the heads up display
        aliveEnemies = numOfAliveEnemies(); // this will store the number of alive enemies
    }

    private void createEnemies(){ // this will create the enemies
        for (int i = 0; i < 5; i++) { // creates all the enemies
            ArrayList<Space_Enemy> temp = new ArrayList<Space_Enemy>(); // will store the enemies for one row
            for (int j = 0; j < 8; j++) { // creates the separate enemies for the row
                if (i == 0) temp.add(new Space_Enemy("red", j, i)); // will assign type of ship depending on which row it is in
                if (i == 1 || i == 2) temp.add(new Space_Enemy("yellow", j, i));
                if (i == 3 || i == 4) temp.add(new Space_Enemy("blue", j, i));
            }
            enemies.add(temp); // adds the arraylist for the enemies in a row to the master arraylist
        }
    }

    private void dropPowerup() { // this will decide at random when to drop a powerup
        Random powerupDrop = new Random(); // creates a random object
        int isDrop = powerupDrop.nextInt(1000); // this will get a random number within the given range
        if (isDrop < 2 && powerups.size() == 0) powerups.add(new Space_PowerUp()); // creates a new powerup if the random number is less than 2 and if there are no other powerups on the screen
        for (int i = 0; i < powerups.size(); i++) { // this will go through the powerups
            powerups.get(i).update(batch); // this will update the powerup
            if (powerups.get(i).getRect().y + powerups.get(i).getRect().height < 0) { // removes the powerup if it is offscreen
                powerups.remove(i);
            } else if (player.isCollidingWith(powerups.get(i))) { // will remove and run a player method when the player collides with the powerup
                player.getPowerup(powerups.get(i)); // this will get the powerup for the player
                powerups.remove(i); // removes the powerup
            }
        }
    }
    private void enemiesUpdate() { // this will check if the enemy is shot, as well as makes the enemies move properly
        for (int i = 0; i < enemies.size(); i++) { // this will go through the all the enemies
            for (int n = 0; n < enemies.get(i).size(); n++) {
                enemies.get(i).get(n).update(batch); // updates an enemy
                if ((enemies.get(i).get(n).getRect().x + enemies.get(i).get(n).getRect().width > Space_Main.WIDTH || enemies.get(i).get(n).getRect().x < 0) && !enemies.get(i).get(n).isDead())
                    // if the enemy hits the left side or right
                    inverseShipDirection(); // moves the enemy in the right direction
                for (int f = 0; f < bullets.size(); f++) { // gets the bullets
                    if (enemies.get(i).get(n).isCollidingWith(bullets.get(f))) { // will check if a bullet hits the enemy
                        bullets.remove(f); // removes the bullet
                        player.setShooting(false); // player is not shooting anymore
                        player.addPoints(enemies.get(i).get(n).getPointValue()); // adds points to the player's score according to the enemy point value
                        enemies.get(i).get(n).setDead(true, player); // this will set the enemy to dead
                    }
                }
            }
        }
    }

    private void isPlayerShot() { // will check if the player is shot
        for (int i = 0; i < enemybullets.size(); i++) {
            if (player.isCollidingWith(enemybullets.get(i))) { // if the player is colliding with a bullet
                enemybullets.remove(i); // removes the enemy bullet
                player.takeAwayLife(); // takes a life
            }
        }
    }

    private void bulletsUpdate() { // updates all the bullets
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update(batch); // updates the bullet
            if (bullets.get(i).getY() > HEIGHT) { // if the bullet is off screen it gets removed
                bullets.remove(i);
                player.setShooting(false);
            }
        }
    }

    private LinkedList<Space_Bullet> chooseShootingEnemies() { // this will choose which enemy will shoot
        LinkedList<Space_Bullet> enemyBullets = new LinkedList<Space_Bullet>(); // creates an arraylist that will store the bullets
        int shootChance; // will store the shooting chance
        Random enemyShootChance = new Random(); // random object
        for (int i = 0; i < enemies.size(); i++) {
            for (int n = 0; n < enemies.get(i).size(); n++) {
                shootChance = enemyShootChance.nextInt(100); // will get a random number
                if (shootChance < 10 && !enemies.get(i).get(n).isDead()) // if it is less than ten and the enemy is still alive it will get to shoot
                    enemyBullets.add(enemies.get(i).get(n).shootBullet()); // will add the enemy's bullet to the arraylist
            }
        }
        return enemyBullets; // returns the enemy's arraylist
    }

    private void enemiesShoot() { // will update the enemy bullets
        if (enemybullets.size() == 0) enemybullets = chooseShootingEnemies(); // if the bullets are used it will choose new enemies to shoot bulets
        for (int i = 0; i < enemybullets.size(); i++) {
            enemybullets.get(i).update(batch); // updates the bullet
            if (enemybullets.get(i).getRect().y + enemybullets.get(i).getRect().height < 0) enemybullets.remove(i); // if the bullet goes off-screen the bullet will be removed
        }
    }

    private void isPlayerDead() { // will check if the player is dead
        if (player.getLives() <= 0) { // if the lives left is less than 0
            playerAlive = false; // will set the playerAlive to false
        }
    }

    private void intro() { // this is the intro for the game before the game actually starts
        if (end == false) { //intro starts here
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && starting1 == false) {//starting to play

                starting1 = true; //starting 2nd phase
                start0.play();
                intro_music.stop();//stopping overall theme
                start0.setOnCompletionListener(new Music.OnCompletionListener() { //waiting for first sound to finish to start 2nd
                    @Override
                    public void onCompletion(Music music) {//waiting for 2nd sound to finish to play third
                        start.play();
                    }
                });
                start.setOnCompletionListener(new Music.OnCompletionListener() { //2nd to play third sound and start 3rd phase
                    @Override
                    public void onCompletion(Music music) {
                        start2.play();
                        starting2 = true;
                    }
                });
            }
            if ((Gdx.input.isKeyPressed(Input.Keys.X) && starting2 == true) || Gdx.input.isKeyPressed(Input.Keys.P)) {//to skip intro quickly/advance into game from third phase
                end = true; //boolean that intro has ended
            }
            batch.draw(background, Space_Main.WIDTH - background.getWidth(), Space_Main.HEIGHT - background.getHeight());
//            batch.draw(background, 0, 0);
            if (starting1 == false) { //drawing lots of texts and diagrams
                font3.draw(batch, "Press Space to initiate your mission", 100, 1000);
                batch.draw(enemy1, 950, 800);
                batch.draw(bullet, 970, 765);
            }
            if (starting2 == false) { //second phase including explosion
                batch.draw(intro_player, 950, 50);
                batch.draw(ship, 0, 50);
                batch.draw(intro_explosion, 800, 140);
                batch.draw(bulletside, 270, 140);
                if (starting1 == true) {
                    batch.draw(intro_explosion, 930, 760);
                    font3.draw(batch, "Your Mission Initiates in T-7", 170, 1000);
                }
            }
            else if (starting2 = true) { //3rd phase screen including ships powerups and controls
                batch.draw(bg,0,0);
                font2.draw(batch, "Commander Shepard was on the normandy when", 0, 1000);
                font2.draw(batch, "he encountered a fleet of hostile ships with", 0, 930);
                font2.draw(batch, "the intention of disturbing the peace.", 0, 860);
                font2.draw(batch, "The Galactic council will send power-ups as", 0, 790);
                font2.draw(batch,"reinforcements, use them wisely!",0,720);
                font3.draw(batch, "ENEMIES", 50, 600);
                font3.draw(batch, "POWER-UPS", 650, 600);
                font3.draw(batch, "40 pts", 0, 450);
                font3.draw(batch, "20 pts", 0, 300);
                font3.draw(batch, "10 pts", 0, 150);
                font4.draw(batch, "Controls:", 400, 500);
                font4.draw(batch, "Start: X", 400, 400);
                font4.draw(batch, "Shoot: Space", 360, 300);
                font4.draw(batch, "Power-up: Shift", 360, 200);
                batch.draw(enemy1, 200, 400);
                batch.draw(enemy2, 210, 250);
                batch.draw(enemy3, 200, 100);
                batch.draw(powerup1, 950, 400);
                batch.draw(powerup2, 950, 250);
                batch.draw(powerup3, 950, 100);
                font3.draw(batch, "Mirror", 650, 450);
                font3.draw(batch, "Bomb", 650, 300);
                font3.draw(batch, "Invincible", 650, 150);
            }
        } else { // after everything else is done the gameStarted will be made true so that the game can start
            gameStarted = true;
        }
    }
    @Override
    public void resize(int width, int height) {
        System.out.println("resized");
        super.resize(width, height);
    }
}
