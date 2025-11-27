package com.example.assignment1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AddAttractionActivity extends AppCompatActivity {

    private EditText etName, etLocation, etFee, etDuration, etNotes;
    private Spinner spinnerType;
    private Button btnSelectDate, btnSelectTime, btnSave, btnCancel;
    private TextView tvSelectedDate, tvSelectedTime;
    private RadioGroup rgRating;
    private CheckBox cbFavorite, cbVisited;

    private SharedPreferencesManager manager;
    private String selectedDate = "";
    private String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attraction);

        // Initialize
        manager = new SharedPreferencesManager(this);
        initializeViews();

        // Setup listeners
        btnSelectDate.setOnClickListener(v -> openDatePicker());
        btnSelectTime.setOnClickListener(v -> openTimePicker());
        btnSave.setOnClickListener(v -> saveAttraction());
        btnCancel.setOnClickListener(v -> finish());

        // Setup spinner
        setupSpinner();
    }

    // Find all views
    private void initializeViews() {
        etName = findViewById(R.id.et_name);
        etLocation = findViewById(R.id.et_location);
        etFee = findViewById(R.id.et_fee);
        etDuration = findViewById(R.id.et_duration);
        etNotes = findViewById(R.id.et_notes);
        spinnerType = findViewById(R.id.spinner_type);
        btnSelectDate = findViewById(R.id.btn_select_date);
        btnSelectTime = findViewById(R.id.btn_select_time);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
        tvSelectedDate = findViewById(R.id.tv_selected_date);
        tvSelectedTime = findViewById(R.id.tv_selected_time);
        rgRating = findViewById(R.id.rg_rating);
        cbFavorite = findViewById(R.id.cb_favorite);
        cbVisited = findViewById(R.id.cb_visited);
    }

    // Setup spinner with attraction types
    private void setupSpinner() {
        String[] types = {"Museum", "Park", "Restaurant", "Monument","mosque","Beach", "Church", "Theater","stadium"};
        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
    }

    // Open DatePickerDialog
    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
            tvSelectedDate.setText("Selected: " + selectedDate);
        }, year, month, day);
        dialog.show();
    }

    // Open TimePickerDialog
    private void openTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
            selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
            tvSelectedTime.setText("Selected: " + selectedTime);
        }, hour, minute, true);
        dialog.show();
    }

    // Save new attraction
    private void saveAttraction() {
        // Validate inputs
        if (etName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter attraction name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedTime.isEmpty()) {
            Toast.makeText(this, "Please select time", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create new attraction object
        Attraction attraction = new Attraction();
        attraction.setName(etName.getText().toString().trim());
        attraction.setLocation(etLocation.getText().toString().trim());
        attraction.setType((String) spinnerType.getSelectedItem());

        // Parse fee
        try {
            attraction.setEntryFee(Double.parseDouble(etFee.getText().toString().trim()));
        } catch (NumberFormatException e) {
            attraction.setEntryFee(0);
        }

        attraction.setVisitDate(selectedDate);
        attraction.setVisitTime(selectedTime);

        // Parse duration
        try {
            attraction.setDuration(Integer.parseInt(etDuration.getText().toString().trim()));
        } catch (NumberFormatException e) {
            attraction.setDuration(1);
        }

        // Get rating from RadioGroup
        int selectedRatingId = rgRating.getCheckedRadioButtonId();
        if (selectedRatingId == R.id.rb_1star) attraction.setRating(1);
        else if (selectedRatingId == R.id.rb_2star) attraction.setRating(2);
        else if (selectedRatingId == R.id.rb_3star) attraction.setRating(3);
        else if (selectedRatingId == R.id.rb_4star) attraction.setRating(4);
        else if (selectedRatingId == R.id.rb_5star) attraction.setRating(5);
        else attraction.setRating(1); // Default

        // Set checkboxes
        attraction.setFavorite(cbFavorite.isChecked());
        attraction.setVisited(cbVisited.isChecked());
        attraction.setNotes(etNotes.getText().toString().trim());

        // Save to SharedPreferences
        manager.addAttraction(attraction);
        Toast.makeText(this, "Attraction added!", Toast.LENGTH_SHORT).show();

        // Close activity
        finish();
    }
}