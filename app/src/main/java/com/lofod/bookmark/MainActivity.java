package com.lofod.bookmark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> filters = Arrays.asList(getResources().getStringArray(R.array.filter_entries));
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(this, R.layout.fashionable_spinner_item, filters);
        Spinner filterSpinner = findViewById(R.id.filterSpinner);
        filterSpinner.setAdapter(filterAdapter);

        List<String> sorts = Arrays.asList(getResources().getStringArray(R.array.sort_entries));
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(this, R.layout.fashionable_spinner_item, sorts);
        Spinner sortSpinner = findViewById(R.id.sortSpinner);
        sortSpinner.setAdapter(sortAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

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

    public void archiveClick(View view) {

    }
}
