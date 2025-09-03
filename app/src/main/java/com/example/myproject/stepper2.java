package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class stepper2 extends AppCompatActivity {

    private EditText etCarName, etBrand, etModel, etPrice, etLocation;
    private Spinner spinnerYear, spinnerCategory;
    private Button btnPrevious, btnContinue;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper2); // make sure this matches your XML filename

        // find views
        etCarName = findViewById(R.id.etCarName);
        etBrand = findViewById(R.id.etBrand);
        etModel = findViewById(R.id.etModel);
        etPrice = findViewById(R.id.etPrice);
        etLocation = findViewById(R.id.etLocation);

        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        btnPrevious = findViewById(R.id.btnPrevious);
        btnContinue = findViewById(R.id.btnContinue);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(50); // Step 2 of 4

        Intent intentImage = getIntent();
        ArrayList<String> selectedImages = intentImage.getStringArrayListExtra("selected_images");


        // --- Populate Year spinner dynamically: "Select Year", current year -> 1990 ---
        List<String> years = new ArrayList<>();
        years.add("Select Year");
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int y = currentYear; y >= 1990; y--) {
            years.add(String.valueOf(y));
        }

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);
        spinnerYear.setSelection(0); // default to "Select Year"

        // Optional: listen to selection changes (not required)
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // position 0 -> "Select Year"
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });

        // --- Populate Category spinner ---
        String[] categories = new String[]{
                "Select Category",
                "SUV",
                "Sedan",
                "Hatchback",
                "Luxury",
                "Convertible",
                "Truck",
                "Van"
        };

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);
        spinnerCategory.setSelection(0);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // position 0 -> "Select Category"
            }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Previous button: go back (or implement your own behavior)
        btnPrevious.setOnClickListener(v -> finish());

        // Continue button: validate and navigate to next step
        btnContinue.setOnClickListener(v -> {
            String carName = etCarName.getText().toString().trim();
            String brand = etBrand.getText().toString().trim();
            String model = etModel.getText().toString().trim();
            String price = etPrice.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String year = spinnerYear.getSelectedItem().toString();
            String category = spinnerCategory.getSelectedItem().toString();

            // validation
            if (carName.isEmpty() || brand.isEmpty() || model.isEmpty()
                    || price.isEmpty() || location.isEmpty()
                    || year.equals("Select Year") || category.equals("Select Category")) {
                Toast.makeText(stepper2.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // pass data to next activity (Stepper3Activity)
            Intent intent = new Intent(stepper2.this, stepper3.class);
            intent.putStringArrayListExtra("selected_images", selectedImages);
            intent.putExtra("carName", carName);
            intent.putExtra("brand", brand);
            intent.putExtra("model", model);
            intent.putExtra("year", year);
            intent.putExtra("category", category);
            intent.putExtra("price", price);
            intent.putExtra("location", location);
            startActivity(intent);
        });
    }
}
