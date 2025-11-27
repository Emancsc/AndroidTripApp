package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AttractionDetailsActivity extends AppCompatActivity {

    private TextView tvName, tvLocation, tvType, tvDate, tvTime, tvFee, tvDuration, tvRating, tvNotes;
    private TextView tvFavorite, tvVisited;
    private Button btnEdit, btnDelete, btnBack;

    private SharedPreferencesManager manager;
    private Attraction attraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_details);

        // Initialize manager
        manager = new SharedPreferencesManager(this);

        // Get attraction ID from intent
        String attractionId = getIntent().getStringExtra("attraction_id");
        if (attractionId == null) {
            Toast.makeText(this, "Error loading attraction", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load attraction
        attraction = manager.getAttractionById(attractionId);
        if (attraction == null) {
            Toast.makeText(this, "Attraction not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize views
        initializeViews();

        // Display attraction details
        displayAttractionDetails();

        // Setup button listeners
        setupButtons();
    }

    private void initializeViews() {
        tvName = findViewById(R.id.tv_detail_name);
        tvLocation = findViewById(R.id.tv_detail_location);
        tvType = findViewById(R.id.tv_detail_type);
        tvDate = findViewById(R.id.tv_detail_date);
        tvTime = findViewById(R.id.tv_detail_time);
        tvFee = findViewById(R.id.tv_detail_fee);
        tvDuration = findViewById(R.id.tv_detail_duration);
        tvRating = findViewById(R.id.tv_detail_rating);
        tvNotes = findViewById(R.id.tv_detail_notes);
        tvFavorite = findViewById(R.id.tv_detail_favorite);
        tvVisited = findViewById(R.id.tv_detail_visited);

        btnEdit = findViewById(R.id.btn_detail_edit);
        btnDelete = findViewById(R.id.btn_detail_delete);
        btnBack = findViewById(R.id.btn_detail_back);
    }

    private void displayAttractionDetails() {
        tvName.setText(attraction.getName());
        tvLocation.setText(attraction.getLocation());
        tvType.setText(attraction.getType());
        tvDate.setText(attraction.getVisitDate());
        tvTime.setText(attraction.getVisitTime());
        tvFee.setText("$" + String.format("%.2f", attraction.getEntryFee()));
        tvDuration.setText(attraction.getDuration() + " hours");
        tvRating.setText(getStars(attraction.getRating()));

        // Display notes or default message
        if (attraction.getNotes() != null && !attraction.getNotes().trim().isEmpty()) {
            tvNotes.setText(attraction.getNotes());
        } else {
            tvNotes.setText("No notes available");
        }

        // Show/hide favorite and visited indicators
        if (attraction.isFavorite()) {
            tvFavorite.setVisibility(android.view.View.VISIBLE);
        } else {
            tvFavorite.setVisibility(android.view.View.GONE);
        }

        if (attraction.isVisited()) {
            tvVisited.setVisibility(android.view.View.VISIBLE);
        } else {
            tvVisited.setVisibility(android.view.View.GONE);
        }
    }

    private void setupButtons() {
        // Edit button
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(AttractionDetailsActivity.this, EditAttractionActivity.class);
            intent.putExtra("attraction_id", attraction.getAttractionId());
            startActivity(intent);
            finish(); // Close details activity after opening edit
        });

        // Delete button with confirmation
        btnDelete.setOnClickListener(v -> showDeleteConfirmation());

        // Back button
        btnBack.setOnClickListener(v -> finish());
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Attraction")
                .setMessage("Are you sure you want to delete '" + attraction.getName() + "'?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    manager.deleteAttraction(attraction.getAttractionId());
                    Toast.makeText(this, "Attraction deleted", Toast.LENGTH_SHORT).show();
                    finish(); // Go back to main activity
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private String getStars(int rating) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < rating; i++) {
            stars.append("⭐");
        }
        return stars.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload attraction in case it was edited
        attraction = manager.getAttractionById(attraction.getAttractionId());
        if (attraction != null) {
            displayAttractionDetails();
        }
    }
}