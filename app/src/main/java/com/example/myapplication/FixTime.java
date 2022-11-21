package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FixTime extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    private String flightNumber;
    private String flightData;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_time);

        Bundle getActivity = getIntent().getExtras();
        flightNumber = getActivity.getString("number");
        flightData = getActivity.getString("data");

        final Button saveButton = findViewById(R.id.save_time_button);

        final EditText appear = findViewById(R.id.appear_editText);
        final EditText docs = findViewById(R.id.docs_editText);
        final EditText beg_pax = findViewById(R.id.beg_pax_editText);
        final EditText end_pax = findViewById(R.id.end_pax_editText);
        final EditText close = findViewById(R.id.close_editText);
        final EditText delay = findViewById(R.id.delay_editText);

        delay.setText("0");

        appear.setOnClickListener(view -> setTime(appear));
        docs.setOnClickListener(view -> setTime(docs));
        beg_pax.setOnClickListener(view -> setTime(beg_pax));
        end_pax.setOnClickListener(view -> setTime(end_pax));
        close.setOnClickListener(view -> setTime(close));

        saveButton.setOnClickListener(view ->{
            dbHelper = new DatabaseHelper(this);
            db = dbHelper.open();
            ContentValues contentValues = new ContentValues();

            contentValues.put(DatabaseHelper.COLUMN_NUMBER, Integer.parseInt(flightNumber));
            contentValues.put(DatabaseHelper.COLUMN_DATA, flightData);
            contentValues.put(DatabaseHelper.COLUMN_APPEAR, appear.getText().toString());
            contentValues.put(DatabaseHelper.COLUMN_DOCS, docs.getText().toString());
            contentValues.put(DatabaseHelper.COLUMN_BEG_PAX, beg_pax.getText().toString());
            contentValues.put(DatabaseHelper.COLUMN_END_PAX, end_pax.getText().toString());
            contentValues.put(DatabaseHelper.COLUMN_CLOSE, close.getText().toString());
            contentValues.put(DatabaseHelper.COLUMN_DELAY, delay.getText().toString());

            db.insert(DatabaseHelper.TABLE_1L, null, contentValues);
            db.close();

            Intent intent = new Intent(FixTime.this, Purser.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
    }

    public void setTime (EditText editText) {
        Date currentDate = new Date();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String timeText = timeFormat.format(currentDate);
        editText.setText(timeText);
    }
}