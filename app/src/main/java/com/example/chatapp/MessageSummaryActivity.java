package com.example.chatapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MessageSummaryActivity extends AppCompatActivity {

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_summary_activity);

        dbHelper = new DbHelper(this);

        int userId = getIntent().getIntExtra("userId", -1);

        if (userId != -1) {
            String body = retrieveBody(userId);

            TextView bodyTextView = findViewById(R.id.Message_body);
            bodyTextView.setText(body);
        } else {
            Utils.openDialog(this, "ERROR", "Invalid user ID");
        }
    }

    private String retrieveBody(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {DbHelper.COLUMN_BODY};
        String selection = DbHelper.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        try (Cursor cursor = db.query(
                DbHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        )) {
            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_BODY));
            } else {
                return "No data found for user ID " + userId;
            }
        }
    }
}
