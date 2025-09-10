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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class All extends Fragment {

    // UI Components
    private EditText etSearch;
    private LinearLayout allCarsContainer;
    private FloatingActionButton fabAddCar;
    private View rootView;

    // Car data lists
    private List<Car> carList;
    private List<View> carCardViews;

    // Car model class
    public static class Car {
        public String name;
        public String price;
        public String imageResource;
        public String mileage;
        public String fuelType;
        public String transmission;
        public String postedDate;
        public String status;
        public int cardId;

        public Car(String name, String price, String imageResource, String mileage,
                   String fuelType, String transmission, String postedDate, String status, int cardId) {
            this.name = name;
            this.price = price;
            this.imageResource = imageResource;
            this.mileage = mileage;
            this.fuelType = fuelType;
            this.transmission = transmission;
            this.postedDate = postedDate;
            this.status = status;
            this.cardId = cardId;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.all, container, false);

        initializeViews();
        initializeCarData();
        setupSearchFunctionality();
        setupButtonListeners();

        return rootView;
    }

    private void initializeViews() {
        etSearch = rootView.findViewById(R.id.et_search);
        allCarsContainer = rootView.findViewById(R.id.allcars);
        fabAddCar = rootView.findViewById(R.id.fabAddCar);

        carCardViews = new ArrayList<>();
    }

    private void initializeCarData() {
        carList = new ArrayList<>();

        // Add car data based on your XML
        carList.add(new Car("2020 Toyota Camry", "₹14,00,000", "unova", "21,000 km",
                "Petrol", "Automatic", "5 days ago", "AVAILABLE", 0));

        carList.add(new Car("2020 Thor 4x4", "₹32,00,000", "thor", "10,000 km",
                "Petrol", "Automatic", "6 days ago", "AVAILABLE", 1));

        carList.add(new Car("Honda Civic 2021", "₹45,00,000", "honda", "30,000 km",
                "Petrol", "Automatic", "6 days ago", "AVAILABLE", 2));

        carList.add(new Car("Honda Civic 2021", "₹45,00,000", "bmw", "25,000 km",
                "Petrol", "Automatic", "4 days ago", "Sold", 3));

        carList.add(new Car("Honda Civic 2021", "₹45,00,000", "benz", "30,000 km",
                "Diesel", "Automatic", "7 days ago", "Sold", 4));

        carList.add(new Car("Mahindra", "₹45,00,000", "mahandhara", "30,000 km",
                "Diesel", "Automatic", "6 days ago", "Available", 5));
    }

    private void setupSearchFunctionality() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCars(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterCars(String searchText) {
        for (int i = 0; i < allCarsContainer.getChildCount(); i++) {
            View child = allCarsContainer.getChildAt(i);

            // Skip the search EditText
            if (child instanceof EditText) {
                continue;
            }

            if (i - 1 < carList.size()) { // -1 because EditText is first child
                Car car = carList.get(i - 1);
                boolean shouldShow = car.name.toLowerCase().contains(searchText.toLowerCase()) ||
                        car.fuelType.toLowerCase().contains(searchText.toLowerCase()) ||
                        car.transmission.toLowerCase().contains(searchText.toLowerCase()) ||
                        car.status.toLowerCase().contains(searchText.toLowerCase());

                child.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
            }
        }
    }

    private void setupButtonListeners() {
        // Setup FAB
        fabAddCar.setOnClickListener(v -> {
            // Intent to add new car activity
            Intent intent = new Intent(getActivity(), stepper1.class);
            startActivity(intent);
        });

        // Setup individual car buttons
        setupCarButtons();
    }

    private void setupCarButtons() {
        // Car 1 buttons
        setupCarButtonPair(R.id.btnMarkSold, R.id.btnEdit, 0);

        // Car 2 buttons
        setupCarButtonPair(R.id.btnMarkSold1, R.id.btnEdit1, 1);

        // Car 3 buttons
        setupCarButtonPair(R.id.btnMarkSold2, R.id.btnEdit2, 2);

        // Car 4 buttons
        setupCarButtonPair(R.id.btnMarkSold3, R.id.btnEdit3, 3);

        // Car 5 buttons
        setupCarButtonPair(R.id.btnMarkSold4, R.id.btnEdit4, 4);

        // Car 6 buttons
        setupCarButtonPair(R.id.btnMarkSold5, R.id.btnEdit5, 5);
    }

    private void setupCarButtonPair(int markSoldId, int editId, int carIndex) {
        Button btnMarkSold = rootView.findViewById(markSoldId);
        Button btnEdit = rootView.findViewById(editId);

        if (btnMarkSold != null) {
            btnMarkSold.setOnClickListener(v -> markCarAsSold(carIndex));
        }

//        if (btnEdit != null) {
//            btnEdit.setOnClickListener(v -> editCarDetails(carIndex));
//        }
    }

    private void markCarAsSold(int carIndex) {
        if (carIndex < carList.size()) {
            Car car = carList.get(carIndex);

            if ("Sold".equalsIgnoreCase(car.status)) {
                Toast.makeText(getContext(), "Car is already sold", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update car status
            car.status = "Sold";

            // Update UI
            updateCarStatusInUI(carIndex, "Sold");

//            Toast.makeText(getContext(), car.name + " marked as sold", Toast.LENGTH_SHORT).show();

            // Here you would typically update your database
            // updateCarStatusInDatabase(car.cardId, "Sold");
        }
    }

//    private void editCarDetails(int carIndex) {
//        if (carIndex < carList.size()) {
//            Car car = carList.get(carIndex);
//
//            // Create intent to edit car activity
//            Intent intent = new Intent(getActivity(),editCarDetails.class);
//            intent.putExtra("car_id", car.cardId);
//            intent.putExtra("car_name", car.name);
//            intent.putExtra("car_price", car.price);
//            intent.putExtra("car_mileage", car.mileage);
//            intent.putExtra("car_fuel", car.fuelType);
//            intent.putExtra("car_transmission", car.transmission);
//            intent.putExtra("car_status", car.status);
//
//            startActivityForResult(intent, 100 + carIndex);
//        }
//    }

    private void updateCarStatusInUI(int carIndex, String newStatus) {
        // Get the corresponding status TextView IDs
        int[] statusTextViewIds = {
                R.id.tvCarStatus, R.id.tvCarStatus1, R.id.tvCarStatus2,
                R.id.tvCarStatus3, R.id.tvCarStatus4, R.id.tvCarStatus5
        };

        if (carIndex < statusTextViewIds.length) {
            TextView statusTextView = rootView.findViewById(statusTextViewIds[carIndex]);
            if (statusTextView != null) {
                statusTextView.setText(newStatus);

                // Update background based on status
                if ("Sold".equalsIgnoreCase(newStatus)) {
                    statusTextView.setBackgroundResource(R.drawable.bg_btn_sold);
                } else {
                    statusTextView.setBackgroundResource(R.drawable.bg_status_available);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK && data != null) {
            // Handle returned data from edit activity
            int carIndex = requestCode - 100;

            if (carIndex >= 0 && carIndex < carList.size()) {
                Car car = carList.get(carIndex);

                // Update car data
                car.name = data.getStringExtra("updated_name");
                car.price = data.getStringExtra("updated_price");
                car.mileage = data.getStringExtra("updated_mileage");
                car.fuelType = data.getStringExtra("updated_fuel");
                car.transmission = data.getStringExtra("updated_transmission");
                car.status = data.getStringExtra("updated_status");

                // Update UI
                updateCarDetailsInUI(carIndex, car);

                Toast.makeText(getContext(), "Car details updated", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateCarDetailsInUI(int carIndex, Car car) {
        // Arrays of TextView IDs for each car detail
        int[] nameTextViewIds = {R.id.tvCarName, R.id.tvCarName1, R.id.tvCarName2,
                R.id.tvCarName3, R.id.tvCarName4, R.id.tvCarName5};
        int[] priceTextViewIds = {R.id.tvCarPrice, R.id.tvCarPrice1, R.id.tvCarPrice2,
                R.id.tvCarPrice3, R.id.tvCarPrice4, R.id.tvCarPrice5};

        if (carIndex < nameTextViewIds.length) {
            TextView nameTextView = rootView.findViewById(nameTextViewIds[carIndex]);
            TextView priceTextView = rootView.findViewById(priceTextViewIds[carIndex]);

            if (nameTextView != null) nameTextView.setText(car.name);
            if (priceTextView != null) priceTextView.setText(car.price);

            // Update status
            updateCarStatusInUI(carIndex, car.status);
        }
    }

    // Method to refresh data (call this from parent activity if needed)
    public void refreshCarData() {
        // This method can be used to refresh car data from database
        // and update the UI accordingly

        // Example:
//         carList = DatabaseHelper.getAllCars();
//         updateAllCarCardsInUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Uncomment if you want to refresh data when fragment resumes
        // refreshCarData();
    }

    // Interface for communication with host activity
    public interface CarListingFragmentListener {
        void onCarSelected(Car car);
        void onAddCarRequested();
        void onCarStatusChanged(Car car, String newStatus);
    }

    private CarListingFragmentListener listener;

    public void setCarListingFragmentListener(CarListingFragmentListener listener) {
        this.listener = listener;
    }

    // Method to update a specific car from external source
    public void updateCar(int carIndex, Car updatedCar) {
        if (carIndex >= 0 && carIndex < carList.size()) {
            carList.set(carIndex, updatedCar);
            updateCarDetailsInUI(carIndex, updatedCar);
        }
    }

    // Method to add a new car to the list
    public void addCar(Car newCar) {
        carList.add(newCar);
        // You would need to dynamically add the card view here
        // or refresh the entire fragment
        refreshCarData();
    }

    // Method to remove a car from the list
    public void removeCar(int carIndex) {
        if (carIndex >= 0 && carIndex < carList.size()) {
            carList.remove(carIndex);
            // Hide the corresponding card view
            refreshCarData();
        }
    }
}