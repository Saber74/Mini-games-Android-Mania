package com.mygdx.game.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Random;
import static com.badlogic.gdx.Gdx.graphics;

public class Main extends ApplicationAdapter {
    int animationType;
    private static final int UP=1;
    private static final int RIGHT=2;
    private static final int LEFT=3;
    private static final int SHOOT=4;
    private static final int SWORD=5;
    boolean animation=false;
    private static SpriteBatch batch;
    ArrayList<PowerUp> powerups = new ArrayList<PowerUp>(); // this stores the powerups
    public static final int WIDTH = 1024; // this is the width of the screen
    public static final int HEIGHT = 1024; // this sets the height of the screen
    private Texture bg;
    Texture bullet;
    public static Player player; // this is the player object and it is static so that it can be accessed from different classes
    public static Player player2;
    public static HUD hud; // this is the heads up display
    public static HUD hud2;
    public static Texture[] explosion = new Texture[73]; // this array stores the sprites for the explosion for when an enemy dies
    private boolean playerAlive = true; // this will store a true or false value depending on whether the player is alive
    private boolean gameStarted = true; // this will store whether the game is started or not
    float stateTime;
    @Override
    public void create() { // create method is used for loading various assets needed
        graphics.setWindowedMode(WIDTH, HEIGHT);
        batch = new SpriteBatch(); // initialized the new batch
        player = new Player(0, 50); // initializes the player and sets the x and y
        player2= new Player(100,50);
        hud = new HUD(); // initializes the heads up display
        hud2=new HUD();
        bg = new Texture("Assets/bg.jpg");
        bullet = new Texture("Assets/Zero/Shoot/7.png");
        for (int i = 0; i < 73; i++) { // this will load in the images for the explosion animation
            explosion[i] = new Texture("Assets/EXPLOSION/" + i + ".png"); // assigns a part of the array to a certain image
        }
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(100, 100, 100, 1); // sets the background colour to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (gameStarted) { // will only check for button presses for these methods when the game has started
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                player.goLeft(); // player will go left when left arrow key pressed
                System.out.println(player.direction);
                if (animation != true) {
                    stateTime = 0f;
                }
                animation = true;
                animationType = RIGHT;
//                System.out.println(animationType);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.goRight(); // player will go right when right arrow key pressed
                if (animation != true) {
                    stateTime = 0f;
                }
                animation = true;
                animationType = RIGHT;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !player.isShooting() && player.bullets.size() == 0) {
                player.bullets.add(player.shootBullet());
            }
            // if the left or right shift is pressed, then the player will use a powerup
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                if(!player.isJumping()) {
                    player.goUp(); // player will go up when left arrow key pressed
                    if (animation != true) {
                        stateTime = 0f;
                    }
                    animationType = UP;
                    animation = true;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT))
                player.usePowerup();
            // if the player presses the space button and the player is not shooting, the player shoots a bullet
            if(Gdx.input.isKeyPressed(Input.Keys.X)){
                player.swordAttack();
                if (animation != true) {
                    stateTime = 0f;
                }
                animationType = SWORD;
                animation = true;
            }
        }
        stateTime += Gdx.graphics.getDeltaTime();
        batch.begin(); // begins the batch which will allow for items to be drawn upon it so that it can be seen on the screen
        batch.draw(bg,0,0);
        if (animation) {
            player.renderAnimation(animationType,stateTime, batch);
            if (player.isFinishedAnimation(stateTime)) {
                animation = false;
            }
        }
        else{
            player2.render(batch);
            player.render(batch);
        }
//        System.out.println(player.test);

        dropPowerup(); // will randomly drop a powerup
        isPlayerShot(player,player2); // this will check if the player is shot and will take away a life (unless a powerup prevents that)
        isPlayerShot(player2,player);
        isPlayerDead(player); // this will check if the player is dead
        isPlayerDead(player2); // this will check if the player is dead
        hud.update(batch); // this will update the heads up display
        batch.end(); // ends the batch
    }

    @Override
    public void dispose() { // disposes of the batch
        batch.dispose();
    }
//
//    private void youWin(){ // displays text and will
////        music.stop(); // stops the music
////        Music win= Gdx.audio.newMusic(Gdx.files.internal("Assets/Sound/win.mp3")); // will load the win audio
////        if(soundPlayed==false) { // plays the sound once
////            win.play();
////            soundPlayed = true;
////        }
//        if (Gdx.input.isKeyPressed(Input.Keys.Y)){
//            restart(); // restarts the game if yes
//        }else if (Gdx.input.isKeyPressed(Input.Keys.N)) {
//            System.exit(0); // exits the game if no
//        }
//    }
//
//    private void youDied(){ // this will display choice words and a sound when the player loses
//        if (Gdx.input.isKeyPressed(Input.Keys.Y)){
//            restart();// restarts the game if yes
//        }else if (Gdx.input.isKeyPressed(Input.Keys.N)) {
//            System.exit(0); // exits the game if no
//        }
//    }
//
//    public void restart(){ // this method will restart the game
//        playerAlive = true; // this will store a true or false value depending on whether the player is alive
//        powerups = new ArrayList<PowerUp>(); // this stores the powerups
//        player = new Player(500, 500);
//        hud = new HUD(); // initializes the heads up display
//    }
//
//
    private void dropPowerup() { // this will decide at random when to drop a powerup
        Random powerupDrop = new Random(); // creates a random object
        int isDrop = powerupDrop.nextInt(10000); // this will get a random number within the given range
        if (isDrop < 2 && powerups.size() == 0) powerups.add(new PowerUp()); // creates a new powerup if the random number is less than 2 and if there are no other powerups on the screen
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
    private void isPlayerShot(Player p1,Player enemey) { // will check if the player is shot
        for (int i = 0; i < enemey.bullets.size(); i++) {
            if (player.isCollidingWith(enemey.bullets.get(i))) { // if the player is colliding with a bullet
                enemey.bullets.remove(i); // removes the enemy bullet
                player.takeAwayLife(); // takes a life
            }
        }
    }
    private void isPlayerDead(Player player) { // will check if the player is dead
        if (player.getLives() <= 0) { // if the lives left is less than 0
            playerAlive = false; // will set the playerAlive to false
        }
    }
}