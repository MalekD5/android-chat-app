package com.example.chatapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "messages";
    public static final String COLUMN_USER_ID = "userID";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_BODY = "body";

    private static final String SQL_CREATE_TABLE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT)",
            TABLE_NAME,
            COLUMN_USER_ID,
            COLUMN_GENDER,
            COLUMN_TITLE,
            COLUMN_BODY
    );

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public Cursor viewAll() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = { COLUMN_USER_ID, COLUMN_TITLE };
        return db.query(TABLE_NAME, projection, null, null, null, null, null);
    }
}



