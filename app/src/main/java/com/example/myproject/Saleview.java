package com.example.myproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class Saleview extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    EditText etCarName, etVariant, etOwners, etInsuranceDate,
            etKmDriven, etLocation, etPrice, etRemarks;
    Spinner spinnerYear, spinnerFuel, spinnerTransmission;
    ImageView ivCarPhoto;
    Button btnSaveCar;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saleview);

        // Initialize views
        etCarName = findViewById(R.id.etCarName);
        etVariant = findViewById(R.id.etVariant);
        etOwners = findViewById(R.id.etOwners);
        etInsuranceDate = findViewById(R.id.etInsuranceDate);
        etKmDriven = findViewById(R.id.etKmDriven);
        etLocation = findViewById(R.id.etLocation);
        etPrice = findViewById(R.id.etPrice);
        etRemarks = findViewById(R.id.etRemarks);

        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerFuel = findViewById(R.id.spinnerFuel);
        spinnerTransmission = findViewById(R.id.spinnerTransmission);

        ivCarPhoto = findViewById(R.id.ivCarPhoto);
        btnSaveCar = findViewById(R.id.btnSaveCar);

        // Setup spinners
        setupYearSpinner();
        setupFuelSpinner();
        setupTransmissionSpinner();

        // 📷 Open gallery when clicking photo container
        findViewById(R.id.photoUploadContainer).setOnClickListener(v -> openGallery());

        // 📅 Open DatePicker for insurance date
        etInsuranceDate.setOnClickListener(v -> showDatePicker());

        // 💾 Save button click
        btnSaveCar.setOnClickListener(v -> saveCarDetails());
    }

    private void setupYearSpinner() {
        ArrayList<String> years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i >= 1990; i--) {
            years.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapter);
    }

    private void setupFuelSpinner() {
        String[] fuels = {"Petrol", "Diesel", "CNG", "Electric", "Hybrid"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, fuels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFuel.setAdapter(adapter);
    }

    private void setupTransmissionSpinner() {
        String[] transmissions = {"Manual", "Automatic"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, transmissions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTransmission.setAdapter(adapter);
    }

    // 📷 Pick image from gallery
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        this.getContentResolver(), imageUri);
                ivCarPhoto.setImageBitmap(bitmap);
                ivCarPhoto.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 📅 Date picker for insurance end date
    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, month1, dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                    etInsuranceDate.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    // 💾 Collect and show details
    private void saveCarDetails() {
        String details =
                "Car Name: " + etCarName.getText().toString() + "\n" +
                        "Variant: " + etVariant.getText().toString() + "\n" +
                        "Owners: " + etOwners.getText().toString() + "\n" +
                        "Year: " + spinnerYear.getSelectedItem().toString() + "\n" +
                        "Insurance End: " + etInsuranceDate.getText().toString() + "\n" +
                        "Fuel: " + spinnerFuel.getSelectedItem().toString() + "\n" +
                        "KM Driven: " + etKmDriven.getText().toString() + "\n" +
                        "Transmission: " + spinnerTransmission.getSelectedItem().toString() + "\n" +
                        "Location: " + etLocation.getText().toString() + "\n" +
                        "Price: " + etPrice.getText().toString() + "\n" +
                        "Remarks: " + etRemarks.getText().toString();

        Toast.makeText(this, details, Toast.LENGTH_LONG).show();
    }
}
