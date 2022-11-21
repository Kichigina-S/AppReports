package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class dutiesText extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duties_text);

        Bundle getActivity = getIntent().getExtras();
        String whichActivity = getActivity.getString("key");

        final Button backButton = findViewById(R.id.back_button);
        TextView textView = findViewById(R.id.duties_textView);

        switch (whichActivity) {
            case "Purser": {
                textView.setText(R.string.PurserDuty);
                break;
            }
            case "FA_1R": {
                textView.setText(R.string.FA_1R_Duty);
                break;
            }
            case "FA_3L": {
                textView.setText(R.string.FA_3L_Duty);
                break;
            }
            case "FA_3R": {
                textView.setText(R.string.FA_3R_Duty);
                break;
            }
        }

        backButton.setOnClickListener(view ->{
            switch (whichActivity) {
                case "Purser": {
                    Intent intent = new Intent(dutiesText.this, Purser.class);
                    startActivity(intent);
                    finish();
                    break;
                }
                case "FA_1R": {
                    Intent intent = new Intent(dutiesText.this, FA_1R.class);
                    startActivity(intent);
                    break;
                }
                case "FA_3L": {
                    Intent intent = new Intent(dutiesText.this, FA_3L.class);
                    startActivity(intent);
                    break;
                }
                case "FA_3R": {
                    Intent intent = new Intent(dutiesText.this, FA_3R.class);
                    startActivity(intent);
                    break;
                }
            }
        });
    }
}