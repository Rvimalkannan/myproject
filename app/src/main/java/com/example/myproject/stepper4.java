package com.example.myproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class stepper4 extends AppCompatActivity {

    // UI Components
    private ImageView backButton, carImage;
    private TextView carTitle, carPrice, carLocation,
            fuelType, transmission, kmsDriven, ownerCount, description;
    private ProgressBar progressBar;
    private Switch featureSwitch;
    private Button btnPrevious, btnPublish;
    private ArrayList<String> selectedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper4); // <-- Change to your XML file name

        // Get selected images from intent
        selectedImages = getIntent().getStringArrayListExtra("selected_images");

        // Initialize Views
        initializeViews();

        // Set the first image to carImage if available
        if (selectedImages != null && !selectedImages.isEmpty()) {
            try {
                carImage.setImageURI(Uri.parse(selectedImages.get(0)));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
            }
        }

        // Set data from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            carTitle.setText(extras.getString("car_name", ""));
            carPrice.setText(extras.getString("price", ""));
            carLocation.setText(extras.getString("location", ""));
            fuelType.setText(extras.getString("fuel_type", ""));
            transmission.setText(extras.getString("transmission", ""));
            kmsDriven.setText(extras.getString("kms_driven", "") + " km");
            ownerCount.setText(extras.getString("owner_count", ""));
            description.setText(extras.getString("description", ""));
        }

        setupClickListeners();
    }

    private void initializeViews() {
        backButton = findViewById(R.id.backButton);   // Add id in XML if missing
        carImage = findViewById(R.id.carImage);
        carTitle = findViewById(R.id.carTitle);       // Add id in XML
        carPrice = findViewById(R.id.carPrice);       // Add id in XML
        carLocation = findViewById(R.id.carLocation); // Add id in XML
        fuelType = findViewById(R.id.fuelType);       // Add id in XML
        transmission = findViewById(R.id.transmission);
        kmsDriven = findViewById(R.id.kmsDriven);
        ownerCount = findViewById(R.id.ownerCount);
        description = findViewById(R.id.description);
        progressBar = findViewById(R.id.progressBar);
        featureSwitch = findViewById(R.id.featureSwitch); // Add id in XML
        btnPrevious = findViewById(R.id.btnPrevious);     // Add id in XML
        btnPublish = findViewById(R.id.btnPublish);       // Add id in XML
    }

    private void setupClickListeners() {
        // Back button
        backButton.setOnClickListener(v -> onBackPressed());

        // Switch
        featureSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, "Feature enabled: More visibility!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Feature disabled", Toast.LENGTH_SHORT).show();
            }
        });

        // Previous button
        btnPrevious.setOnClickListener(v -> {
            // Go back to Step 3
            finish(); // or navigate to stepper3 activity
        });

        // Publish button
        btnPublish.setOnClickListener(v -> {
            Toast.makeText(this, "Car Published Successfully!", Toast.LENGTH_LONG).show();

            // Example: Navigate to dashboard/home after publishing
            Intent intent = new Intent(stepper4.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}


