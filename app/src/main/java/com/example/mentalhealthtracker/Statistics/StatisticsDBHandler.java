package com.example.mentalhealthtracker.Statistics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// Handler to communicate with statistics database for activity usage
public class StatisticsDBHandler extends SQLiteOpenHelper {
    // attributes
    private static final int db_version = 1;
    private static final String db_name = "ResourceStatistics.db";
    public static final String table_name = "ResourceStatistics";
    public static final String column_Resource = "Resource";
    public static final String column_TimesUsed = "TimesUsed";

    // methods
    public StatisticsDBHandler(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, db_name, factory, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + table_name + "(" + column_Resource + " TEXT," +
                column_TimesUsed + " INTEGER" + ")";
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
            String resource = cursor.getString(0);
            int numTimesUsed = cursor.getInt(1);
            result.append(resource).append(": ").append(numTimesUsed)
                    .append(System.getProperty("line.separator"));
        }
        cursor.close();
        db.close();
        return result.toString();
    }

    public void addHandler(Statistics statistic) {
        ContentValues values = new ContentValues();
        values.put(column_Resource, statistic.getResource());
        values.put(column_TimesUsed, statistic.getNumTimesUsed());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(table_name, null, values);
        db.close();
    }

    public Statistics findHandler(String resource) {
        String query = "SELECT * FROM " + table_name + " WHERE " + 
                column_Resource + " = '" + resource + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Statistics statistic = new Statistics();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            statistic.setResource(cursor.getString(0));
            statistic.setNumTimesUsed(cursor.getInt(1));
            cursor.close();
        }
        else {
            statistic = null;
        }
        db.close();
        return statistic;
    }

    public void updateHandler(String resource) {
        SQLiteDatabase db = this.getWritableDatabase();
        Statistics statistic = findHandler(resource);
        if(statistic == null) {
            Statistics newStatistic = new Statistics(resource, 1);
            addHandler(newStatistic);
        }
        else{
            int numTimesUsed = statistic.getNumTimesUsed() + 1;
            ContentValues values = new ContentValues();
            values.put(column_TimesUsed, numTimesUsed);
            db.update(table_name, values, column_Resource +
                    "=" + resource, null);
        }
        db.close();
    }

    public void deleteHandler(String resource) {
        String query = "SELECT * FROM " + table_name + " WHERE " + column_Resource +
                "= '" + resource + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Statistics statistic = new Statistics();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            statistic.setResource(cursor.getString(0));
            db.delete(table_name, column_Resource + "=?",
                    new String[] {String.valueOf(statistic.getResource())});
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
