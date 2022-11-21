package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class fix_cargo extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    private String flightNumber;
    private String flightData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_cargo);

        Bundle getActivity = getIntent().getExtras();
        flightNumber = getActivity.getString("number");
        flightData = getActivity.getString("data");

        final Button saveButton = findViewById(R.id.save_cargo_button);

        final EditText baggage = findViewById(R.id.baggage_editText);
        final EditText cargo = findViewById(R.id.cargo_editText);
        final EditText mail = findViewById(R.id.mail_editText);
        final EditText weapon = findViewById(R.id.weapon_editText);
        final EditText dang = findViewById(R.id.dang_editText);
        final EditText animal = findViewById(R.id.animal_editText);

        baggage.setText("0");
        cargo.setText("0");
        mail.setText("0");
        weapon.setText("0");
        dang.setText("0");
        animal.setText("0");

        saveButton.setOnClickListener(view ->{

            dbHelper = new DatabaseHelper(this);
            db = dbHelper.open();
            ContentValues contentValues = new ContentValues();

            contentValues.put(DatabaseHelper.COLUMN_NUMBER, Integer.parseInt(flightNumber));
            contentValues.put(DatabaseHelper.COLUMN_DATA, flightData);
            contentValues.put(DatabaseHelper.COLUMN_BAGGAGE, Integer.parseInt(baggage.getText().toString()));
            contentValues.put(DatabaseHelper.COLUMN_CARGO, Integer.parseInt(cargo.getText().toString()));
            contentValues.put(DatabaseHelper.COLUMN_MAIL, Integer.parseInt(mail.getText().toString()));
            contentValues.put(DatabaseHelper.COLUMN_WEAPON, Integer.parseInt(weapon.getText().toString()));
            contentValues.put(DatabaseHelper.COLUMN_DANG, Integer.parseInt(dang.getText().toString()));
            contentValues.put(DatabaseHelper.COLUMN_ANIMAL, Integer.parseInt(animal.getText().toString()));

            db.insert(DatabaseHelper.TABLE_3R, null, contentValues);
            db.close();

            Intent intent = new Intent(fix_cargo.this, FA_3R.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
    }
}