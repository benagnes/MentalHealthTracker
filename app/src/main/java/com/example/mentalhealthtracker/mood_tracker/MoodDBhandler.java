package com.example.mentalhealthtracker.mood_tracker;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import androidx.annotation.Nullable;

import com.example.mentalhealthtracker.mood_tracker.Mood;

// Handler to communicate with Mood Tracker database
public class MoodDBhandler extends SQLiteOpenHelper {
    // attributes
    private static final int db_version = 1;
    private static final String db_name = "moodTrackerDB.db";
    public static final String table_name = "Moods";
    public static final String column_ID = "ID";
    public static final String column_date = "Date";
    public static final String column_mood = "Mood";
    public static final String column_energy = "Energy";
    public static final String column_social_meter = "Social_Meter";
    public static final String column_productivity = "Productivity";

    // methods
    public MoodDBhandler(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, db_name, factory, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + table_name + "(" + column_ID + " INTEGER PRIMARY KEY," +
                column_date + " TEXT," + column_mood + " TEXT," + column_energy + " TEXT," + column_social_meter
                + " TEXT," + column_productivity + " TEXT" + ")";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public void closeConnection() {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db.isOpen()) {
            db.close();
        }
    }

    public String loadHandler() {
        StringBuilder result = new StringBuilder();
        String query = "SELECT * FROM " + table_name;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int iresult = cursor.getInt(0);
            String sresult = cursor.getString(1);
            String s2result = cursor.getString(2);
            String s3result = cursor.getString(3);
            String s4result = cursor.getString(4);
            String s5result = cursor.getString(5);
            result.append(iresult).append(": ").append(sresult).append(",").append(s2result).append(" ").append(
                    s3result).append(",").append(s4result).append(",").append(
                            s5result).append(System.getProperty("line.separator"));
        }
        cursor.close();
        db.close();
        return result.toString();
    }

    public void addHandler(Mood mood) {
        ContentValues values = new ContentValues();
        values.put(column_ID, mood.getID());
        values.put(column_date, mood.getDate());
        values.put(column_mood, mood.getMood());
        values.put(column_energy, mood.getEnergy());
        values.put(column_social_meter, mood.getSocial_meter());
        values.put(column_productivity, mood.getProductivity());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(table_name, null, values);
        db.close();
    }

    public Mood findHandler(String date) {
        String query = "SELECT * FROM " + table_name + " WHERE " + column_date + " = '" + date + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Mood mood = new Mood();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            mood.setID(cursor.getInt(0));
            mood.setDate(cursor.getString(1));
            mood.setMood(cursor.getString(2));
            mood.setEnergy(cursor.getString(3));
            mood.setSocial_meter(cursor.getString(4));
            mood.setProductivity(cursor.getString(5));
            cursor.close();
        }
        else {
            mood = null;
        }
        db.close();
        return mood;
    }

    public void deleteHandler(String date) {
        String query = "SELECT * FROM " + table_name + " WHERE " + column_date + "= '" + date + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Mood mood = new Mood();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            mood.setDate(cursor.getString(1));
            db.delete(table_name, column_date + "=?", new String[] { mood.getDate() });
            cursor.close();
        }
        db.close();
    }

    public boolean updateHandler(Integer ID, String mood, String energy, String social_meter, String productivity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(column_mood, mood);
        values.put(column_energy, energy);
        values.put(column_social_meter, social_meter);
        values.put(column_productivity, productivity);

        // db.update() returns # of rows affected
        boolean result = (db.update(table_name, values,column_ID + "=" + ID, null) > 0);
        db.close();
        return result;
    }
}
