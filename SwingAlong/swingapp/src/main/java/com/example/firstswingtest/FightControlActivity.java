package com.example.firstswingtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class FightControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_control);
    }

    public void moveLeft(View view) {
        Log.d("FightControlActivity","left");
    }


    public void moveUp(View view) {
        Log.d("FightControlActivity","up");
    }


    public void moveRight(View view) {
        Log.d("FightControlActivity","right");
    }

    public void usePowerUp(View view) {
    }

    public void shoot(View view) {
    }

    public void attack(View view) {
    }
}
