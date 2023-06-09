package com.example.cc_zaliczenie;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;


import androidx.appcompat.app.AppCompatActivity;

public class UpdateDeleteActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText authorEditText;
    private Button updateButton;
    private Button deleteButton;

    private int songId;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        titleEditText = findViewById(R.id.title_text);
        authorEditText = findViewById(R.id.author_text);
        updateButton = findViewById(R.id.update_button);
        deleteButton = findViewById(R.id.delete_button);

        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        songId = intent.getIntExtra("id", -1);

        if (songId == -1) {
            Toast.makeText(this, "Invalid song ID", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            loadSongDetails();
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTitle = titleEditText.getText().toString().trim();
                String updatedAuthor = authorEditText.getText().toString().trim();

                boolean isUpdated = databaseHelper.updateSong(songId, updatedTitle, updatedAuthor);

                if (isUpdated) {
                    Toast.makeText(UpdateDeleteActivity.this, "Song updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateDeleteActivity.this, "Failed to update song", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleted = databaseHelper.deleteSong(songId);

                if (isDeleted) {
                    Toast.makeText(UpdateDeleteActivity.this, "Song deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateDeleteActivity.this, "Failed to delete song", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void loadSongDetails() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " +
                DatabaseHelper.COLUMN_ID + " = " + songId, null);

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE));
            @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_AUTHOR));

            titleEditText.setText(title);
            authorEditText.setText(author);
        }

        cursor.close();
        db.close();
    }
}






