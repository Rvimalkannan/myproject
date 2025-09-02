package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class All extends Fragment {

    private EditText etSearch;
    private FloatingActionButton fabAddCar;

    // A model to store each car’s views
    private static class CarView {
        MaterialCardView card;
        TextView name, status, price;
        Button btnMarkSold, btnEdit;

        CarView(MaterialCardView card, TextView name, TextView status, TextView price,
                Button btnMarkSold, Button btnEdit) {
            this.card = card;
            this.name = name;
            this.status = status;
            this.price = price;
            this.btnMarkSold = btnMarkSold;
            this.btnEdit = btnEdit;
        }
    }

    // List of cars
    private final List<CarView> cars = new ArrayList<>();

    public All() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all, container, false);

        // Init Views
        etSearch = view.findViewById(R.id.et_search);
        fabAddCar = view.findViewById(R.id.fabAddCar);

        // Collect all cars into list
        setupCars(view);

        // Add search functionality
        setupSearch();

        // Add click listeners for cars + FAB
        setupClickListeners();

        return view;
    }

    private void setupCars(View view) {
        cars.add(new CarView(
                (MaterialCardView) ((ViewGroup) view.findViewById(R.id.allcars)).getChildAt(1),
                view.findViewById(R.id.tvCarName),
                view.findViewById(R.id.tvCarStatus),
                view.findViewById(R.id.tvCarPrice),
                view.findViewById(R.id.btnMarkSold),
                view.findViewById(R.id.btnEdit)
        ));

        cars.add(new CarView(
                (MaterialCardView) ((ViewGroup) view.findViewById(R.id.allcars)).getChildAt(2),
                view.findViewById(R.id.tvCarName2),
                view.findViewById(R.id.tvCarStatus2),
                view.findViewById(R.id.tvCarPrice2),
                view.findViewById(R.id.btnMarkSold2),
                view.findViewById(R.id.btnEdit2)
        ));

        cars.add(new CarView(
                (MaterialCardView) ((ViewGroup) view.findViewById(R.id.allcars)).getChildAt(3),
                view.findViewById(R.id.tvCarName3),
                view.findViewById(R.id.tvCarStatus3),
                view.findViewById(R.id.tvCarPrice3),
                view.findViewById(R.id.btnMarkSold3),
                view.findViewById(R.id.btnEdit3)
        ));

        cars.add(new CarView(
                (MaterialCardView) ((ViewGroup) view.findViewById(R.id.allcars)).getChildAt(4),
                view.findViewById(R.id.tvCarName4),
                view.findViewById(R.id.tvCarStatus4),
                view.findViewById(R.id.tvCarPrice4),
                view.findViewById(R.id.btnMarkSold4),
                view.findViewById(R.id.btnEdit4)
        ));

        cars.add(new CarView(
                (MaterialCardView) ((ViewGroup) view.findViewById(R.id.allcars)).getChildAt(5),
                view.findViewById(R.id.tvCarName5),
                view.findViewById(R.id.tvCarStatus5),
                view.findViewById(R.id.tvCarPrice5),
                view.findViewById(R.id.btnMarkSold5),
                view.findViewById(R.id.btnEdit5)
        ));
        cars.add(new CarView(
                (MaterialCardView) ((ViewGroup) view.findViewById(R.id.allcars)).getChildAt(5),
                view.findViewById(R.id.tvCarName6),
                view.findViewById(R.id.tvCarStatus6),
                view.findViewById(R.id.tvCarPrice6),
                view.findViewById(R.id.btnMarkSold6),
                view.findViewById(R.id.btnEdit6)
        ));
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase();
                for (CarView car : cars) {
                    String carName = car.name.getText().toString().toLowerCase();
                    String carPrice = car.price.getText().toString().toLowerCase();
                    if (carName.contains(query) || carPrice.contains(query)) {
                        car.card.setVisibility(View.VISIBLE);
                    } else {
                        car.card.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void setupClickListeners() {
        for (CarView car : cars) {

            if (car.name.getText().toString().equals("2020 Toyota Camry")) {
                car.btnMarkSold.setOnClickListener(v -> {
                    car.status.setText("AVAILABLE");
                    car.status.setBackgroundColor(getResources().getColor(android.R.color.background_dark));
                    Toast.makeText(getContext(), car.name.getText() + " marked as SOLD 🚗", Toast.LENGTH_SHORT).show();
                });

                car.btnEdit.setOnClickListener(v -> {
                    Toast.makeText(getContext(), "Edit " + car.name.getText() + " details clicked ✏️", Toast.LENGTH_SHORT).show();
                });

            } else if  (car.name.getText().toString().equals("2020 Thor 4X4"))  {
                car.btnMarkSold.setOnClickListener(v -> {
                    car.status.setText("Sold");
                    car.status.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    Toast.makeText(getContext(), car.name.getText() + " marked as SOLD 🚗", Toast.LENGTH_SHORT).show();
                });

                car.btnEdit.setOnClickListener(v -> {
                    Toast.makeText(getContext(), "Edit " + car.name.getText() + " details clicked ✏️", Toast.LENGTH_SHORT).show();
                });
            }


            else if  (car.name.getText().toString().equals("2024 Honda Accord"))  {
                car.btnMarkSold.setOnClickListener(v -> {
                    car.status.setText("AVAILABLE");
                    car.status.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    Toast.makeText(getContext(), car.name.getText() + " marked as SOLD 🚗", Toast.LENGTH_SHORT).show();
                });

                car.btnEdit.setOnClickListener(v -> {
                    Toast.makeText(getContext(), "Edit " + car.name.getText() + " details clicked ✏️", Toast.LENGTH_SHORT).show();
                });
            }

            else if  (car.name.getText().toString().equals("2025 BMW X5"))  {
                car.btnMarkSold.setOnClickListener(v -> {
                    car.status.setText("Sold");
                    car.status.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    Toast.makeText(getContext(), car.name.getText() + " marked as SOLD 🚗", Toast.LENGTH_SHORT).show();
                });

                car.btnEdit.setOnClickListener(v -> {
                    Toast.makeText(getContext(), "Edit " + car.name.getText() + " details clicked ✏️", Toast.LENGTH_SHORT).show();
                });
            }

            else if  (car.name.getText().toString().equals("2024 Mercedes-Benz C-Class"))  {
                car.btnMarkSold.setOnClickListener(v -> {
                    car.status.setText("AVAILABLE");
                    car.status.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    Toast.makeText(getContext(), car.name.getText() + " marked as SOLD 🚗", Toast.LENGTH_SHORT).show();
                });

                car.btnEdit.setOnClickListener(v -> {
                    Toast.makeText(getContext(), "Edit " + car.name.getText() + " details clicked ✏️", Toast.LENGTH_SHORT).show();
                });
            }
            else if  (car.name.getText().toString().equals("2024 Maruthi Suzuki"))  {
                car.btnMarkSold.setOnClickListener(v -> {
                    car.status.setText("AVAILABLE");
                    car.status.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    Toast.makeText(getContext(), car.name.getText() + " marked as SOLD 🚗", Toast.LENGTH_SHORT).show();
                });

                car.btnEdit.setOnClickListener(v -> {
                    Toast.makeText(getContext(), "Edit " + car.name.getText() + " details clicked ✏️", Toast.LENGTH_SHORT).show();
                });
            }
        }

        fabAddCar.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), stepper1.class);
            startActivity(intent);
        });
    }
}
