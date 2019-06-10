package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button jumpButton = findViewById(R.id.button);
        jumpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("My First App","CLICKED");
            }
        });



    }


}
