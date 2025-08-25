package com.example.myproject;

import android.annotation.SuppressLint;
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

public class Sold extends Fragment {

    private ImageView ivCarImage;
    private TextView tvCarName, tvCarStatus, tvCarPrice;
    private Button btnMarkSold, btnEdit;

    // Required empty public constructor
    public Sold() { }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sold, container, false);

        // Initialize views
        ivCarImage = view.findViewById(R.id.ivCarImage);
        tvCarName = view.findViewById(R.id.tvCarName);
        tvCarStatus = view.findViewById(R.id.tvCarStatus);
        tvCarPrice = view.findViewById(R.id.tvCarPrice);
        btnMarkSold = view.findViewById(R.id.btnMarkSold);
        btnEdit = view.findViewById(R.id.btnEdit);

        // Example: set initial data
        tvCarName.setText("2020 bMW X5");
        tvCarStatus.setText("SOLD");

        tvCarStatus.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
        tvCarPrice.setText("Rs.34,00,000");
//
//         Button: Mark as Sold
//        btnMarkSold.setOnClickListener(v -> {
//            tvCarStatus.setText("SOLD");
//            tvCarStatus.setBackgroundColor(
//                    getResources().getColor(android.R.color.holo_red_dark)
//            );
//            Toast.makeText(getContext(), "Car marked as SOLD 🚗", Toast.LENGTH_SHORT).show();
//        });

        // Button: Edit
        btnEdit.setOnClickListener(v ->
                Toast.makeText(getContext(), "Edit car details clicked ✏️", Toast.LENGTH_SHORT).show()
        );

        return view;
    }
}
