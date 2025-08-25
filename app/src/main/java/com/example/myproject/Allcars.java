package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Allcars extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_allcars, container, false);

        // First car button
        Button btnDetails1 = view.findViewById(R.id.btnViewDetails);
        btnDetails1.setOnClickListener(v -> {
            TextView tvTitle = view.findViewById(R.id.tvCarTitle);
            TextView tvPrice = view.findViewById(R.id.tvCarPrice);
            TextView tvDetails = view.findViewById(R.id.tvCarDetails);

            Intent intent = new Intent(getActivity(), cardetails.class);
            intent.putExtra("car_image", R.drawable.carimg);
            intent.putExtra("car_title", tvTitle.getText().toString());
            intent.putExtra("car_price", tvPrice.getText().toString());
            intent.putExtra("car_details", tvDetails.getText().toString());
            startActivity(intent);
        });

        // Second car button
        Button btnDetails2 = view.findViewById(R.id.btnViewDetails2);
        btnDetails2.setOnClickListener(v -> {
            TextView tvTitle = view.findViewById(R.id.tvCarTitle1);
            TextView tvPrice = view.findViewById(R.id.tvCarPrice1);
            TextView tvDetails = view.findViewById(R.id.tvCarDetails1);

            Intent intent = new Intent(getActivity(), cardetails.class);
            intent.putExtra("car_image", R.drawable.benz);
            intent.putExtra("car_title", tvTitle.getText().toString());
            intent.putExtra("car_price", tvPrice.getText().toString());
            intent.putExtra("car_details", tvDetails.getText().toString());
            startActivity(intent);
        });

        // Third car button
        Button btnDetails3 = view.findViewById(R.id.btnViewDetails3);
        btnDetails3.setOnClickListener(v -> {
            TextView tvTitle = view.findViewById(R.id.tvCarTitle2);
            TextView tvPrice = view.findViewById(R.id.tvCarPrice2);
            TextView tvDetails = view.findViewById(R.id.tvCarDetails2);

            Intent intent = new Intent(getActivity(), cardetails.class);
            intent.putExtra("car_image", R.drawable.bmw);
            intent.putExtra("car_title", tvTitle.getText().toString());
            intent.putExtra("car_price", tvPrice.getText().toString());
            intent.putExtra("car_details", tvDetails.getText().toString());
            startActivity(intent);
        });


        Button btnDetails4 = view.findViewById(R.id.btnViewDetails4);
        btnDetails4.setOnClickListener(v -> {
            TextView tvTitle = view.findViewById(R.id.tvCarTitle3);
            TextView tvPrice = view.findViewById(R.id.tvCarPrice3);
            TextView tvDetails = view.findViewById(R.id.tvCarDetails3);

            Intent intent = new Intent(getActivity(), cardetails.class);
            intent.putExtra("car_image", R.drawable.honda);
            intent.putExtra("car_title", tvTitle.getText().toString());
            intent.putExtra("car_price", tvPrice.getText().toString());
            intent.putExtra("car_details", tvDetails.getText().toString());
            startActivity(intent);
        });

        // Fifth car button (Suzuki)
        Button btnDetails5 = view.findViewById(R.id.btnViewDetails5);
        btnDetails5.setOnClickListener(v -> {
            TextView tvTitle = view.findViewById(R.id.tvCarTitle5);
            TextView tvPrice = view.findViewById(R.id.tvCarPrice5);
            TextView tvDetails = view.findViewById(R.id.tvCarDetails5);

            Intent intent = new Intent(getActivity(), cardetails.class);
            intent.putExtra("car_image", R.drawable.suzuki);
            intent.putExtra("car_title", tvTitle.getText().toString());
            intent.putExtra("car_price", tvPrice.getText().toString());
            intent.putExtra("car_details", tvDetails.getText().toString());
            startActivity(intent);
        });

        // Sixth car button (Unova)
        Button btnDetails6 = view.findViewById(R.id.btnViewDetails6);
        btnDetails6.setOnClickListener(v -> {
            TextView tvTitle = view.findViewById(R.id.tvCarTitle6);
            TextView tvPrice = view.findViewById(R.id.tvCarPrice6);
            TextView tvDetails = view.findViewById(R.id.tvCarDetails6);

            Intent intent = new Intent(getActivity(), cardetails.class);
            intent.putExtra("car_image", R.drawable.unova);
            intent.putExtra("car_title", tvTitle.getText().toString());
            intent.putExtra("car_price", tvPrice.getText().toString());
            intent.putExtra("car_details", tvDetails.getText().toString());
            startActivity(intent);
        });

        // Seventh car button (Mini)
        Button btnDetails7 = view.findViewById(R.id.btnViewDetails7);
        btnDetails7.setOnClickListener(v -> {
            TextView tvTitle = view.findViewById(R.id.tvCarTitle7);
            TextView tvPrice = view.findViewById(R.id.tvCarPrice7);
            TextView tvDetails = view.findViewById(R.id.tvCarDetails7);

            Intent intent = new Intent(getActivity(), cardetails.class);
            intent.putExtra("car_image", R.drawable.mini);
            intent.putExtra("car_title", tvTitle.getText().toString());
            intent.putExtra("car_price", tvPrice.getText().toString());
            intent.putExtra("car_details", tvDetails.getText().toString());
            startActivity(intent);
        });

        // Eighth car button (Volkswagen)
        Button btnDetails8 = view.findViewById(R.id.btnViewDetails8);
        btnDetails8.setOnClickListener(v -> {
            TextView tvTitle = view.findViewById(R.id.tvCarTitle8);
            TextView tvPrice = view.findViewById(R.id.tvCarPrice8);
            TextView tvDetails = view.findViewById(R.id.tvCarDetails8);

            Intent intent = new Intent(getActivity(), cardetails.class);
            intent.putExtra("car_image", R.drawable.vv);
            intent.putExtra("car_title", tvTitle.getText().toString());
            intent.putExtra("car_price", tvPrice.getText().toString());
            intent.putExtra("car_details", tvDetails.getText().toString());
            startActivity(intent);
        });

        // Ninth car button (TATA)
        Button btnDetails9 = view.findViewById(R.id.btnViewDetails9);
        btnDetails9.setOnClickListener(v -> {
            TextView tvTitle = view.findViewById(R.id.tvCarTitle9);
            TextView tvPrice = view.findViewById(R.id.tvCarPrice9);
            TextView tvDetails = view.findViewById(R.id.tvCarDetails9);

            Intent intent = new Intent(getActivity(), cardetails.class);
            intent.putExtra("car_image", R.drawable.tata);
            intent.putExtra("car_title", tvTitle.getText().toString());
            intent.putExtra("car_price", tvPrice.getText().toString());
            intent.putExtra("car_details", tvDetails.getText().toString());
            startActivity(intent);
        });

        // Tenth car button (Thor)
        Button btnDetails10 = view.findViewById(R.id.btnViewDetails10);
        btnDetails10.setOnClickListener(v -> {
            TextView tvTitle = view.findViewById(R.id.tvCarTitle4);
            TextView tvPrice = view.findViewById(R.id.tvCarPrice4);
            TextView tvDetails = view.findViewById(R.id.tvCarDetails4);

            Intent intent = new Intent(getActivity(), cardetails.class);
            intent.putExtra("car_image", R.drawable.thor);
            intent.putExtra("car_title", tvTitle.getText().toString());
            intent.putExtra("car_price", tvPrice.getText().toString());
            intent.putExtra("car_details", tvDetails.getText().toString());
            startActivity(intent);
        });

        return view;
    }
}
