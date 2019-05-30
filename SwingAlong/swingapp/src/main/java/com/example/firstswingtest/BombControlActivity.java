package com.example.firstswingtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class BombControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bomb_control);

        for(int i = 1; i<=6; i++){
            Switch sw = (Switch) findViewById(getResources().getIdentifier("wire"+i, "id", this.getPackageName()));

            sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    // The toggle is enabled
                    buttonView.setAlpha(0.25f);
                    buttonView.setChecked(false);
                    buttonView.setEnabled(false);
                    Log.d("BombControlActivity","WIRE DEACTIVATED");
                }
            }
        });
        }
    }


    //https://www.tutlane.com/tutorial/android/android-switch-on-off-button-with-examples

}
