package com.gameso.vegascards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStart = findViewById(R.id.buttonStart);
        Button buttonExit = findViewById(R.id.buttonExit);
        buttonStart.setOnClickListener(onClickStart);
        buttonExit.setOnClickListener(onClickExit);
    }

    View.OnClickListener onClickStart = view -> {
        Intent intentStart = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intentStart);
        finish();
    };

    View.OnClickListener onClickExit = view -> finishAffinity();
}