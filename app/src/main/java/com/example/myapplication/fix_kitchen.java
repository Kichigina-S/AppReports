package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class fix_kitchen extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    private String flightNumber;
    private String flightData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_kitchen);

        Bundle getActivity = getIntent().getExtras();
        flightNumber = getActivity.getString("number");
        flightData = getActivity.getString("data");

        final Button saveButton = findViewById(R.id.save_kitchen_button);

        final EditText y = findViewById(R.id.y_editText);
        final EditText c = findViewById(R.id.c_editText);
        final EditText k = findViewById(R.id.k_editText);
        final EditText chml = findViewById(R.id.chml_editText);
        final EditText meml = findViewById(R.id.meml_editText);
        final EditText fiml = findViewById(R.id.fiml_editText);
        final EditText vgml = findViewById(R.id.vgml_editText);
        final EditText brml = findViewById(R.id.brml_editText);
        final EditText total = findViewById(R.id.total_editText);

        y.setText("0");
        c.setText("0");
        k.setText("0");
        chml.setText("0");
        meml.setText("0");
        fiml.setText("0");
        vgml.setText("0");
        brml.setText("0");

        total.setOnClickListener(view -> total.setText(kitchenTotal(y, c, k, chml, meml, fiml, vgml, brml)));

        saveButton.setOnClickListener(view ->{

            dbHelper = new DatabaseHelper(this);
            db = dbHelper.open();
            ContentValues contentValues = new ContentValues();

            contentValues.put(DatabaseHelper.COLUMN_NUMBER, Integer.parseInt(flightNumber));
            contentValues.put(DatabaseHelper.COLUMN_DATA, flightData);
            contentValues.put(DatabaseHelper.COLUMN_Y, Integer.parseInt(y.getText().toString()));
            contentValues.put(DatabaseHelper.COLUMN_C, Integer.parseInt(c.getText().toString()));
            contentValues.put(DatabaseHelper.COLUMN_K, Integer.parseInt(k.getText().toString()));
            contentValues.put(DatabaseHelper.COLUMN_CHML, Integer.parseInt(chml.getText().toString()));
            contentValues.put(DatabaseHelper.COLUMN_MEML, Integer.parseInt(meml.getText().toString()));
            contentValues.put(DatabaseHelper.COLUMN_FIML, Integer.parseInt(fiml.getText().toString()));
            contentValues.put(DatabaseHelper.COLUMN_VGML, Integer.parseInt(vgml.getText().toString()));
            contentValues.put(DatabaseHelper.COLUMN_BRML, Integer.parseInt(brml.getText().toString()));
            contentValues.put(DatabaseHelper.COLUMN_TOTAL, Integer.parseInt(total.getText().toString()));

            db.insert(DatabaseHelper.TABLE_3L, null, contentValues);
            db.close();

            Intent intent = new Intent(fix_kitchen.this, FA_3L.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
    }

    public String kitchenTotal(EditText y, EditText c, EditText k, EditText chml, EditText meml, EditText fiml, EditText vgml, EditText brml){
        int sum = Integer.parseInt(y.getText().toString()) + Integer.parseInt(c.getText().toString()) + Integer.parseInt(k.getText().toString()) +
                Integer.parseInt(chml.getText().toString()) + Integer.parseInt(meml.getText().toString()) + Integer.parseInt(fiml.getText().toString()) +
                Integer.parseInt(vgml.getText().toString()) + Integer.parseInt(brml.getText().toString());
        return Integer.toString(sum);
    }
}