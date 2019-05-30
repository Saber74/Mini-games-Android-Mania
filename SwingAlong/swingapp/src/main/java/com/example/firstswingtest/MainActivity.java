package com.example.firstswingtest;

//https://google-developer-training.gitbooks.io/android-developer-fundamentals-course-practicals/content/en/Unit%201/21_p_create_and_start_activities.html

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void launchSwingApp(View view) {
        //Log.d("MainActivity","CLICKED");

        Intent intent = new Intent(this, SwingControlActivity.class);
        startActivity(intent);

        //server
    }

    public void launchInvadersApp(View view) {
        Intent intent = new Intent(this, InvadersControlActivity.class);
        startActivity(intent);
    }

    public void launchCrossyApp(View view) {
        Intent intent = new Intent(this, CrossyControlActivity.class);
        startActivity(intent);
    }

    public void launchFightApp(View view) {
        Intent intent = new Intent(this, FightControlActivity.class);
        startActivity(intent);
    }

    public void launchBombApp(View view) {
        Intent intent = new Intent(this, BombControlActivity.class);
        startActivity(intent);
    }
}
