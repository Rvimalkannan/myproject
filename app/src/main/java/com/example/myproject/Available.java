package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Available extends Fragment {

    // First Car Views
    private ImageView ivCarImage4;
    private TextView tvCarName4, tvCarStatus4, tvCarPrice4;
    private MaterialButton btnMarkSold4, btnEdit4;

    // Second Car Views
    private ImageView ivCarImage3;
    private TextView tvCarName3, tvCarStatus3, tvCarPrice3;
    private MaterialButton btnMarkSold3, btnEdit3;

    // Floating Action Button
    private FloatingActionButton fabAddCar;

    // Required empty public constructor
    public Available() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.available, container, false);

        // Initialize views
        initializeViews(view);

        // Set up click listeners
        setupClickListeners();

        // Load initial data
        loadInitialData();

        return view;
    }

    private void initializeViews(View view) {
        // First Car Views
        ivCarImage4 = view.findViewById(R.id.ivCarImage4);
        tvCarName4 = view.findViewById(R.id.tvCarName4);
        tvCarStatus4 = view.findViewById(R.id.tvCarStatus4);
        tvCarPrice4 = view.findViewById(R.id.tvCarPrice4);
        btnMarkSold4 = view.findViewById(R.id.btnMarkSold4);
        btnEdit4 = view.findViewById(R.id.btnEdit4);

        // Second Car Views
        ivCarImage3 = view.findViewById(R.id.ivCarImage3);
        tvCarName3 = view.findViewById(R.id.tvCarName3);
        tvCarStatus3 = view.findViewById(R.id.tvCarStatus3);
        tvCarPrice3 = view.findViewById(R.id.tvCarPrice3);
        btnMarkSold3 = view.findViewById(R.id.btnMarkSold3);
        btnEdit3 = view.findViewById(R.id.btnEdit3);

        // Floating Action Button
        fabAddCar = view.findViewById(R.id.fabAddCar);
    }

    private void setupClickListeners() {
        // First Car - Mark Sold Button
        btnMarkSold4.setOnClickListener(v -> markCarAsSold(4, tvCarStatus4, "benz C class 2021"));

        // First Car - Edit Button
        btnEdit4.setOnClickListener(v -> editCarDetails("benz C class 2021", 4));

        // Second Car - Mark Sold Button
        btnMarkSold3.setOnClickListener(v -> markCarAsSold(3, tvCarStatus3, "bmw X5 2021"));

        // Second Car - Edit Button
        btnEdit3.setOnClickListener(v -> editCarDetails("bmw X5 2021", 3));

        // Floating Action Button
        fabAddCar.setOnClickListener(v -> addNewCar());
    }

    private void loadInitialData() {
        // Set initial data for first car
        tvCarName4.setText("benz C class 2021");
        tvCarStatus4.setText("AVAILABLE");
        tvCarPrice4.setText("₹55,00,000");

        // Set initial data for second car
        tvCarName3.setText("bmw X5 2021");
        tvCarStatus3.setText("AVAILABLE");
        tvCarPrice3.setText("₹45,00,000");
    }

    private void markCarAsSold(int carId, TextView statusView, String carName) {
        // Update the status visually
        statusView.setText("SOLD");
        statusView.setBackgroundResource(R.drawable.bg_btn_sold);

        // Show confirmation message
        Toast.makeText(getContext(), carName + " marked as SOLD!", Toast.LENGTH_SHORT).show();

        // Navigate to Sold fragment after a short delay
//        statusView.postDelayed(() -> navigateToSoldFragment(carName, carId), 1000);

        // Here you would typically also update your backend/database
        // updateCarStatusInDatabase(carId, "SOLD");
    }

//    private void navigateToSoldFragment(String carName, int carId) {
//        try {
//            // Create new instance of Sold fragment
//            Sold soldFragment = new Sold();
//
//            // Pass data to sold fragment using Bundle
//            Bundle bundle = new Bundle();
//            bundle.putString("car_name", carName);
//            bundle.putInt("car_id", carId);
//            soldFragment.setArguments(bundle);
//
//            // Navigate to sold fragment
//            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//            transaction.replace(R.id.fragment_container, soldFragment); // Replace with your container ID
//            transaction.addToBackStack(null);
//            transaction.commit();
//
//        } catch (Exception e) {
//            Toast.makeText(getContext(), "Error navigating to sold page", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void editCarDetails(String carName, int carId) {
        Toast.makeText(getContext(), "Edit details for " + carName, Toast.LENGTH_SHORT).show();

        // Here you would typically navigate to an edit fragment or activity
        // navigateToEditFragment(carName, carId);
    }

    private void addNewCar() {
        navigateToStepper1(); // Now it opens your stepper1 Activity
    }

    private void navigateToStepper1() {
        try {
            // Navigate to stepper1 Activity (since stepper1 is an Activity, not a Fragment)
            Intent intent = new Intent(getActivity(), stepper1.class);
            startActivity(intent);

            Toast.makeText(getContext(), "Opening Add Car Form", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(getContext(), "Error opening add car form", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to update car status in database (placeholder)
    private void updateCarStatusInDatabase(int carId, String status) {
        // Implement your API call here to update the car status in backend
        // Example:
        // loginapi.updateCarStatus(carId, status, (success, response) -> {
        //     if (success) {
        //         // Handle success
        //     } else {
        //         // Handle error
        //     }
        // });
    }

    // Method to handle back press or refresh data if needed
    @Override
    public void onResume() {
        super.onResume();
        // Refresh data when fragment becomes visible
        // loadAvailableCarsFromAPI();
    }
}
