package com.example.MentalHealthTracker.Statistics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// Handler to communicate with statistics database for activity usage
public class StatisticsDBHandler extends SQLiteOpenHelper {
    // attributes
    private static StatisticsDBHandler instance;
    private static final int db_version = 1;
    private static final String db_name = "ResourceStatistics.db";
    public static final String table_name = "ResourceStatistics";
    public static final String column_ID = "ID";
    public static final String column_TimesUsed = "TimesUsed";

    public static synchronized StatisticsDBHandler getInstance(Context context) {
        if (instance == null) {
            instance = new StatisticsDBHandler(context, null);
        }
        return instance;
    }

    // methods
    public StatisticsDBHandler(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, db_name, factory, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + table_name + "(" + column_ID + " INTEGER PRIMARY KEY,"
                + column_TimesUsed + " INTEGER" + ")";
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
            int ID = cursor.getInt(0);
            int numTimesUsed = cursor.getInt(1);
            result.append(ID).append(": ").append(numTimesUsed)
                    .append(System.getProperty("line.separator"));
        }
        cursor.close();
        db.close();
        return result.toString();
    }

    public void addHandler(Statistics statistic) {
        Statistics alreadyExists = findHandler(statistic.getID());
        if (alreadyExists == null) {
            ContentValues values = new ContentValues();
            values.put(column_ID, statistic.getID());
            values.put(column_TimesUsed, statistic.getNumTimesUsed());

            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(table_name, null, values);
            db.close();
        }
    }

    public Statistics findHandler(int ID) {
        String query = "SELECT * FROM " + table_name + " WHERE " + 
                column_ID + " = '" + ID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Statistics statistic = new Statistics();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            statistic.setID(cursor.getInt(0));
            statistic.setNumTimesUsed(cursor.getInt(1));
            cursor.close();
        }
        else {
            statistic = null;
        }
        db.close();
        return statistic;
    }

    public void updateHandler(int ID) {
        Statistics alreadyExists = findHandler(ID);
        if(alreadyExists != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            int numTimesUsed = alreadyExists.getNumTimesUsed() + 1;
            ContentValues values = new ContentValues();
            values.put(column_TimesUsed, numTimesUsed);
            db.update(table_name, values, column_ID + "=" + ID, null);
            db.close();
        }
    }

    public int getTimesUsedHandler(int ID) {
        String query = "SELECT * FROM " + table_name + " WHERE " +
                column_ID + " = '" + ID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int numTimesUsed;
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            numTimesUsed = cursor.getInt(1);
            cursor.close();
        }
        else {
            numTimesUsed = 0;
        }
        db.close();
        return numTimesUsed;
    }

    public void resetAllHandler() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(column_TimesUsed, 0);
        db.update(table_name, values, null, null);
        db.close();
    }

    public void deleteHandler(int ID) {
        String query = "SELECT * FROM " + table_name + " WHERE " + column_ID +
                "= '" + ID + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            db.delete(table_name, column_ID + "=?",
                    new String[] {String.valueOf(ID)});
            cursor.close();
        }
        db.close();
    }

    public void deleteAllHandler() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from " + table_name);
        db.close();
    }
}
