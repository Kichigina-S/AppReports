package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Cursor cursor;
    String flightNumber, flightData;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    private final String about = "aboutButton";
    private final String noFD = "noNumberFlight";
    private final String existFD = "existNumberFlight";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            final Button flightButton = findViewById(R.id.flight_button);
            final Button infoButton = findViewById(R.id.info_button);
            final Button aboutButton = findViewById(R.id.about_button);
            final Button choose1LButton = findViewById(R.id.choose_1L_button);
            final Button choose1RButton = findViewById(R.id.choose_1R_button);
            final Button choose3LButton = findViewById(R.id.choose_3L_button);
            final Button choose3RButton = findViewById(R.id.choose_3R_button);

            EditText flight_num = findViewById(R.id.flight_num_editText);
            TextView flight_data = findViewById(R.id.flight_data_editText);

            flight_data.setOnClickListener(view -> callDatePicker(flight_data));

            flightButton.setOnClickListener(view->{
                getNumberData(flight_num, flight_data);
                if (flightNumber.isEmpty() || flightData.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, aboutActivity.class);
                    intent.putExtra("key", noFD);
                    startActivity(intent);
                } else {
                    dbHelper = new DatabaseHelper(getApplicationContext());
                    dbHelper.create_db();
                    choose1LButton.setVisibility(View.VISIBLE);
                    choose1RButton.setVisibility(View.VISIBLE);
                    choose3LButton.setVisibility(View.VISIBLE);
                    choose3RButton.setVisibility(View.VISIBLE);
                }
            });

            choose1RButton.setOnClickListener(view ->{
                Intent intent = new Intent(MainActivity.this, FA_1R.class);
                startActivity(intent);
            });

            choose1LButton.setOnClickListener(view -> {
                getNumberData(flight_num, flight_data);
                Intent intent;
                if (checkNumberData(DatabaseHelper.TABLE_1L) != 0){
                    intent = new Intent(MainActivity.this, aboutActivity.class);
                    intent.putExtra("key", existFD);
                } else {
                    intent = new Intent(MainActivity.this, Purser.class);
                    intent.putExtra("number", flightNumber);
                    intent.putExtra("data", flightData);
                }
                startActivity(intent);
            });

            choose3LButton.setOnClickListener(view ->{
                getNumberData(flight_num, flight_data);
                Intent intent;
                if (checkNumberData(DatabaseHelper.TABLE_3L) != 0){
                    intent = new Intent(MainActivity.this, aboutActivity.class);
                    intent.putExtra("key", existFD);
                } else {
                    intent = new Intent(MainActivity.this,FA_3L.class);
                    intent.putExtra("number", flightNumber);
                    intent.putExtra("data", flightData);
                }
                startActivity(intent);
            });

            choose3RButton.setOnClickListener(view ->{
                getNumberData(flight_num, flight_data);
                Intent intent;
                if (checkNumberData(DatabaseHelper.TABLE_3R) != 0){
                    intent = new Intent(MainActivity.this, aboutActivity.class);
                    intent.putExtra("key", existFD);
                } else {
                    intent = new Intent(MainActivity.this, FA_3R.class);
                    intent.putExtra("number", flightNumber);
                    intent.putExtra("data", flightData);
                }
                startActivity(intent);
            });

            infoButton.setOnClickListener(view -> {
                getNumberData(flight_num, flight_data);
                Intent intent;
                if (flightNumber.isEmpty() || flightData.isEmpty()) {
                    intent = new Intent(MainActivity.this, aboutActivity.class);
                    intent.putExtra("key", noFD);
                } else {
                    intent = new Intent(MainActivity.this, flightInfo.class);
                        intent.putExtra("number", flightNumber);
                        intent.putExtra("data", flightData);
                }
                startActivity(intent);
            });

            aboutButton.setOnClickListener(view->{
                Intent intent = new Intent(MainActivity.this, aboutActivity.class);
                intent.putExtra("key", about);
                startActivity(intent);
            });

        } catch (Exception ex) {
            Log.v(ex.getMessage(), "error");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public void getNumberData (EditText flight_num, TextView flight_data){
        flightNumber = flight_num.getText().toString();
        flightData = flight_data.getText().toString();
    }

    public int checkNumberData (String tableNumber){
        ArrayList<HashMap<String, Object>> report = new ArrayList<>();

        String query = "select number, data " +
                "from " + tableNumber +
                " where number = ? and data = ? ";
        String[] args={flightNumber, flightData};

        dbHelper = new DatabaseHelper(getApplicationContext());
        db = dbHelper.open();

        makeReport(query, args, report);
        db.close();
        return report.size();
    }

    private void makeReport(String query, String[] args, ArrayList<HashMap<String, Object>> report){
        cursor = db.rawQuery(query, args);
        HashMap<String, Object> reportHashMap;

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            reportHashMap = new HashMap<>();
            reportHashMap.put(DatabaseHelper.COLUMN_NUMBER,  cursor.getString(0));
            reportHashMap.put(DatabaseHelper.COLUMN_DATA,  cursor.getString(1));

            report.add(reportHashMap);
            cursor.moveToNext();
        }
        cursor.close();
    }

    private void callDatePicker(TextView editText) {
        final Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String editTextDateParam = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                    editText.setText(editTextDateParam);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
