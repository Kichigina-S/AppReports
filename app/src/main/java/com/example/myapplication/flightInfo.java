package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class flightInfo extends AppCompatActivity {

    Cursor cursor;
    TextView textView;
    ListView userList;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_info);

        Bundle getActivity = getIntent().getExtras();
        String flightNumber = getActivity.getString("number");
        String flightData = getActivity.getString("data");

        userList = findViewById(R.id.list);
        textView = findViewById(R.id.info_textView);

        final Button backButton = findViewById(R.id.back_button);

        dbHelper = new DatabaseHelper(getApplicationContext());
        db = dbHelper.open();

        ArrayList<HashMap<String, Object>> report = new ArrayList<>();
        String query = "select F_ALL.number, F_ALL.data, baggage, cargo, mail, weapon, dang, animal," +
                "appear, docs, beg_pax, end_pax, close, delay, " +
                "y, c, k, chml, meml, fiml, vgml, brml, total " +
                "from F_ALL, F_1L, F_3L, F_3R where " +
                "F_1L._id = F_ALL._id_1l and F_3L._id = F_ALL._id_3l and F_3R._id = F_ALL._id_3r and F_ALL.number = ? and F_ALL.data = ? ";
        String[] args={flightNumber, flightData};

        makeReport(query, args, report);

        if (report.size() == 0 ){
            ContentValues contentValues = new ContentValues();
            String newQuery = " select F_1L.number, F_1L.data, F_1L._id, F_3L._id, F_3R._id " +
                    " from F_1L, F_3L, F_3R " +
                    " where F_1L.data = F_3L.data and F_1L.data = F_3R.data " +
                    " and F_1L.number = F_3L.number and F_1L.number = F_3R.number and F_1L.number = ? and F_1L.data = ? ";
            fillTableAll(newQuery, args, report, contentValues);
            if (report.size() == 0 ){
                textView.setText(R.string.noInfo);
            } else {
                db.insert(DatabaseHelper.TABLE_ALL, null, contentValues);
                report.clear();
                makeReport(query, args, report);
                printReport(report);
            }
        } else {
            printReport(report);
        }

        backButton.setOnClickListener(view ->{
            db.close();
            Intent intent = new Intent(flightInfo.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
        cursor.close();
    }

    public void printReport(ArrayList<HashMap<String, Object>> report){
            String[] from = {"number", "data", "baggage", "cargo", "mail", "weapon", "dang", "animal",
                    "appear", "docs", "beg_pax", "end_pax", "close", "delay",
                    "y", "k", "c", "chml", "meml", "fiml", "vgml", "brml", "total"};
            int[] to = {R.id.flightNumberText, R.id.flightDataText, R.id.baggageText, R.id.cargoText, R.id.mailText, R.id.weaponText, R.id.dangText, R.id.animalText,
                    R.id.appearText, R.id.docsText, R.id.beg_paxText, R.id.end_paxText, R.id.closeText, R.id.delayText,
                    R.id.yText, R.id.kText, R.id.cText, R.id.chmlText, R.id.memlText, R.id.fimlText, R.id.vgmlText, R.id.brmlText, R.id.totalText};

            SimpleAdapter adapter = new SimpleAdapter(this, report, R.layout.adapter_item, from, to);
            ListView listView = userList;
            listView.setAdapter(adapter);
    }

    private void makeReport(String query, String[] args, ArrayList<HashMap<String, Object>> report){
        cursor = db.rawQuery(query, args);
        HashMap<String, Object> reportHashMap;

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            reportHashMap = new HashMap<>();
            reportHashMap.put(DatabaseHelper.COLUMN_NUMBER,  cursor.getString(0));
            reportHashMap.put(DatabaseHelper.COLUMN_DATA,  cursor.getString(1));
            reportHashMap.put(DatabaseHelper.COLUMN_BAGGAGE,  cursor.getString(2));
            reportHashMap.put(DatabaseHelper.COLUMN_CARGO,  cursor.getString(3));
            reportHashMap.put(DatabaseHelper.COLUMN_MAIL,  cursor.getString(4));
            reportHashMap.put(DatabaseHelper.COLUMN_WEAPON,  cursor.getString(5));
            reportHashMap.put(DatabaseHelper.COLUMN_DANG,  cursor.getString(6));
            reportHashMap.put(DatabaseHelper.COLUMN_ANIMAL,  cursor.getString(7));

            reportHashMap.put(DatabaseHelper.COLUMN_APPEAR,  cursor.getString(8));
            reportHashMap.put(DatabaseHelper.COLUMN_DOCS,  cursor.getString(9));
            reportHashMap.put(DatabaseHelper.COLUMN_BEG_PAX,  cursor.getString(10));
            reportHashMap.put(DatabaseHelper.COLUMN_END_PAX,  cursor.getString(11));
            reportHashMap.put(DatabaseHelper.COLUMN_CLOSE,  cursor.getString(12));
            reportHashMap.put(DatabaseHelper.COLUMN_DELAY,  cursor.getString(13));

            reportHashMap.put(DatabaseHelper.COLUMN_Y,  cursor.getString(14));
            reportHashMap.put(DatabaseHelper.COLUMN_K,  cursor.getString(15));
            reportHashMap.put(DatabaseHelper.COLUMN_C,  cursor.getString(16));
            reportHashMap.put(DatabaseHelper.COLUMN_CHML,  cursor.getString(17));
            reportHashMap.put(DatabaseHelper.COLUMN_MEML,  cursor.getString(18));
            reportHashMap.put(DatabaseHelper.COLUMN_FIML,  cursor.getString(19));
            reportHashMap.put(DatabaseHelper.COLUMN_VGML,  cursor.getString(20));
            reportHashMap.put(DatabaseHelper.COLUMN_BRML,  cursor.getString(21));
            reportHashMap.put(DatabaseHelper.COLUMN_TOTAL,  cursor.getString(22));

            report.add(reportHashMap);
            cursor.moveToNext();
        }
        cursor.close();
    }

    public void fillTableAll(String query, String[] args, ArrayList<HashMap<String, Object>> report, ContentValues contentValues){
        HashMap<String, Object> reportHashMap;
        cursor = db.rawQuery(query, args);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            reportHashMap = new HashMap<>();
            reportHashMap.put(DatabaseHelper.COLUMN_NUMBER,  cursor.getString(0));
            reportHashMap.put(DatabaseHelper.COLUMN_DATA,  cursor.getString(1));
            reportHashMap.put(DatabaseHelper.COLUMN_ID1,  cursor.getString(2));
            reportHashMap.put(DatabaseHelper.COLUMN_IDL,  cursor.getString(3));
            reportHashMap.put(DatabaseHelper.COLUMN_IDR,  cursor.getString(4));

            report.add(reportHashMap);

            contentValues.put(DatabaseHelper.COLUMN_NUMBER, cursor.getString(0));
            contentValues.put(DatabaseHelper.COLUMN_DATA, cursor.getString(1));
            contentValues.put(DatabaseHelper.COLUMN_ID_1L, cursor.getString(2));
            contentValues.put(DatabaseHelper.COLUMN_ID_3L, cursor.getString(3));
            contentValues.put(DatabaseHelper.COLUMN_ID_3R, cursor.getString(4));
            cursor.moveToNext();
        }
        cursor.close();
    }
}