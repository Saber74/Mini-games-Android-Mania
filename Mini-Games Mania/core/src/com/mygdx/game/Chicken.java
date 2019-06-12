package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

//class of Chicken players for CHICKEN CROSSY ROAD game
public class Chicken {
    int num; //which player number: 0 or 1

    int home; //number of player's chickens that have reached a home

    int score;//score of the player

    Texture win;//player's winning screen texture

    Texture[][] goodTextures;//chicken frames (4 walking frames for each of the 4 directions)
    Rectangle goodRect;//rectangle around the chicken (check for collisions)
    float gx,gy;//coordinates of player
    float moveX;//amount that must be added to player coords as it floats on moving object in water

    int lives;//number of lives the player has left

    Sprite feather;//the feather that appears when a chicken dies
    float fx,fy;//coordinates of feather (location where chicken died, then floats up)
    float a;//transparency of feather (fades over time)

    int goodIndex;//player's current direction (back, front, left, right)
    int goodFrame;//player's walking frame

    boolean dead;//check if player died
    boolean disableKeys;//prevents certain keys from being if player lost a life
    boolean overlap;
    //check if chicken is overlapping an object on the water.
    //if it is overlapping, then will not count chicken overlapping water as a death
    boolean first;//flag, so you only add coordinates of object to log the first time a chicken collides with an object

    Texture homeTexture;//when chicken reaches a nest, display this image at the end


    //Constructor method
    //pass in which player the chicken is (Player 0 or Player 1)
    public Chicken(int num){

        disableKeys = false;//enable all keys at start of game

        home = 0;//no chickens have reached home
        score = 0;//no score yet

        this.num = num;//which player the chicken is

        win = new Texture("android/assets/ChickenCrossyRoad/"+String.format("p%dwin.png", num+1));


        //create the good chicken walking textures
        goodTextures = new Texture[4][3];
        String[] goodImages = {"_back","_front","_left","_right"};

        for(int i=0; i<goodImages.length; i++){
            for(int j=0; j<3; j++){
                goodTextures[i][j] = new Texture("android/assets/ChickenCrossyRoad/"+String.format("good%d%s%d.png",num+1,goodImages[i],j+1));
            }
        }

        //start at 0th chicken texture
        goodIndex = 0;
        goodFrame = 0;


        homeTexture = new Texture("android/assets/ChickenCrossyRoad/"+String.format("home%d.png", num+1));

        lives = 5;//start with 5 lives

        //starting position
        gx = (800-50)/2-100+200*num;
        gy = 50;

        goodRect = new Rectangle(gx,gy,50,50);

        //initialize the feather (which pops up when the chicken is hit)
        feather = new Sprite(new Texture("android/assets/ChickenCrossyRoad/"+String.format("feather%d.png",num+1)));

        dead = false;
        overlap = false;//player has not yet reached a HopOn object
    }

    //check if the player has won (3 nests at the end)
    public boolean won(){
        return home==3 ? true : false;
        //if player has 3 chickens home, return true. else, return false
    }

    //check if the player is alive (more than 0 lives)
    public boolean isAlive(){
        return lives>0 ? true : false;
    }

    //check if player is off screen
    public boolean isOffScreen(){
        if(gx<25 || gx>750){
            return true;
        }
        return false;
    }

    //add points to player's score
    public void addToScore(int points){
        score += points;
    }

    //player reached a home nest
    public void addHome(){
        home++;
    }

    //update whether or not player is on a HopOn object in the water
    //this determines if the player will sink in water or not
    public void setOverlap(boolean b){
        overlap = b;
    }

    //find whether or not player just reached a HopOn object
    //If player just reached it, then add the object's coordinates to the player, so player moves with the object
    public boolean getFirst(){
        return first;
    }

    public void setFirst(boolean b){
        first = b;
    }

    //change frame to change direction that the player is moving
    public void setIndex(int i){
        goodIndex = i;
    }

    //make player look like he's moving
    public void increaseFrame(){
        goodFrame++;
    }

    //update player's coordinates
    public void setX(float x){
        gx = x;
    }
    public void setY(float y){
        gy = y;
    }
    public void translateX(float x){
        gx += x;
    }
    public void translateY(float y){
        gy += y;
    }

    //add x-coordinate of HopOn object to player
    public void setMoveX(float mx){
        moveX = mx;
    }

    public float getMoveX(){
        return moveX;
    }

    //set the player's rectangle
    public void setRect(float x, float y, float width, float height){
        goodRect = new Rectangle(x,y,width,height);
    }

    //update player's rectangle as player moves
    public void updateRect(float x, float y){
        goodRect.x = x;
        goodRect.y = y;
    }

    public Rectangle getRect(){
        return goodRect;
    }

    //set number of lives
    public void addLives(int l){
        lives = l;
    }


    public void render(SpriteBatch batch){
        batch.draw(goodTextures[goodIndex][goodFrame%3],goodRect.x,goodRect.y);
    }

    public void renderLives(SpriteBatch batch, int index){
        for(int i=0; i<lives-1; i++){
            batch.draw(goodTextures[1][1], 50*i + 600*index, 0, 50,50);
        }
    }

    public void die(){
        //set feather coordinates equal to last location where chicken died
        fx = gx;
        fy = gy;
        dead = true;
        //set chicken to position off screen until feather disappears
        gy = -100;
        a=1;
        setKeysDisable(true);
    }

    public void setKeysDisable(boolean b){
        disableKeys = b;
    }

    public boolean areKeysDisabled(){
        return disableKeys;
    }

    public void updateFeather(SpriteBatch batch, int count){
        //increase feather coordinates (gives the effect that feather is rising up)
        fx++;
        fy++;
        feather.setPosition(fx,fy);
        feather.draw(batch,a);
        if(count%10==0){
            a-=0.5;//make feather transparent over time
        }
        //once feather fully disappears, reset chicken position
        if(a==0){
            gx = (800-50)/2-100+200*num;
            gy = 50;
            lives--;
            goodIndex = 0;
            dead = false;
        }
    }

    public float getAlpha(){
        return a;
    }

    public float getX(){
        return gx;
    }

    public float getY(){
        return gy;
    }

    public boolean isDead(){
        return dead;
    }
}


