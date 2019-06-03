package com.mygdx.game.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
public class Stick_Player {
    private float x, y; // stores the x and the y of the player
    private Texture player_sprite;
    private Sprite player;
    private boolean shooting = false;
    private boolean attacking=false;
    private boolean jumping=false;
    // powerup types
    private final static int SPIRITBOMB = 0;
    private final static int INVINCIBLE = 1;
    private final static int HEART = 2;
    private ArrayList<Integer> powerupID = new ArrayList<Integer>(); // will store the id of the powerup
    private int lives = 3; // stores the amount of lives left
    private boolean invincible = true; // used to determine invincibility
    private Sprite barrier = new Sprite(new Texture("Assets/barriers.png")); // sprite of the barrier
    Rectangle rect; // stores a rectangle of the player which is used for collision and more
    String jump[]={"Assets/Zero/Jump/1.png","Assets/Zero/Jump/2.png","Assets/Zero/Jump/3.png","Assets/Zero/Jump/4.png","Assets/Zero/Jump/5.png","Assets/Zero/Jump/6.png","Assets/Zero/Jump/7.png"};
    String right[]={"Assets/Zero/Walk/1.png","Assets/Zero/Walk/2.png","Assets/Zero/Walk/3.png","Assets/Zero/Walk/4.png","Assets/Zero/Walk/5.png","Assets/Zero/Walk/6.png","Assets/Zero/Walk/7.png","Assets/Zero/Walk/8.png","Assets/Zero/Walk/9.png","Assets/Zero/Walk/10.png","Assets/Zero/Walk/11.png","Assets/Zero/Walk/12.png","Assets/Zero/Walk/13.png","Assets/Zero/Walk/14.png","Assets/Zero/Walk/15.png"};
    String sword[]={"Assets/Zero/Blade/1.png","Assets/Zero/Blade/2.png","Assets/Zero/Blade/3.png","Assets/Zero/Blade/4.png","Assets/Zero/Blade/5.png","Assets/Zero/Blade/6.png","Assets/Zero/Blade/7.png","Assets/Zero/Blade/8.png","Assets/Zero/Blade/9.png","Assets/Zero/Blade/10.png","Assets/Zero/Blade/11.png"};
    String shoot[]={"Assets/Zero/Shoot/1.png","Assets/Zero/Shoot/2.png","Assets/Zero/Shoot/3.png","Assets/Zero/Shoot/4.png","Assets/Zero/Shoot/5.png","Assets/Zero/Shoot/6.png"};
    Texture currentFrame;
    Texture[] jumpTextures =new Texture[jump.length];
    Texture[] rightTextures =new Texture[right.length];
    Texture[] swordTextures=new Texture[sword.length];
    Texture[] shootTextures= new Texture[shoot.length];
    Animation<Texture> shootAnimation;
    Animation<Texture> jumpAnimation;
    Animation<Texture> rightAnimation;
    Animation<Texture> swordAnimation;
    public static LinkedList<Stick_Bullet> bullets = new LinkedList<Stick_Bullet>(); // arraylist that stores the enemy bullets
    boolean animation=false;
    int direction;
    private static final int UP=1;
    private static final int RIGHT=2;
    private static final int LEFT=3;
    private static final int SWORD=4;
    private static final int SHOOT=5;

    public Stick_Player(float x, float y) { // constructor takes in x and y
        player_sprite = new Texture("Assets/Zero/Walk/0.png"); // loads in player sprite image
        player = new Sprite(player_sprite); // creates a sprite out of the image
        this.x = x; // sets the x variable
        this.y = y; // sets the y variable
        player.setX(x); // sets the player's x
        player.setY(y); // sets the player's y
        rect = new Rectangle((int) player.getX(), (int) player.getY(), (int) player.getWidth(), (int) player.getHeight()); // creates a rect based on the sprite's dimensions
        for(int i=0; i<right.length; i++){
            rightTextures[i] = new Texture(right[i]);
        }
        rightAnimation = new Animation<Texture>(0.04f,rightTextures);
        for(int i=0; i<jump.length; i++){
            jumpTextures[i] = new Texture(jump[i]);
        }
        jumpAnimation= new Animation<Texture>(0.04f,jumpTextures);
        for(int i=0;i<sword.length;i++){
            swordTextures[i]= new Texture(sword[i]);
        }
        swordAnimation= new Animation<Texture>(0.04f,swordTextures);
        for(int i=0;i<shoot.length;i++){
            shootTextures[i]=new Texture(shoot[i]);
        }
        shootAnimation= new Animation<Texture>(0.04f,shootTextures);
    }

