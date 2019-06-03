package com.mygdx.game.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import static com.badlogic.gdx.Gdx.graphics;
public class Main extends ApplicationAdapter {
    BitmapFont diedFont;
    int p1animationType;
    int p2animationType;
    private static final int UP=1;
    private static final int RIGHT=2;
    private static final int LEFT=3;
    private static final int SHOOT=4;
    private static final int SWORD=5;
    boolean p1animation=false;
    boolean p2animation=false;
    private static SpriteBatch batch;
    LinkedList<Stick_PowerUp> powerups = new LinkedList<Stick_PowerUp>(); // this stores the powerups
    public static final int WIDTH = 1024; // this is the width of the screen
    public static final int HEIGHT = 1024; // this sets the height of the screen
    private Texture bg;
    Texture bullet;
    public static Stick_Player player; // this is the player object and it is static so that it can be accessed from different classes
    public static Stick_Player player2;
    public static Stick_HUD hud; // this is the heads up display
    public static Stick_HUD hud2;
    public static Texture[] explosion = new Texture[73]; // this array stores the sprites for the explosion for when an enemy dies
    private boolean playerAlive = true; // this will store a true or false value depending on whether the player is alive
    private boolean gameStarted = true; // this will store whether the game is started or not
    float stateTime;
    @Override
    public void create() { // create method is used for loading various assets needed
        diedFont = new BitmapFont(Gdx.files.internal("ASSETS/died.fnt"));
        graphics.setWindowedMode(WIDTH, HEIGHT);
        batch = new SpriteBatch(); // initialized the new batch
        player = new Stick_Player(0, 50); // initializes the player and sets the x and y
        player2= new Stick_Player(100,50);
        player2.direction=LEFT;
        hud = new Stick_HUD(); // initializes the heads up display
        hud2=new Stick_HUD();
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
                if (p1animation != true) {
                    stateTime = 0f;
                }
                p1animation = true;
                p1animationType = RIGHT;
//                System.out.println(animationType);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                player.goRight(); // player will go right when right arrow key pressed
                if (p1animation != true) {
                    stateTime = 0f;
                }
                p1animation = true;
                p1animationType = RIGHT;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !player.isShooting() && player.bullets.size() == 0) {
                player.bullets.add(player.shootBullet());
            }
            // if the left or right shift is pressed, then the player will use a powerup
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                if (!player.isJumping()) {
                    player.goUp(); // player will go up when left arrow key pressed
                    if (p1animation != true) {
                        stateTime = 0f;
                    }
                    p1animationType = UP;
                    p1animation = true;
                }
            }
            if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT));
                player.usePowerup();
            // if the player presses the space button and the player is not shooting, the player shoots a bullet
            if(Gdx.input.isKeyPressed(Input.Keys.X)){
                player.swordAttack();
                if (p1animation != true) {
                    stateTime = 0f;
                }
                p1animationType = SWORD;
                p1animation = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                player2.goLeft(); // player will go left when left arrow key pressed
                if (p2animation != true) {
                    stateTime = 0f;
                }
                p2animation = true;
                p2animationType = RIGHT;
//                System.out.println(animationType);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                player2.goRight(); // player will go right when right arrow key pressed
                if (p2animation != true) {
                    stateTime = 0f;
                }
                p2animation = true;
                p2animationType = RIGHT;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.Q) && !player2.isShooting() && player2.bullets.size() == 0) {
                player2.bullets.add(player2.shootBullet());
            }
            // if the left or right shift is pressed, then the player will use a powerup
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)){
                if(!player2.isJumping()) {
                    player2.goUp(); // player will go up when left arrow key pressed
                    if (p1animation != true) {
                        stateTime = 0f;
                    }
                    p2animationType = UP;
                    p2animation = true;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));
                player2.usePowerup();
            // if the player presses the space button and the player is not shooting, the player shoots a bullet
            if(Gdx.input.isKeyPressed(Input.Keys.R)){
                player.swordAttack();
                if (p2animation != true) {
                    stateTime = 0f;
                }
                p2animationType = SWORD;
                p2animation = true;
            }
        }
        stateTime += Gdx.graphics.getDeltaTime();
        batch.begin(); // begins the batch which will allow for items to be drawn upon it so that it can be seen on the screen
