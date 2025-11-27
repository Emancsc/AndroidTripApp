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

public class EditAttractionActivity extends AppCompatActivity {

    private EditText etName, etLocation, etFee, etDuration, etNotes;
    private Spinner spinnerType;
    private Button btnSelectDate, btnSelectTime, btnSave, btnCancel;
    private TextView tvSelectedDate, tvSelectedTime;
    private RadioGroup rgRating;
    private CheckBox cbFavorite, cbVisited;

    private SharedPreferencesManager manager;
    private String selectedDate = "";
    private String selectedTime = "";
    private Attraction currentAttraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_attraction);

        // Initialize
        manager = new SharedPreferencesManager(this);
        initializeViews();

        // Get attraction ID from intent
        String attractionId = getIntent().getStringExtra("attraction_id");
        currentAttraction = manager.getAttractionById(attractionId);

        // If found, populate form
        if (currentAttraction != null) {
            populateForm();
        }

        // Setup listeners
        btnSelectDate.setOnClickListener(v -> openDatePicker());
        btnSelectTime.setOnClickListener(v -> openTimePicker());
        btnSave.setOnClickListener(v -> updateAttraction());
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
        String[] types = {"Museum", "Park", "Restaurant", "Monument", "Beach", "Church", "Theater"};
        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
    }

    // Fill form with existing attraction data
    private void populateForm() {
        etName.setText(currentAttraction.getName());
        etLocation.setText(currentAttraction.getLocation());
        etFee.setText(String.valueOf(currentAttraction.getEntryFee()));
        etDuration.setText(String.valueOf(currentAttraction.getDuration()));
        etNotes.setText(currentAttraction.getNotes());

        // Set date and time
        selectedDate = currentAttraction.getVisitDate();
        tvSelectedDate.setText("Selected: " + selectedDate);

        selectedTime = currentAttraction.getVisitTime();
        tvSelectedTime.setText("Selected: " + selectedTime);

        // Set spinner to current type
        String[] types = {"Museum", "Park", "Restaurant", "Monument", "Beach", "Church", "Theater"};
        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(currentAttraction.getType())) {
                spinnerType.setSelection(i);
                break;
            }
        }

        // Set rating RadioButtons
        int rating = currentAttraction.getRating();
        if (rating == 1) rgRating.check(R.id.rb_1star);
        else if (rating == 2) rgRating.check(R.id.rb_2star);
        else if (rating == 3) rgRating.check(R.id.rb_3star);
        else if (rating == 4) rgRating.check(R.id.rb_4star);
        else if (rating == 5) rgRating.check(R.id.rb_5star);

        // Set checkboxes
        cbFavorite.setChecked(currentAttraction.isFavorite());
        cbVisited.setChecked(currentAttraction.isVisited());
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

    // Update existing attraction
    private void updateAttraction() {
        // Validate
        if (etName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter attraction name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update attraction fields
        currentAttraction.setName(etName.getText().toString().trim());
        currentAttraction.setLocation(etLocation.getText().toString().trim());
        currentAttraction.setType((String) spinnerType.getSelectedItem());

        // Parse fee
        try {
            currentAttraction.setEntryFee(Double.parseDouble(etFee.getText().toString().trim()));
        } catch (NumberFormatException e) {
            currentAttraction.setEntryFee(0);
        }

        currentAttraction.setVisitDate(selectedDate);
        currentAttraction.setVisitTime(selectedTime);

        // Parse duration
        try {
            currentAttraction.setDuration(Integer.parseInt(etDuration.getText().toString().trim()));
        } catch (NumberFormatException e) {
            currentAttraction.setDuration(1);
        }

        // Get rating from RadioGroup
        int selectedRatingId = rgRating.getCheckedRadioButtonId();
        if (selectedRatingId == R.id.rb_1star) currentAttraction.setRating(1);
        else if (selectedRatingId == R.id.rb_2star) currentAttraction.setRating(2);
        else if (selectedRatingId == R.id.rb_3star) currentAttraction.setRating(3);
        else if (selectedRatingId == R.id.rb_4star) currentAttraction.setRating(4);
        else if (selectedRatingId == R.id.rb_5star) currentAttraction.setRating(5);

        // Set checkboxes
        currentAttraction.setFavorite(cbFavorite.isChecked());
        currentAttraction.setVisited(cbVisited.isChecked());
        currentAttraction.setNotes(etNotes.getText().toString().trim());

        // Save updated attraction
        manager.updateAttraction(currentAttraction);
        Toast.makeText(this, "Attraction updated!", Toast.LENGTH_SHORT).show();

        // Close activity
        finish();
    }
}