    //updates character's position
    public void render(SpriteBatch batch) { // renders in the player and the invincibility circle if the ability is active
        if(isJumping()){
            y-=10;
        }
        player.setX(x);
        player.setY(y);
        // creates a new rectangle
        rect = new Rectangle((int) player.getX(), (int) player.getY(), (int) player.getWidth(), (int) player.getHeight());
        if (invincible) { // if the player is invincible
            // ther barrier sprite's x and y is set corresponding to the player's x and y
            barrier.setX(player.getX() - 35);
            barrier.setY(player.getY() - 40);
            barrier.rotate(1); // will rotate the barrier
            barrier.draw(batch); // draws the barrier onto the screen
        }
        if(direction==LEFT){
            Sprite flipped=player;
            flipped.flip(true,false);
            batch.draw(flipped,x,y);
        }
        else{
            batch.draw(player,x,y);
        }
    }


    public void usePowerup() { // uses a powerup when the shift button is pressed
        if (powerupID.size() > 0) { // if there is a powerup that is present
            if (powerupID.get(0) == INVINCIBLE) { // if the powerup is invincible
                invincible = true; // invincible  is set to true
                powerupID.remove(0); // removes the powerup id as it is not used
            }
        }
    }
    public void renderAnimation(int animationtype,float time, SpriteBatch batch) {
        if (animationtype == RIGHT) {
            currentFrame = rightAnimation.getKeyFrame(time, true);
        }
        else if (animationtype == UP) {
            currentFrame = jumpAnimation.getKeyFrame(time, true);
        }
        else if(animationtype==SWORD){
            currentFrame=swordAnimation.getKeyFrame(time,true);
        }
        else if(animationtype==SHOOT){
            currentFrame=shootAnimation.getKeyFrame(time,true);
        }
        if(direction==LEFT){
            Sprite frame=new Sprite(currentFrame);
            frame.flip(true,false);
            batch.draw(frame,x+20,y);
        }
        else{
            batch.draw(currentFrame, x, y);
        }
        animation = false;
        attacking=false;
        shooting=false;
    }
    public boolean isFinishedAnimation(float stateTime){
        if(rightAnimation.isAnimationFinished(stateTime)){
            return true;
        }
        return false;
    }

    public void getPowerup(Stick_PowerUp powerup) { // called when the player collides with a powerup object
        int type = powerup.getType(); // gets the type of powerup
        if (powerupID.size() == 0) { // will only receive it if there are non presently used
            if (type == INVINCIBLE) { // if the type is invincible
                powerupID.add(INVINCIBLE); // adds the ID to the arraylist
            }
        }
        else if(type==SPIRITBOMB){
            powerupID.add(SPIRITBOMB);
        }
        else if (type == HEART) { // will add a life if not already maxed out
            lives += (lives == 3 ? 0 : 1);
        }
    }

    public void goLeft() { // goes left
        if (player.getX() > 0) {
            x -= 8;
        }
        direction=LEFT;
        animation=true;
    }

    public void goRight() { // goes right
        if (player.getX() + player.getWidth() < Main.WIDTH) x += 8;
        animation=true;
        direction=RIGHT;
    }
    public void goUp(){
        if(player.getY()+player.getHeight()<Main.HEIGHT&&!isJumping()){
            y+=10;
        }
        animation=true;
        jumping=true;
    }
    public boolean isJumping(){
        if(player.getY()>50){
            return true;
        }
        else{
            return false;
        }
    }
    public void swordAttack() {
        if (!attacking) {
            attacking = true;
            animation = true;
        }
    }

    public boolean isCollidingWith(Stick_PowerUp powerup) { // checks if the player is colliding with a powerup object and return a boolean
        return powerup.getRect().intersects(this.getRect());
    }

    public Rectangle getRect() { // returns the Rectangle
        return rect;
    }

    public int getLives() { // returns the amount of lives
        return lives;
    }

    public void takeAwayLife() { // takes away a life
        if (!invincible) lives -= 1;
        else invincible = false; // if the player was invincible then the ability will end
    }
    public float getxpos(){
        return player.getX();
    }
    public float getypos(){
        return player.getY();
    }
    public Stick_Bullet shootBullet() { // sets shooting to true and returns a new bullet object
        shooting = true;
        return new Stick_Bullet(player.getX(), player.getY(), player.getWidth(), 0);
    }
    public boolean isShooting() { // returns the shooting boolean
        return shooting;
    }
    public void setShooting(boolean shooting) { // sets the shooting boolean
        this.shooting = shooting;
    }
    public boolean isCollidingWith(Stick_Bullet bullet) { // checks if the player is colliding with a bullet and returns a boolean
        return bullet.getRect().intersects(this.getRect());
    }
}