package com.example.firstswingtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class CrossyControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crossy_control);
    }

    public void moveLeft(View view) {
        Log.d("CrossyControlActivity","left");
    }


    public void moveUp(View view) {
        Log.d("CrossyControlActivity","up");
    }


    public void moveRight(View view) {
        Log.d("CrossyControlActivity","right");
    }


    public void moveDown(View view) {
        Log.d("CrossyControlActivity","down");
    }
}
