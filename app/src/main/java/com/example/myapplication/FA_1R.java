package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class FA_1R extends AppCompatActivity {

    private final String nameActivity = "FA_1R";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_1r);

        final Button actButton = findViewById(R.id.act_button);
        final Button backButton = findViewById(R.id.back_button);
        final Button dutiesButton = findViewById(R.id.duties_button);

        actButton.setOnClickListener(view ->{
            Intent intent = new Intent(FA_1R.this, actsText.class);
            intent.putExtra("key", nameActivity);
            startActivity(intent);
        });

        dutiesButton.setOnClickListener(view ->{
            Intent intent = new Intent(FA_1R.this, dutiesText.class);
            intent.putExtra("key", nameActivity);
            startActivity(intent);
        });

        backButton.setOnClickListener(view ->{
            Intent intent = new Intent(FA_1R.this, MainActivity.class);
            startActivity(intent);
        });
    }
}