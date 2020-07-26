package com.lofod.bookmark;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ArrayList<Card> data;
    CardAdapter adapter;
    private int lastSelectedFilter;
    private int lastSelectedSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Spinner with filters
        List<String> filters = Arrays.asList(getResources().getStringArray(R.array.filter_entries));
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(this, R.layout.fashionable_spinner_item, filters);
        Spinner filterSpinner = findViewById(R.id.filterSpinner);
        filterSpinner.setAdapter(filterAdapter);
        lastSelectedFilter = filterSpinner.getSelectedItemPosition();
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != lastSelectedFilter) {
                    lastSelectedFilter = i;
                    /*TODO запрос к БД и передача новых карточек в adapter*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Spinner with sorting options
        List<String> sorts = Arrays.asList(getResources().getStringArray(R.array.sort_entries));
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(this, R.layout.fashionable_spinner_item, sorts);
        Spinner sortSpinner = findViewById(R.id.sortSpinner);
        sortSpinner.setAdapter(sortAdapter);
        lastSelectedSort = sortSpinner.getSelectedItemPosition();
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != lastSelectedSort) {
                    lastSelectedSort = i;
                    /*TODO запрос к БД и передача новых карточек в adapter*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getData();

        // RecyclerView
        adapter = new CardAdapter(data, getFilesDir());
        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    void getData() {
        data = new ArrayList<>();
        Card card = new Card();
        card.bookImg = "default";
        card.rating = 2;
        card.description = "Очень интересно, но нихуя не понятно";
        card.seriesAndDate = "Болшая серия | 26.07.2020";
        card.author = "Очень уважаемый человек";
        ArrayList<String> tags = new ArrayList<>();
        tags.add("тэг1");
        tags.add("tag2");
        tags.add("taг3");
        card.tags = tags;
        card.bookName = "Книжечкол";
        data.add(card);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {     // main.xml
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    //
    //  Search
    //
    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast toast = Toast.makeText(this, "Working!", Toast.LENGTH_SHORT);
        toast.show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }


    //
    //  onClick listeners
    //
    public void addBookClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AddBookActivity.class);
        startActivityForResult(intent, AddBookActivity.resultCode);
    }

    public void archiveClick(View view) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddBookActivity.resultCode) {
            if (data == null)
                return;

            Card newCard = data.getParcelableExtra("newCard");
            adapter.addCard(newCard);
            /*TODO добавление в БД*/
        }
    }
}
