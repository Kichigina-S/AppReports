package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class aboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Bundle getActivity = getIntent().getExtras();
        String whichActivity = getActivity.getString("key");

        TextView textView = findViewById(R.id.textview_about_content);
        switch (whichActivity) {
            case "aboutButton": {
                setTitle(R.string.about_button);
                textView.setText(R.string.madeBy);
                break;
            }
            case "noNumberFlight": {
                setTitle(R.string.error);
                textView.setText(R.string.noNumberFlight);
                break;
            }
            case "existNumberFlight": {
                setTitle(R.string.error);
                textView.setText(R.string.existNumberFlight);
                break;
            }
        }
    }
}