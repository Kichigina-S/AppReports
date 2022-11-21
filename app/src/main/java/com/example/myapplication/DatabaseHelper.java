package com.example.myapplication;

import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH;
    public static final String DATABASE_NAME = "S7_Reports.db";
    public static final int SCHEMA = 1;
    public static final String TABLE_ALL = "F_ALL";
    public static final String TABLE_3R = "F_3R";
    public static final String TABLE_3L = "F_3L";
    public static final String TABLE_1L = "F_1L";

    public static final String COLUMN_ID_1L = "_id_1l";
    public static final String COLUMN_ID_3L = "_id_3l";
    public static final String COLUMN_ID_3R = "_id_3r";

    public static final String COLUMN_ID1 = "_id";
    public static final String COLUMN_IDL = "_id";
    public static final String COLUMN_IDR = "_id";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_BAGGAGE = "baggage";
    public static final String COLUMN_CARGO = "cargo";
    public static final String COLUMN_MAIL = "mail";
    public static final String COLUMN_WEAPON = "weapon";
    public static final String COLUMN_DANG = "dang";
    public static final String COLUMN_ANIMAL = "animal";

    public static final String COLUMN_Y = "y";
    public static final String COLUMN_C = "c";
    public static final String COLUMN_K = "k";
    public static final String COLUMN_CHML = "chml";
    public static final String COLUMN_MEML = "meml";
    public static final String COLUMN_FIML = "fiml";
    public static final String COLUMN_VGML = "vgml";
    public static final String COLUMN_BRML = "brml";
    public static final String COLUMN_TOTAL = "total";

    public static final String COLUMN_APPEAR = "appear";
    public static final String COLUMN_DOCS = "docs";
    public static final String COLUMN_BEG_PAX = "beg_pax";
    public static final String COLUMN_END_PAX = "end_pax";
    public static final String COLUMN_CLOSE = "close";
    public static final String COLUMN_DELAY = "delay";

    private final Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
        this.myContext=context;
        DB_PATH =context.getFilesDir().getPath() + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
    }

    void create_db(){
        File file = new File(DB_PATH);
        if (!file.exists()) {
            try(InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
                OutputStream myOutput = new FileOutputStream(DB_PATH)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
            }
            catch(IOException ex){
                Log.d("DatabaseHelper", ex.getMessage());
            }
        }
    }

    public SQLiteDatabase open()throws SQLException {
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }

}