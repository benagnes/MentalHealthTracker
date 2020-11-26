package com.example.mentalhealthtracker.FavouriteResources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// Handler to communicate with Favourite resources database
public class FavouriteDBHandler extends SQLiteOpenHelper {
    // attributes
    private static final int db_version = 1;
    private static final String db_name = "FavouriteResources.db";
    public static final String table_name = "FavouriteResources";
    public static final String column_ID = "FavouriteNumber";
    public static final String column_Resource = "Resource";

    // methods
    public FavouriteDBHandler(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, db_name, factory, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + table_name + "(" + column_ID + " INTEGER PRIMARY KEY," +
                column_Resource + " TEXT" + ")";
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
            int FavNumresult = cursor.getInt(0);
            String ResourceResult = cursor.getString(1);
            result.append(FavNumresult).append(": ").append(ResourceResult).append(System.getProperty("line.separator"));
        }
        cursor.close();
        db.close();
        return result.toString();
    }

    public void addHandler(Favourite favourite) {
        ContentValues values = new ContentValues();
        values.put(column_ID, favourite.getFavouriteNumber());
        values.put(column_Resource, favourite.getResource());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(table_name, null, values);
        db.close();
    }

    public Favourite findHandler(int FavouriteNumber) {
        String query = "SELECT * FROM " + table_name + " WHERE " + column_ID + " = '" + FavouriteNumber + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Favourite favourite = new Favourite();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            favourite.setFavouriteNumber(cursor.getInt(0));
            favourite.setResource(cursor.getString(1));
            cursor.close();
        }
        else {
            favourite = null;
        }
        db.close();
        return favourite;
    }

    public void deleteHandler(int FavouriteNumber) { // TO-DO: will this work??
        String query = "SELECT * FROM " + table_name + " WHERE " + column_ID + "= '" + FavouriteNumber + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Favourite favourite = new Favourite();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            favourite.setFavouriteNumber(cursor.getInt(0));
            db.delete(table_name, column_ID + "=?", new String[] {String.valueOf(favourite.getFavouriteNumber())});
            cursor.close();
        }
        db.close();
    }

    public boolean updateHandler(int FavouriteNumber, String Resource) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(column_ID, FavouriteNumber);
        values.put(column_Resource, Resource);

        // db.update() returns # of rows affected
        boolean result = (db.update(table_name, values,column_ID + "=" + FavouriteNumber, null) > 0);
        db.close();
        return result;
    }
}
