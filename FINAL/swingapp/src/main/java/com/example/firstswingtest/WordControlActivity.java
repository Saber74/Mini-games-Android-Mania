package com.example.firstswingtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WordControlActivity extends AppCompatActivity {

    Button submitButton;
    EditText submitEdit;
    TextView submitText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_control);

        submitButton = (Button)findViewById(R.id.button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                submitEdit   = (EditText)findViewById(R.id.editText);
                Log.d("WordControlActivity",submitEdit.getText().toString());
                submitEdit.setEnabled(false);
            }
        });

    }




}
