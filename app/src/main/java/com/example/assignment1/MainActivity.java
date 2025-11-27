package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AttractionAdapter.OnAttractionClickListener {

    private RecyclerView recyclerView;
    private AttractionAdapter adapter;
    private SearchView searchView;
    private TextView emptyMessage;
    private Button btnAdd;

    private SharedPreferencesManager manager;
    private List<Attraction> allAttractions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize manager
        manager = new SharedPreferencesManager(this);

        // Find views
        recyclerView = findViewById(R.id.recycler_view);
        searchView = findViewById(R.id.search_view);
        emptyMessage = findViewById(R.id.empty_list_message);
        btnAdd = findViewById(R.id.btn_add_attraction);

        // Check if button exists
        if (btnAdd == null) {
            android.util.Log.e("MainActivity", "Button not found! Check your XML");
        }

        // Setup RecyclerView
        setupRecyclerView();

        // Setup SearchView
        setupSearchView();

        // Setup Add Button
        setupAddButton();

        // Load attractions
        loadAttractions();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allAttractions = manager.getAllAttractions();
        adapter = new AttractionAdapter(allAttractions, this, this);
        recyclerView.setAdapter(adapter);
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    adapter.updateList(allAttractions);
                } else {
                    List<Attraction> filtered = manager.searchAttractions(newText);
                    adapter.updateList(filtered);
                }
                return true;
            }
        });
    }

    private void setupAddButton() {
        if (btnAdd != null) {
            btnAdd.setOnClickListener(v -> {
                android.util.Log.d("MainActivity", "Add button clicked!");
                Intent intent = new Intent(MainActivity.this, AddAttractionActivity.class);
                startActivity(intent);
            });
        }
    }

    private void loadAttractions() {
        allAttractions = manager.getAllAttractions();

        if (allAttractions.isEmpty()) {
            emptyMessage.setVisibility(android.view.View.VISIBLE);
            recyclerView.setVisibility(android.view.View.GONE);
        } else {
            emptyMessage.setVisibility(android.view.View.GONE);
            recyclerView.setVisibility(android.view.View.VISIBLE);
            adapter.updateList(allAttractions);
        }
    }

    // Handle item click - open details activity
    @Override
    public void onAttractionClick(Attraction attraction) {
        Intent intent = new Intent(MainActivity.this, AttractionDetailsActivity.class);
        intent.putExtra("attraction_id", attraction.getAttractionId());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAttractions();
    }
}