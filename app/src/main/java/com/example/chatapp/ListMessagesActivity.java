package com.example.chatapp;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListMessagesActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private final ArrayList<String> messages = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_messages_activity);
        MediaPlayer aud = MediaPlayer.create(this, R.raw.notification);
        aud.start();
        dbHelper = new DbHelper(this);
        Toast.makeText(ListMessagesActivity.this, "Success", Toast.LENGTH_SHORT).show();

        ListView listView = findViewById(R.id.listViewss);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages);
        listView.setAdapter(arrayAdapter);

        fetchAndPopulateListView();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = arrayAdapter.getItem(position);

            assert selectedItem != null;
            String[] parts = selectedItem.split("\n");
            if (parts.length == 2) {
                String userIdString = parts[0];
                int userId = Integer.parseInt(userIdString);


                Intent intent = new Intent(ListMessagesActivity.this, MessageSummaryActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
    }

    private void fetchAndPopulateListView() {
        try (Cursor cursor = dbHelper.viewAll()) {
            if (cursor.moveToFirst()) {
                int userIdColumnIndex = cursor.getColumnIndexOrThrow(DbHelper.COLUMN_USER_ID);
                int titleColumnIndex = cursor.getColumnIndexOrThrow(DbHelper.COLUMN_TITLE);

                while (!cursor.isAfterLast()) {
                    int userId = cursor.getInt(userIdColumnIndex);
                    String title = cursor.getString(titleColumnIndex);

                    String concatenatedString = userId + "\n" + title;
                    messages.add(concatenatedString);

                    cursor.moveToNext();
                }

                arrayAdapter.notifyDataSetChanged();
            }
        }
    }
}
