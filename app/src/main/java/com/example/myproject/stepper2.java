package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;

public class stepper2 extends AppCompatActivity {

    private TextInputEditText etCarName, etBrand, etModel, etPrice, etLocation;
    private AutoCompleteTextView spinnerYear, spinnerCategory;
    private Button btnPrevious, btnContinue;
    private TextView tvStep;
    private ArrayList<String> selectedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper2);

        // Get selected images from previous step
        selectedImages = getIntent().getStringArrayListExtra("selected_images");

        initializeViews();
        setupDropdowns();
        setupClickListeners();
    }

    private void initializeViews() {
        etCarName = findViewById(R.id.etCarName);
        etBrand = findViewById(R.id.etBrand);
        etModel = findViewById(R.id.etModel);
        etPrice = findViewById(R.id.etPrice);
        etLocation = findViewById(R.id.etLocation);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnContinue = findViewById(R.id.btnContinue);
        tvStep = findViewById(R.id.tvStep);

        // Set step text
        tvStep.setText("Step 2 of 4");
    }

    private void setupDropdowns() {
        // Setup Year dropdown
        String[] years = {"2025", "2024", "2023", "2022", "2021", "2020"};
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                years
        );
        spinnerYear.setAdapter(yearAdapter);

        // Setup Category dropdown
        String[] categories = {"SUV", "Sedan", "Hatchback", "Sports Car", "Luxury"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                categories
        );
        spinnerCategory.setAdapter(categoryAdapter);
    }

    private void setupClickListeners() {
        btnPrevious.setOnClickListener(v -> finish());

        btnContinue.setOnClickListener(v -> {
            if (validateInputs()) {
                proceedToNextStep();
            }
        });
    }

    private boolean validateInputs() {
        if (etCarName.getText().toString().trim().isEmpty()) {
            etCarName.setError("Please enter car name");
            return false;
        }
        if (etBrand.getText().toString().trim().isEmpty()) {
            etBrand.setError("Please enter brand");
            return false;
        }
        if (etModel.getText().toString().trim().isEmpty()) {
            etModel.setError("Please enter model");
            return false;
        }
        if (spinnerYear.getText().toString().trim().isEmpty()) {
            spinnerYear.setError("Please select year");
            return false;
        }
        if (spinnerCategory.getText().toString().trim().isEmpty()) {
            spinnerCategory.setError("Please select category");
            return false;
        }
        if (etPrice.getText().toString().trim().isEmpty()) {
            etPrice.setError("Please enter price");
            return false;
        }
        if (etLocation.getText().toString().trim().isEmpty()) {
            etLocation.setError("Please enter location");
            return false;
        }
        return true;
    }

    private void proceedToNextStep() {
        Intent intent = new Intent(stepper2.this, stepper3.class);

        // Pass selected images along with other data
        intent.putStringArrayListExtra("selected_images", selectedImages);
        intent.putExtra("car_name", etCarName.getText().toString().trim());
        intent.putExtra("brand", etBrand.getText().toString().trim());
        intent.putExtra("model", etModel.getText().toString().trim());
        intent.putExtra("year", spinnerYear.getText().toString().trim());
        intent.putExtra("category", spinnerCategory.getText().toString().trim());
        intent.putExtra("price", etPrice.getText().toString().trim());
        intent.putExtra("location", etLocation.getText().toString().trim());

        startActivity(intent);
    }
}
