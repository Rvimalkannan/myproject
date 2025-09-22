package com.example.myproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.MaterialToolbar;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class cardetails extends AppCompatActivity {

    ViewPager2 viewPager;
    TextView tvCarNameCard, tvCarModelCard, tvCarPrice, tvCarYear, tvCarKm, tvCarFuel, tvCarOwner, tvCarLocation;
    Button btnCallDealer, btnSendRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardetails);

        // Setup Toolbar as ActionBar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

// Enable back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

// Handle back button click
        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        // Bind Views
        viewPager = findViewById(R.id.viewPager2);
        tvCarNameCard = findViewById(R.id.tvCarNameCard);
        tvCarModelCard = findViewById(R.id.tvCarModelCard);
        tvCarPrice = findViewById(R.id.tvCarPriceDetails);
        tvCarYear = findViewById(R.id.tvCarYear);
        tvCarKm = findViewById(R.id.tvCarKm);
        tvCarFuel = findViewById(R.id.tvCarFuel);
        tvCarOwner = findViewById(R.id.tvCarOwner);
        tvCarLocation = findViewById(R.id.tvCarLocation);
        btnCallDealer = findViewById(R.id.btnCallDealer);
        btnSendRequest = findViewById(R.id.btnSendRequest);

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
        tvCarNameCard.setText(name);
        tvCarModelCard.setText(model);
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

        ImageSliderAdapter adapter = new ImageSliderAdapter(this, images);
        viewPager.setAdapter(adapter);

        // 📞 Call Dealer
        btnCallDealer.setOnClickListener(v -> {
            String phoneNumber = "6374392913";
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
            Toast.makeText(this, "📞 Calling Dealer...", Toast.LENGTH_SHORT).show();
        });

        // 📩 Send Request via WhatsApp (Text + Image)
        btnSendRequest.setOnClickListener(v -> {
            String message = "Hi! I am interested in the following car:\n\n"
                    + "Name: " + name + "\n"
                    + "Model: " + model + "\n"
                    + "Price: " + price + "\n"
                    + "Year: " + year + "\n"
                    + "KM Driven: " + km + "\n"
                    + "Fuel: " + fuel + "\n"
                    + "Owner: " + owner + "\n"
                    + "Location: " + location;

            try {
                // Convert drawable to File
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bmw); // first image
                File cachePath = new File(getCacheDir(), "images");
                cachePath.mkdirs();
                File file = new File(cachePath, "car.jpg");
                FileOutputStream stream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.close();

                // Get content URI with FileProvider
                Uri imageUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);

                // Create WhatsApp Intent
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, message);
                whatsappIntent.setType("image/*");
                whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                whatsappIntent.setPackage("com.whatsapp");

                startActivity(whatsappIntent);

            } catch (Exception e) {
                Toast.makeText(this, "WhatsApp not installed or error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
