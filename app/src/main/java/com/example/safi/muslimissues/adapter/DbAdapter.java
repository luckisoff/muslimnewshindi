package com.example.safi.muslimissues.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.safi.muslimissues.model.Quran;

import java.util.ArrayList;
import java.util.List;

public class DbAdapter extends SQLiteOpenHelper{

    private static final String dbname="data";
    private static final String table="quran_text";
    private static final String ID="id";
    private static final String SURA="sura";
    private static final String AYA="aya";
    private static final String TEXT="text";
    private static final int version=1;

    public DbAdapter(Context context){
        super(context,dbname,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE IF NOT EXISTS " + table + "("
                + ID+ " INTEGER PRIMARY KEY," + SURA+ " INTEGER,"
                + AYA+ " INTEGER,"
                +TEXT+" TEXT)";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table);
        onCreate(db);
    }

    public Quran getQuran(int sura) {

        SQLiteDatabase db = this.getReadableDatabase();
        Quran quran=new Quran();

        Cursor cursor = db.rawQuery("SELECT text FROM quran_text",null);

        while(cursor.moveToNext()) {

            String text=cursor.getString(cursor.getColumnIndex(TEXT));
            quran.setText(text);
        }
        System.out.println(cursor.getColumnIndex(TEXT));

        return quran;
    }

    public List<Quran> getAllNotes() {

        List<Quran> quranList = new ArrayList<Quran>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + table;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Quran quran = new Quran();
                quran.setId(Integer.parseInt(cursor.getString(0)));
                quran.setSura(cursor.getInt(1));
                quran.setAya(cursor.getInt(2));
                quran.setText(cursor.getString(3));
                // Adding note to list
                quranList.add(quran);
            } while (cursor.moveToNext());
        }

        // return note list
        return quranList;
    }


}
