package com.example.cc_zaliczenie;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText authorEditText;
    private Button addButton;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        titleEditText = findViewById(R.id.title_input);
        authorEditText = findViewById(R.id.author_input);
        addButton = findViewById(R.id.save_button);

        databaseHelper = new DatabaseHelper(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString().trim();
                String author = authorEditText.getText().toString().trim();

                long result = databaseHelper.addSong(title, author);

                if (result != -1) {
                    Toast.makeText(AddActivity.this, "Song added successfully", Toast.LENGTH_SHORT).show();
                    titleEditText.setText("");
                    authorEditText.setText("");
                } else {
                    Toast.makeText(AddActivity.this, "Failed to add song", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}



