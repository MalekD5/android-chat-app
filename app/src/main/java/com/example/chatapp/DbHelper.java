package com.example.chatapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "search.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "infos";
    public static final String COLUMN_USER_ID = "userID";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_BODY = "body";

    private static final String SQL_CREATETABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_GENDER + " TEXT," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_BODY + " TEXT)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATETABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public Cursor viewAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, new String[]{COLUMN_USER_ID, COLUMN_TITLE}, null, null, null, null, null);
    }

}


