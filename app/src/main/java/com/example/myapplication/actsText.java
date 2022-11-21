package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class actsText extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acts);

        Bundle getActivity = getIntent().getExtras();
        String whichActivity = getActivity.getString("key");

        final Button backButton = findViewById(R.id.back_button);

        backButton.setOnClickListener(view ->{
            switch (whichActivity) {
                case "Purser": {
                    Intent intent = new Intent(actsText.this, Purser.class);
                    startActivity(intent);
                    break;
                }
                case "FA_1R": {
                    Intent intent = new Intent(actsText.this, FA_1R.class);
                    startActivity(intent);
                    break;
                }
                case "FA_3L": {
                    Intent intent = new Intent(actsText.this, FA_3L.class);
                    startActivity(intent);
                    break;
                }
                case "FA_3R": {
                    Intent intent = new Intent(actsText.this, FA_3R.class);
                    startActivity(intent);
                    break;
                }
            }
        });
    }
}