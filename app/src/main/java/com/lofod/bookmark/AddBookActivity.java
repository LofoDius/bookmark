package com.lofod.bookmark;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AddBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        setTitle(getString(R.string.add_book_title));

    }
}