package com.example.cc_zaliczenie;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity implements SongAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private SongAdapter songAdapter;
    private List<Integer> songIds;
    private List<String> songTitles;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        songIds = new ArrayList<>();
        songTitles = new ArrayList<>();

        songAdapter = new SongAdapter(songTitles, this);
        recyclerView.setAdapter(songAdapter);

        databaseHelper = new DatabaseHelper(this);

        loadSongs();
    }

    private void loadSongs() {
        songIds.clear();
        songTitles.clear();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                int songId = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE));

                songIds.add(songId);
                songTitles.add(title);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        songAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {
        // Handle item click event
        int clickedSongId = songIds.get(position);
        Intent intent = new Intent(ViewActivity.this, UpdateDeleteActivity.class);
        intent.putExtra("id", clickedSongId);
        startActivity(intent);
    }
}
