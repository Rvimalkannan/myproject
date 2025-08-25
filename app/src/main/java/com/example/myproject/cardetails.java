package com.example.myproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class cardetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cardetails); // Make sure this matches your XML file name

        TextView tvTitle = findViewById(R.id.tvCarTitle);
        TextView tvPrice = findViewById(R.id.tvCarPrice);
        TextView tvDetails = findViewById(R.id.tvCarDetails1); // Add TextView for details
        ImageView carImageView = findViewById(R.id.imgCar);
        Button btnBuyNow = findViewById(R.id.btnBuyNow);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        // Get Data from Intent
        String title = getIntent().getStringExtra("car_title");
        String price = getIntent().getStringExtra("car_price");
        String details = getIntent().getStringExtra("car_details"); // Get details from intent
        int carImage = getIntent().getIntExtra("car_image", R.drawable.carimg); // Get the image resource, with carimg as fallback

        tvTitle.setText(title);
        tvPrice.setText(price);
        tvDetails.setText(details); // Set the details text

        carImageView.setImageResource(carImage); // Use the image resource from intent

        // Handle system insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the Buy Now button


        // Set click listener for the button
        btnBuyNow.setOnClickListener(v -> {
            Toast.makeText(cardetails.this, "Booking Confirmed", Toast.LENGTH_SHORT).show();
        });
    }
}
