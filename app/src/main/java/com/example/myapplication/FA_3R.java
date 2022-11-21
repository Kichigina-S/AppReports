package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class FA_3R extends AppCompatActivity {

    private String flightNumber;
    private String flightData;
    private String num;
    private String data;
    SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "flightNumberData";
    public static final String APP_PREFERENCES_NUMBER = "flightNumber";
    public static final String APP_PREFERENCES_DATA = "flightData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa_3r);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        loadText();
        Bundle getActivity = getIntent().getExtras();
        if (getActivity != null) {
            flightNumber = getActivity.getString("number");
            flightData = getActivity.getString("data");
        } else {
            flightNumber = num;
            flightData = data;
        }

        final Button cargoButton = findViewById(R.id.cargo_button);
        final Button actButton = findViewById(R.id.act_button);
        final Button backButton = findViewById(R.id.back_button);
        final Button dutiesButton = findViewById(R.id.duties_button);

        cargoButton.setOnClickListener(view ->{
            Intent intent = new Intent(FA_3R.this, fix_cargo.class);
            intent.putExtra("number", flightNumber);
            intent.putExtra("data", flightData);
            startActivity(intent);
        });

        actButton.setOnClickListener(view ->{
            String nameActivity = "FA_3R";
            Intent intent = new Intent(FA_3R.this, actsText.class);
            intent.putExtra("key", nameActivity);
            startActivity(intent);
        });

        dutiesButton.setOnClickListener(view ->{
            String nameActivity = "FA_3R";
            Intent intent = new Intent(FA_3R.this, dutiesText.class);
            intent.putExtra("key", nameActivity);
            startActivity(intent);
        });

        backButton.setOnClickListener(view ->{
            Intent intent = new Intent(FA_3R.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveText();
    }

    void saveText() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_NUMBER, flightNumber);
        editor.putString(APP_PREFERENCES_DATA, flightData);
        editor.apply();
    }

    void loadText() {
        if(mSettings.contains(APP_PREFERENCES_NUMBER) && mSettings.contains(APP_PREFERENCES_DATA)) {
            num = mSettings.getString(APP_PREFERENCES_NUMBER, "");
            data = mSettings.getString(APP_PREFERENCES_DATA, "");
        }
    }
}