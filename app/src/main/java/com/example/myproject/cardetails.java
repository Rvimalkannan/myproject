package com.example.myproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class cardetails extends AppCompatActivity {

    ViewPager2 viewPager;
    TextView tvCarName, tvCarModel, tvCarPrice, tvCarYear, tvCarKm, tvCarFuel, tvCarOwner, tvCarLocation;
    Button btnCallDealer, btnTestDrive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardetails);

        // Bind Views
        viewPager = findViewById(R.id.viewPager2);
        tvCarName = findViewById(R.id.tvCarName);
        tvCarModel = findViewById(R.id.tvCarModel);
        tvCarPrice = findViewById(R.id.tvCarPriceDetails);
        tvCarYear = findViewById(R.id.tvCarYear);
        tvCarKm = findViewById(R.id.tvCarKm);
        tvCarFuel = findViewById(R.id.tvCarFuel);
        tvCarOwner = findViewById(R.id.tvCarOwner);
        tvCarLocation = findViewById(R.id.tvCarLocation);
        btnCallDealer = findViewById(R.id.btnCallDealer);
        btnTestDrive = findViewById(R.id.btnTestDrive);

        // Get Data from Intent
        String name = getIntent().getStringExtra("carName");
        String model = getIntent().getStringExtra("carModel");
        String price = getIntent().getStringExtra("carPrice");
        String year = getIntent().getStringExtra("carYear");
        String km = getIntent().getStringExtra("carKm");
        String fuel = getIntent().getStringExtra("carFuel");
        String owner = getIntent().getStringExtra("carOwner");
        String location = getIntent().getStringExtra("carLocation");

        // Set Data
        tvCarName.setText(name);
        tvCarModel.setText(model);
        tvCarPrice.setText(price);
        tvCarYear.setText(year);
        tvCarKm.setText(km);
        tvCarFuel.setText(fuel);
        tvCarOwner.setText(owner);
        tvCarLocation.setText(location);

        // Setup Image Slider
        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.bmw);
        images.add(R.drawable.audi);
        images.add(R.drawable.thor);

//         Pass context + images to adapter ✅
        ImageSliderAdapter adapter = new ImageSliderAdapter(this, images);
        viewPager.setAdapter(adapter);

        // Button Actions
        btnCallDealer.setOnClickListener(v -> {
            String phoneNumber = "6374392913";
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
            Toast.makeText(this, "📞 Calling Dealer...", Toast.LENGTH_SHORT).show();
        });

        btnTestDrive.setOnClickListener(v ->
                Toast.makeText(this, "✅ Test Drive Booked!", Toast.LENGTH_SHORT).show());
    }
}
