package com.example.firstswingtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MemoryControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_control);
    }

    public void moveLeft(View view) {
        Log.d("MemoryControlActivity","left");
    }


    public void moveUp(View view) {
        Log.d("MemoryControlActivity","up");
    }


    public void moveRight(View view) {
        Log.d("MemoryControlActivity","right");
    }


    public void moveDown(View view) {
        Log.d("MemoryControlActivity","down");
    }

}
