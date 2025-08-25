package com.example.myproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class All extends Fragment {

    // Car 1 views
    private ImageView ivCarImage;
    private TextView tvCarName, tvCarStatus, tvCarPrice;
    private Button btnMarkSold, btnEdit;

    // Car 2 views
    private ImageView ivCarImage2;
    private TextView tvCarName2, tvCarStatus2, tvCarPrice2;
    private Button btnMarkSold2, btnEdit2;

//    // Car 3 views
//    private ImageView ivCarImage3;
//    private TextView tvCarName3, tvCarStatus3, tvCarPrice3;
//    private Button btnMarkSold3, btnEdit3;
//
//    // Car 4 views
//    private ImageView ivCarImage4;
//    private TextView tvCarName4, tvCarStatus4, tvCarPrice4;
//    private Button btnMarkSold4, btnEdit4;

    // Car 5 views
    private ImageView ivCarImage5;
    private TextView tvCarName5, tvCarStatus5, tvCarPrice5;
    private Button btnMarkSold5, btnEdit5;

    // Floating Action Button
    private FloatingActionButton fabAddCar;

    public void AllCarsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the XML layout you created
        View view = inflater.inflate(R.layout.all, container, false);

        // Initialize all views
        initializeViews(view);

        // Set up click listeners for all buttons
        setupClickListeners();

        return view;
    }

    private void initializeViews(View view) {
        // Car 1
        ivCarImage = view.findViewById(R.id.ivCarImage);
        tvCarName = view.findViewById(R.id.tvCarName);
        tvCarStatus = view.findViewById(R.id.tvCarStatus);
        tvCarPrice = view.findViewById(R.id.tvCarPrice);
        btnMarkSold = view.findViewById(R.id.btnMarkSold);
        btnEdit = view.findViewById(R.id.btnEdit);

        // Car 2
        ivCarImage2 = view.findViewById(R.id.ivCarImage2);
        tvCarName2 = view.findViewById(R.id.tvCarName2);
        tvCarStatus2 = view.findViewById(R.id.tvCarStatus2);
        tvCarPrice2 = view.findViewById(R.id.tvCarPrice2);
        btnMarkSold2 = view.findViewById(R.id.btnMarkSold2);
        btnEdit2 = view.findViewById(R.id.btnEdit2);

        // Car 3
//        ivCarImage3 = view.findViewById(R.id.ivCarImage3);
//        tvCarName3 = view.findViewById(R.id.tvCarName3);
//        tvCarStatus3 = view.findViewById(R.id.tvCarStatus3);
//        tvCarPrice3 = view.findViewById(R.id.tvCarPrice3);
//        btnMarkSold3 = view.findViewById(R.id.btnMarkSold3);
//        btnEdit3 = view.findViewById(R.id.btnEdit3);
//
//        // Car 4
//        ivCarImage4 = view.findViewById(R.id.ivCarImage4);
//        tvCarName4 = view.findViewById(R.id.tvCarName4);
//        tvCarStatus4 = view.findViewById(R.id.tvCarStatus4);
//        tvCarPrice4 = view.findViewById(R.id.tvCarPrice4);
//        btnMarkSold4 = view.findViewById(R.id.btnMarkSold4);
//        btnEdit4 = view.findViewById(R.id.btnEdit4);

        // Car 5
        ivCarImage5 = view.findViewById(R.id.ivCarImage5);
        tvCarName5 = view.findViewById(R.id.tvCarName5);
        tvCarStatus5 = view.findViewById(R.id.tvCarStatus5);
        tvCarPrice5 = view.findViewById(R.id.tvCarPrice5);
        btnMarkSold5 = view.findViewById(R.id.btnMarkSold5);
        btnEdit5 = view.findViewById(R.id.btnEdit5);

        // Floating Action Button
        fabAddCar = view.findViewById(R.id.fabAddCar);
    }

    private void setupClickListeners() {
        // Car 1 buttons
        btnMarkSold.setOnClickListener(v -> {
            tvCarStatus.setText("SOLD");
            tvCarStatus.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            Toast.makeText(getContext(), "Toyota Camry marked as SOLD 🚗", Toast.LENGTH_SHORT).show();
        });

        btnEdit.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Edit Toyota Camry details clicked ✏️", Toast.LENGTH_SHORT).show();
        });

        // Car 2 buttons
        btnMarkSold2.setOnClickListener(v -> {
            tvCarStatus2.setText("SOLD");
            tvCarStatus2.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            Toast.makeText(getContext(), "Second Toyota Camry marked as SOLD 🚗", Toast.LENGTH_SHORT).show();
        });

        btnEdit2.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Edit second Toyota Camry details clicked ✏️", Toast.LENGTH_SHORT).show();
        });

        // Car 3 buttons
//        btnMarkSold3.setOnClickListener(v -> {
//            tvCarStatus3.setText("SOLD");
//            tvCarStatus3.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
//            Toast.makeText(getContext(), "Honda Accord marked as SOLD 🚗", Toast.LENGTH_SHORT).show();
//        });
//
//        btnEdit3.setOnClickListener(v -> {
//            Toast.makeText(getContext(), "Edit Honda Accord details clicked ✏️", Toast.LENGTH_SHORT).show();
//        });
//
//        // Car 4 buttons
//        btnMarkSold4.setOnClickListener(v -> {
//            tvCarStatus4.setText("SOLD");
//            tvCarStatus4.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
//            Toast.makeText(getContext(), "BMW X5 marked as SOLD 🚗", Toast.LENGTH_SHORT).show();
//        });
//
//        btnEdit4.setOnClickListener(v -> {
//            Toast.makeText(getContext(), "Edit BMW X5 details clicked ✏️", Toast.LENGTH_SHORT).show();
//        });

        // Car 5 buttons
        btnMarkSold5.setOnClickListener(v -> {
            tvCarStatus5.setText("SOLD");
            tvCarStatus5.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            Toast.makeText(getContext(), "Mercedes-Benz C-Class marked as SOLD 🚗", Toast.LENGTH_SHORT).show();
        });

        btnEdit5.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Edit Mercedes-Benz C-Class details clicked ✏️", Toast.LENGTH_SHORT).show();
        });

        // Floating Action Button click listener
        fabAddCar.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Sales", Toast.LENGTH_SHORT).show();
            // Here you can navigate to Add Car Activity/Fragment
            // For example: startActivity(new Intent(getContext(), AddCarActivity.class));
        });
    }
}