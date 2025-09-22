package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper2);

        // --- Bind Views ---
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

        ivBack = findViewById(R.id.backrow); // you may need to set android:id="@+id/back_arrow" in XML for ImageView

        Intent intentImage = getIntent();
        ArrayList<String> selectedImages = intentImage.getStringArrayListExtra("selected_images");

        // --- Back Arrow Click ---
        ivBack.setOnClickListener(v -> finish()); // finish current activity to go back

        // --- Populate Year spinner dynamically ---
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
        spinnerYear.setSelection(0);

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }
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
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }
            @Override public void onNothingSelected(AdapterView<?> parent) { }
        });

        // --- Previous Button ---
        btnPrevious.setOnClickListener(v -> finish()); // go back

        // --- Continue Button ---
        btnContinue.setOnClickListener(v -> {
            String carName = etCarName.getText().toString().trim();
            String brand = etBrand.getText().toString().trim();
            String model = etModel.getText().toString().trim();
            String price = etPrice.getText().toString().trim();
            String location = etLocation.getText().toString().trim();
            String year = spinnerYear.getSelectedItem().toString();
            String category = spinnerCategory.getSelectedItem().toString();

            // --- Validation ---
            if (carName.isEmpty() || brand.isEmpty() || model.isEmpty()
                    || price.isEmpty() || location.isEmpty()
                    || year.equals("Select Year") || category.equals("Select Category")) {
                Toast.makeText(stepper2.this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // --- Pass data to next step ---
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
