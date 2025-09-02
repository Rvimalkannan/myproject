package com.example.myproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class cardetails extends AppCompatActivity {

    private final String dealerPhone = "6374392913";
    private final String dealerEmail = "Vimal@gmail.com";
    private TextView tvCarTitle, tvCarPrice, tvCarDetails;
    private ViewPager2 viewPager;
    private LinearLayout dotsLayout;
    private ImageSliderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardetails);

        initializeViews();
        handleIntentData();
        setupClickListeners();
    }

    private void initializeViews() {
        try {
            Button btnSendRequest = findViewById(R.id.btnSendRequest);
            Button btnCallNow = findViewById(R.id.btnCallNow);
            viewPager = findViewById(R.id.viewPager);
            dotsLayout = findViewById(R.id.dotsIndicator);
            tvCarTitle = findViewById(R.id.tvCarTitle);
            tvCarPrice = findViewById(R.id.tvCarPrice);
            tvCarDetails = findViewById(R.id.tvCarDetails);

            if (viewPager != null) {
                viewPager.setOffscreenPageLimit(1);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing views", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void handleIntentData() {
        try {
            Intent intent = getIntent();
            if (intent != null) {
                ArrayList<Integer> imageList = intent.getIntegerArrayListExtra("image_list");
                if (imageList != null && !imageList.isEmpty()) {
                    setupImageSlider(imageList);
                } else {
                    int carImage = intent.getIntExtra("car_image", 0);
                    if (carImage != 0) {
                        ArrayList<Integer> singleImageList = new ArrayList<>();
                        singleImageList.add(carImage);
                        setupImageSlider(singleImageList);
                    }
                }

                setTextViewData(intent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error loading car details", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void setupImageSlider(ArrayList<Integer> images) {
        if (viewPager != null && images != null && !images.isEmpty()) {
            adapter = new ImageSliderAdapter(images);
            viewPager.setAdapter(adapter);
            setupDotIndicators(images.size());

            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    updateDotIndicator(position);
                }
            });
        }
    }

    private void setTextViewData(Intent intent) {
        if (intent != null) {
            String carTitle = intent.getStringExtra("car_title");
            String carPrice = intent.getStringExtra("car_price");
            String carDetails = intent.getStringExtra("car_details");

            if (tvCarTitle != null && carTitle != null) {
                tvCarTitle.setText(carTitle);
            }
            if (tvCarPrice != null && carPrice != null) {
                tvCarPrice.setText(carPrice);
            }
            if (tvCarDetails != null && carDetails != null) {
                tvCarDetails.setText(carDetails);
            }
        }
    }

    private void setupClickListeners() {
        Button btnSendRequest = findViewById(R.id.btnSendRequest);
        Button btnCallNow = findViewById(R.id.btnCallNow);

        if (btnSendRequest != null) {
            btnSendRequest.setOnClickListener(v -> sendRequestEmail());
        }

        if (btnCallNow != null) {
            btnCallNow.setOnClickListener(v -> makePhoneCall());
        }
    }

    private void setupDotIndicators(int count) {
        try {
            if (dotsLayout == null) return;

            ImageView[] dots = new ImageView[count];
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);

            dotsLayout.removeAllViews();

            for (int i = 0; i < dots.length; i++) {
                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,
                        R.drawable.inactive_dot));
                dots[i].setLayoutParams(params);
                dotsLayout.addView(dots[i]);
            }

            if (dots.length > 0) {
                dots[0].setImageDrawable(ContextCompat.getDrawable(this,
                        R.drawable.active_dot));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDotIndicator(int position) {
        try {
            if (dotsLayout == null) return;

            int childCount = dotsLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                ImageView dot = (ImageView) dotsLayout.getChildAt(i);
                dot.setImageDrawable(ContextCompat.getDrawable(this,
                        i == position ? R.drawable.active_dot : R.drawable.inactive_dot));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendRequestEmail() {
        try {
            if (tvCarTitle == null) return;

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:" + dealerEmail));

            String carTitle = tvCarTitle.getText().toString();
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Car Purchase Inquiry - " + carTitle);
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, I am interested in the " + carTitle + ". Please provide me more details.");

            startActivity(Intent.createChooser(emailIntent, "Send Email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No email client installed.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error sending email", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void makePhoneCall() {
        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + dealerPhone));
            startActivity(callIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Error making phone call", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
