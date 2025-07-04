package com.example.chatapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import java.util.OptionalInt;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ImageView img = findViewById(R.id.img);
        EditText userid = findViewById(R.id.userid);
        EditText input_title = findViewById(R.id.input_title);
        EditText input_body = findViewById(R.id.input_body);
        RadioButton male = findViewById(R.id.male);
        RadioButton female = findViewById(R.id.female);
        Button spButton = findViewById(R.id.sp);
        Button sqliteButton = findViewById(R.id.sqlite);

        sqliteButton.setOnClickListener(view -> {
            String rawUserId = userid.getText().toString();

            OptionalInt optionalUserId = Utils.parseNumber(rawUserId);

            if (!optionalUserId.isPresent()) {
                Utils.openDialog(this, "ERROR", "User id must be a number");
                return;
            }

            int userId = optionalUserId.getAsInt();

            String gender = "";

            if (male.isChecked()) {
                gender = "Male";
            } else if (female.isChecked()) {
                gender = "Female";
            }

            String title = input_title.getText().toString();
            String body = input_body.getText().toString();

            if (title.isEmpty() || body.isEmpty() || gender.isEmpty()) {
                Utils.openDialog(this, "ERROR", "You must enter title, body, and gender first!");
                return;
            }

            try (DbHelper dbHelper = new DbHelper(this)) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();

                contentValues.put(DbHelper.COLUMN_USER_ID, userId);
                contentValues.put(DbHelper.COLUMN_GENDER, gender);
                contentValues.put(DbHelper.COLUMN_TITLE, title);
                contentValues.put(DbHelper.COLUMN_BODY, body);

                long returnId = db.insert(DbHelper.TABLE_NAME, null, contentValues);
                if (returnId == -1) {
                    Utils.openDialog(this, "ERROR", "You can't have a user with the same id");
                    return;
                }
            }

            Utils.clearInputs(findViewById(R.id.mainLayout));

            startActivity(new Intent(MainActivity.this, ListMessagesActivity.class));
        });

        spButton.setOnClickListener(v -> {
            if (male.isChecked()) {
                img.setImageResource(R.drawable.man);
            } else if (female.isChecked()) {
                img.setImageResource(R.drawable.female);
            } else {
                img.setImageResource(R.drawable.d);
            }

            String userId = userid.getText().toString();
            String messageBody = input_body.getText().toString();

            if (userId.isEmpty() || messageBody.isEmpty()) {
                Utils.openDialog(this, "ERROR", "You must enter userId and message first!");
                return;
            }

            SharedPreferences sharedPreferences = getSharedPreferences("content", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("id", userId);
            editor.putString("body", messageBody);
            editor.apply();

            String spUserId = sharedPreferences.getString("id", "defaultUserId");
            String spMessageBody = sharedPreferences.getString("body", "defaultMessageBody");

            String s = """
            User id: %s
            Message: %s
            """;

            Utils.openDialog(this, "Review Message", String.format(s, spUserId, spMessageBody));
        });

    }

}