//        batch.draw(bg,0,0);
        if (p1animation) {
            player.renderAnimation(p1animationType,stateTime, batch);
            if (player.isFinishedAnimation(stateTime)) {
                p1animation = false;
            }
        }
        else{
            player.render(batch);
        }
        if(p2animation){
            player2.renderAnimation(p2animationType,stateTime, batch);
            if (player2.isFinishedAnimation(stateTime)) {
                p2animation = false;
            }
        }
        else{
            player2.render(batch);
        }
//        System.out.println(player.test);

        dropPowerup(); // will randomly drop a powerup
        isPlayerShot(player,player2); // this will check if the player is shot and will take away a life (unless a powerup prevents that)
        isPlayerShot(player2,player);
        isPlayerDead(player); // this will check if the player is dead
        isPlayerDead(player2); // this will check if the player is dead
        hud.update(batch); // this will update the heads up display
        hud2.update(batch);
        batch.end(); // ends the batch
    }

    @Override
    public void dispose() { // disposes of the batch
        batch.dispose();
    }
//
    private void youWin(){ // displays text and will
        if(player2.getLives()==0){
            diedFont.draw(batch,"PLAYER ONE WON!!!",300,612); // displays "YOU WON" on the screen
        }
        else if(player.getLives()==0) {
            diedFont.draw(batch, "PLAYER ONE WON!!!", 300, 612); // displays "YOU WON" on the screen
        }
        diedFont.draw(batch,"Continue?",335,512); // displays "YOU WON" on the screen
        diedFont.draw(batch,"Yes - Y",385,412); // displays "YOU WON" on the screen
        diedFont.draw(batch,"No - N",385,312); // displays "YOU WON" on the screen
        if (Gdx.input.isKeyPressed(Input.Keys.Y)){
            restart(); // restarts the game if yes
        }else if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            System.exit(0); // exits the game if no
        }
    }
    public void restart(){ // this method will restart the game
        playerAlive = true; // this will store a true or false value depending on whether the player is alive
        powerups = new LinkedList<Stick_PowerUp>(); // this stores the powerups
        player = new Stick_Player(500, 500);
        player2= new Stick_Player(700,500);
        hud = new Stick_HUD(); // initializes the heads up display
        hud2= new Stick_HUD();//
    }
    private void dropPowerup() { // this will decide at random when to drop a powerup
        Random powerupDrop = new Random(); // creates a random object
        int isDrop = powerupDrop.nextInt(10000); // this will get a random number within the given range
        if (isDrop < 2 && powerups.size() == 0) powerups.add(new Stick_PowerUp()); // creates a new powerup if the random number is less than 2 and if there are no other powerups on the screen
        for (int i = 0; i < powerups.size(); i++) { // this will go through the powerups
            powerups.get(i).update(batch); // this will update the powerup
            if (powerups.get(i).getRect().y + powerups.get(i).getRect().height < 0) { // removes the powerup if it is offscreen
                powerups.remove(i);
            } else if (player.isCollidingWith(powerups.get(i))) { // will remove and run a player method when the player collides with the powerup
                player.getPowerup(powerups.get(i)); // this will get the powerup for the player
                powerups.remove(i); // removes the powerup
            } else if (player2.isCollidingWith(powerups.get(i))) { // will remove and run a player method when the player collides with the powerup
                player2.getPowerup(powerups.get(i)); // this will get the powerup for the player
                powerups.remove(i); // removes the powerup
            }
        }
    }
    private void isPlayerShot(Stick_Player p1,Stick_Player enemey) { // will check if the player is shot
        for (int i = 0; i < enemey.bullets.size(); i++) {
            if (player.isCollidingWith(enemey.bullets.get(i))) { // if the player is colliding with a bullet
                enemey.bullets.remove(i); // removes the enemy bullet
                player.takeAwayLife(); // takes a life
            }
        }
    }
    private void isPlayerDead(Stick_Player player) { // will check if the player is dead
        if (player.getLives() <= 0) { // if the lives left is less than 0
            playerAlive = false; // will set the playerAlive to false
        }
    }
}