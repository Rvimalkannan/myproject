package com.example.myproject;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home extends AppCompatActivity {

    private TextView tvHeaderTitle;
    private TextView tvHeaderSub;
    private View header;
    private LinearLayout allCars;
    private BottomNavigationView bottomNavigation;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Initialize views
        bottomNavigation = findViewById(R.id.bottomNavigation);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderSub = findViewById(R.id.tvHeaderSub);
        //header = findViewById(R.id.header_bg);
        allCars = findViewById(R.id.header_container);

        // Handle window insets properly for EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Only apply top and horizontal padding to main container
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);

            // Apply bottom padding to bottom navigation to account for system navigation
            int bottomPadding = systemBars.bottom;
            bottomNavigation.setPadding(0, 0, 0, bottomPadding);

            return insets;
        });

        if (savedInstanceState == null) {
            loadFragment(new Allcars()); // default fragment
            updateHeader("Available Cars", "Browse dealer inventory");
            // Set default selected item
            bottomNavigation.setSelectedItemId(R.id.nav_browse);
        }

        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            String title = "";
            String subtitle = "";

            int id = item.getItemId();

            if (id == R.id.nav_browse) {
                // Hide all headers for browse
                hideAllHeaders();
                selectedFragment = new Allcars();
//                title = "Available Cars";
//                subtitle = "Browse dealer inventory";

            } else if (id == R.id.nav_my_cars) {
                // Show custom header for my cars
                //header.setVisibility(View.GONE);
                tvHeaderTitle.setVisibility(View.GONE);
                tvHeaderSub.setVisibility(View.GONE);
                allCars.setVisibility(View.GONE);
                hideDefaultHeaders();
                allCars.setVisibility(View.VISIBLE);
                //header.setVisibility(View.VISIBLE);
                selectedFragment = new Mycars();

            } else if (id == R.id.nav_messages) {
                hideAllHeaders();
                selectedFragment = new Message();

            } else if (id == R.id.nav_profile) {
                hideAllHeaders();
                selectedFragment = new Profile();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                if (!title.isEmpty()) {
                    updateHeader(title, subtitle);
                }
            }

            return true;
        });
    }

    private void hideAllHeaders() {
        //header.setVisibility(View.GONE);
        tvHeaderTitle.setVisibility(View.GONE);
        tvHeaderSub.setVisibility(View.GONE);
        allCars.setVisibility(View.GONE);
    }

    private void hideDefaultHeaders() {
        tvHeaderTitle.setVisibility(View.GONE);
        tvHeaderSub.setVisibility(View.GONE);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void updateHeader(String title, String subtitle) {
        if (tvHeaderTitle.getVisibility() == View.VISIBLE) {
            // Fade out and update
            tvHeaderTitle.animate().alpha(0f).setDuration(150).withEndAction(() -> {
                tvHeaderTitle.setText(title);
                tvHeaderTitle.animate().alpha(1f).setDuration(150);
            });

            tvHeaderSub.animate().alpha(0f).setDuration(150).withEndAction(() -> {
                tvHeaderSub.setText(subtitle);
                tvHeaderSub.animate().alpha(1f).setDuration(150);
            });
        } else {
            // Just set the text directly
            tvHeaderTitle.setText(title);
            tvHeaderSub.setText(subtitle);
        }
    }
}