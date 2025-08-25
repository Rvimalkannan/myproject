package com.example.myproject;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Initialize views
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderSub = findViewById(R.id.tvHeaderSub);

        // Handle window insets properly for EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Only apply top and horizontal padding to main container
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);

            // Apply bottom padding to bottom navigation
            bottomNavigation.setPadding(0, 0, 0, systemBars.bottom);

            return insets;
        });

        if (savedInstanceState == null) {
            loadFragment(new Allcars()); // default fragment
            // Set default header
            updateHeader("Available Cars", "Browse dealer inventory");
        }

        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            String title = "";
            String subtitle = "";

            int id = item.getItemId();

            if (id == R.id.nav_browse) {
                selectedFragment = new Allcars();
                title = "Available Cars";
                subtitle = "Browse dealer inventory";
            } else if (id == R.id.nav_my_cars) {
                selectedFragment = new Mycars();
                title = "My Cars";
                subtitle = "Your saved vehicles";
            } else if (id == R.id.nav_messages) {
                selectedFragment = new Message();
                title = "You see the Details";
                subtitle = "Chat with dealers";
            } else if (id == R.id.nav_profile) {
                selectedFragment = new Profile();
                title = "About You";
                subtitle = "Manage your account";
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                updateHeader(title, subtitle);
            }

            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void updateHeader(String title, String subtitle) {
        // Fade out
        tvHeaderTitle.animate().alpha(0f).setDuration(150).withEndAction(() -> {
            tvHeaderTitle.setText(title);
            // Fade in
            tvHeaderTitle.animate().alpha(1f).setDuration(150);
        });

        tvHeaderSub.animate().alpha(0f).setDuration(150).withEndAction(() -> {
            tvHeaderSub.setText(subtitle);
            tvHeaderSub.animate().alpha(1f).setDuration(150);
        });
    }
}