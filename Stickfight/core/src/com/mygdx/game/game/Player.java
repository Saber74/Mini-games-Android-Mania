package com.mygdx.game.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.*;
import java.util.ArrayList;
public class Player {
    private float x, y; // stores the x and the y of the player
    private Texture player_sprite;
    private Sprite player;
    private boolean shooting = false;
    // powerup types
    private final static int SPIRITBOMB = 0;
    private final static int INVINCIBLE = 1;
    private final static int HEART = 2;
    private ArrayList<Integer> powerupID = new ArrayList<Integer>(); // will store the id of the powerup
    private int points = 0; // will store the points
    private int lives = 3; // stores the amount of lives left
    private boolean invincible = true; // used to determine invincibility
    private Sprite barrier = new Sprite(new Texture("Assets/barriers.png")); // sprite of the barrier
    boolean musicPlaying = false; // checks if music is playing
    Rectangle rect; // stores a rectangle of the player which is used for collision and more
    String jump[]={"Assets/Zero/Jump/0.png","Assets/Zero/Jump/1.png","Assets/Zero/Jump/8.png","Assets/Zero/Jump/9.png","Assets/Zero/Jump/10.png","Assets/Zero/Jump/11.png","Assets/Zero/Jump/12.png","Assets/Zero/Jump/13.png","Assets/Zero/Jump/14.png","Assets/Zero/Jump/15.png"};
    String right[]={"Assets/Zero/Walk/1.png","Assets/Zero/Walk/2.png","Assets/Zero/Walk/3.png","Assets/Zero/Walk/4.png","Assets/Zero/Walk/5.png","Assets/Zero/Walk/6.png","Assets/Zero/Walk/7.png","Assets/Zero/Walk/8.png","Assets/Zero/Walk/9.png","Assets/Zero/Walk/10.png","Assets/Zero/Walk/11.png","Assets/Zero/Walk/12.png","Assets/Zero/Walk/13.png","Assets/Zero/Walk/14.png","Assets/Zero/Walk/15.png"};
//    String shoot[]={};
//    String sword[]={};
    Texture currentFrame;
    Texture[] jumpTextures =new Texture[jump.length];
    Texture[] rightTextures =new Texture[right.length];
//    Animation<Texture> shootAnimation;
    //    Animation<Texture> leftAnimation;
    Animation<Texture> jumpAnimation;
    Animation<Texture> rightAnimation;
//    Animation<Texture> swordAnimation;
    boolean animation=false;
    int direction;
    private static final int UP=1;
    private static final int RIGHT=2;
    private static final int LEFT=3;
    public int test=0;

    public Player(float x, float y) { // constructor takes in x and y
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
    }

    //updates character's position
    public void render(SpriteBatch batch) { // renders in the player and the invincibility circle if the ability is active
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
        player.draw(batch); // draws the player on to the screen
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
        if(direction==LEFT){
            Sprite frame=new Sprite(currentFrame);
            frame.flip(true,false);
            batch.draw(frame,x,y);
        }
        else{
            batch.draw(currentFrame, x, y);
        }

        animation = false;
    }
    public boolean isFinishedAnimation(float stateTime){
        if(rightAnimation.isAnimationFinished(stateTime)){
            return true;
        }
        return false;
    }

    public void getPowerup(PowerUp powerup) { // called when the player collides with a powerup object
        int type = powerup.getType(); // gets the type of powerup
        if (powerupID.size() == 0) { // will only receive it if there are non presently used
            if (type == INVINCIBLE) { // if the type is invincible
                powerupID.add(INVINCIBLE); // adds the ID to the arraylist
            }
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
        int v=10;
        if(player.getY()+player.getHeight()<Main.HEIGHT) y+=8;
        animation=true;
    }

    public boolean isCollidingWith(PowerUp powerup) { // checks if the player is colliding with a powerup object and return a boolean
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
}