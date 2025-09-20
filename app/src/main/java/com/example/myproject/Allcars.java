package com.example.myproject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Allcars extends Fragment {

    EditText etSearch;
    RecyclerView recyclerCars;
    APiCarAdapter apiCarAdapter;
    List<ApiAllCar> carList;
    List<ApiAllCar> filteredList;

    ImageView btnFilter;
    DrawerLayout drawerLayout;

    // Show Variant Toggle Buttons
    ToggleButton btnSedan, btnSuv, btnHatchback, btnLuxury;

    // Fuel Type Toggle Buttons
    ToggleButton btnPetrol, btnDiesel, btnCng, btnElectric;

    // Transmission Toggle Buttons
    ToggleButton btnManual, btnAutomatic;

    // Number of Owners Toggle Buttons (Fixed to match XML)
    ToggleButton btnFirstOwner, btnSecondOwner, btnThirdOwner, btnFourthOwner;

    Button btnReset;
    Button btnApply;
    TextView tvCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_allcars, container, false);

        // Initialize main views
        etSearch = view.findViewById(R.id.etSearch);
        recyclerCars = view.findViewById(R.id.recyclerCars);
        recyclerCars.setLayoutManager(new LinearLayoutManager(getContext()));

        btnFilter = view.findViewById(R.id.btnFilter);
        drawerLayout = view.findViewById(R.id.drawerLayout);

        // Initialize drawer filter views
        initializeFilterViews(view);

        // Load cars from API
        loadCarsFromAPI();

        // Set up click listeners
        setupClickListeners();

        // Set up search functionality
        setupSearchFunctionality();

        return view;
    }

    private void initializeFilterViews(View view) {
        // Show Variant toggles
        btnSedan = view.findViewById(R.id.btnSedan);
        btnSuv = view.findViewById(R.id.btnSuv);
        btnHatchback = view.findViewById(R.id.btnHatchback);
        btnLuxury = view.findViewById(R.id.btnLuxury);

        // Fuel Type toggles
        btnPetrol = view.findViewById(R.id.btnPetrol);
        btnDiesel = view.findViewById(R.id.btnDiesel);
        btnCng = view.findViewById(R.id.btnCng);
        btnElectric = view.findViewById(R.id.btnElectric);

        // Transmission toggles
        btnManual = view.findViewById(R.id.btnManual);
        btnAutomatic = view.findViewById(R.id.btnAutomatic);

        // Number of Owners toggles (Now matching XML)
        btnFirstOwner = view.findViewById(R.id.btnFirstOwner);
        btnSecondOwner = view.findViewById(R.id.btnSecondOwner);
        btnThirdOwner = view.findViewById(R.id.btnThirdOwner);
        btnFourthOwner = view.findViewById(R.id.btnFourthOwner);

        // Action buttons
        btnReset = view.findViewById(R.id.btnReset);
        btnApply = view.findViewById(R.id.btnApply);
        tvCancel = view.findViewById(R.id.tvCancel);

        // Make Number of Owners buttons single-select
        setupOwnerButtons();
    }

    private void setupOwnerButtons() {
        final ToggleButton[] ownerButtons = {btnFirstOwner, btnSecondOwner, btnThirdOwner, btnFourthOwner};

        for (final ToggleButton button : ownerButtons) {
            button.setOnClickListener(v -> {
                // If the button is being turned on
                if (button.isChecked()) {
                    // Uncheck all other buttons
                    for (ToggleButton b : ownerButtons) {
                        if (b != button) {
                            b.setChecked(false);
                        }
                    }
                }
            });
        }
    }

    private void setupSearchFunctionality() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterBySearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterBySearch(String query) {
        if (carList == null || carList.isEmpty()) {
            return;
        }

        List<ApiAllCar> searchFilteredList = new ArrayList<>();

        if (query.isEmpty()) {
            searchFilteredList.addAll(carList);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
//            for (ApiAllCar car : carList) {
//                // Search in car name, brand, model, etc.
//                if ((car.getName() != null && car.getName().toLowerCase().contains(lowerCaseQuery)) ||
//                        (car.getBrand() != null && car.getBrand().toLowerCase().contains(lowerCaseQuery)) ||
//                        (car.getModel() != null && car.getModel().toLowerCase().contains(lowerCaseQuery)) ||
//                        (car.getVariant() != null && car.getVariant().toLowerCase().contains(lowerCaseQuery))) {
//                    searchFilteredList.add(car);
//                }
//            }
        }

        filteredList = searchFilteredList;
//        if (apiCarAdapter != null) {
//            apiCarAdapter.updateCarList(filteredList);
//            apiCarAdapter.notifyDataSetChanged();
//        }
    }

    private void loadCarsFromAPI() {
        loginapi.getAllCars((success, response) -> {
            if (success) {
                requireActivity().runOnUiThread(() -> {
                    try {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<ApiAllCar>>() {}.getType();
                        carList = gson.fromJson(response, listType);
                        filteredList = new ArrayList<>(carList);

                        apiCarAdapter = new APiCarAdapter(getContext(), filteredList);
                        recyclerCars.setAdapter(apiCarAdapter);

                        Log.d("CarAPI", "Loaded " + carList.size() + " cars successfully");

                    } catch (Exception e) {
                        Log.e("CarAPI", "Parsing error: " + e.getMessage());
                    }
                });
            } else {
                Log.d("CarAPI", "API failed: " + response);
            }
        });
    }

    private void setupClickListeners() {
        // Open/close drawer on filter button click
        btnFilter.setOnClickListener(v -> {
            if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.openDrawer(GravityCompat.END);
            } else {
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        // Cancel button - close drawer
        tvCancel.setOnClickListener(v -> drawerLayout.closeDrawer(GravityCompat.END));

        // Reset filters
        btnReset.setOnClickListener(v -> {
            resetAllFilters();
            applyFilters();
        });

        // Apply filters
        btnApply.setOnClickListener(v -> {
            applyFilters();
            drawerLayout.closeDrawer(GravityCompat.END);
        });
    }

    private void resetAllFilters() {
        // Reset Show Variant
        btnSedan.setChecked(false);
        btnSuv.setChecked(false);
        btnHatchback.setChecked(false);
        btnLuxury.setChecked(false);

        // Reset Fuel Type
        btnPetrol.setChecked(false);
        btnDiesel.setChecked(false);
        btnCng.setChecked(false);
        btnElectric.setChecked(false);

        // Reset Transmission
        btnManual.setChecked(false);
        btnAutomatic.setChecked(false);

        // Reset Number of Owners
        btnFirstOwner.setChecked(false);
        btnSecondOwner.setChecked(false);
        btnThirdOwner.setChecked(false);
        btnFourthOwner.setChecked(false);
    }

    private void applyFilters() {
        if (carList == null || carList.isEmpty()) {
            return;
        }

        filteredList.clear();

        // Get filter states
        boolean sedan = btnSedan.isChecked();
        boolean suv = btnSuv.isChecked();
        boolean hatchback = btnHatchback.isChecked();
        boolean luxury = btnLuxury.isChecked();

        boolean petrol = btnPetrol.isChecked();
        boolean diesel = btnDiesel.isChecked();
        boolean cng = btnCng.isChecked();
        boolean electric = btnElectric.isChecked();

        boolean manual = btnManual.isChecked();
        boolean automatic = btnAutomatic.isChecked();

        boolean firstOwner = btnFirstOwner.isChecked();
        boolean secondOwner = btnSecondOwner.isChecked();
        boolean thirdOwner = btnThirdOwner.isChecked();
        boolean fourthOwner = btnFourthOwner.isChecked();

        // Check if any filters are applied
        boolean hasVariantFilter = sedan || suv || hatchback || luxury;
        boolean hasFuelFilter = petrol || diesel || cng || electric;
        boolean hasTransmissionFilter = manual || automatic;
        boolean hasOwnerFilter = firstOwner || secondOwner || thirdOwner || fourthOwner;

        // If no filters applied, show all cars
        if (!hasVariantFilter && !hasFuelFilter && !hasTransmissionFilter && !hasOwnerFilter) {
            filteredList.addAll(carList);
        } else {
            // Apply filters
            for (ApiAllCar car : carList) {
                boolean matchesFilter = true;

                // Check variant filter
                if (hasVariantFilter) {
                    String variant = car.getVariant();
                    boolean variantMatch = false;
                    if (sedan && variant != null && variant.toLowerCase().contains("sedan")) variantMatch = true;
                    if (suv && variant != null && variant.toLowerCase().contains("suv")) variantMatch = true;
                    if (hatchback && variant != null && variant.toLowerCase().contains("hatchback")) variantMatch = true;
                    if (luxury && variant != null && variant.toLowerCase().contains("luxury")) variantMatch = true;
                    if (!variantMatch) matchesFilter = false;
                }

                // Check fuel filter
                if (hasFuelFilter && matchesFilter) {
                    String fuel = car.getFuelType();
                    boolean fuelMatch = false;
                    if (petrol && fuel != null && fuel.toLowerCase().contains("petrol")) fuelMatch = true;
                    if (diesel && fuel != null && fuel.toLowerCase().contains("diesel")) fuelMatch = true;
                    if (cng && fuel != null && fuel.toLowerCase().contains("cng")) fuelMatch = true;
                    if (electric && fuel != null && fuel.toLowerCase().contains("electric")) fuelMatch = true;
                    if (!fuelMatch) matchesFilter = false;
                }

                // Check transmission filter
                if (hasTransmissionFilter && matchesFilter) {
                    String transmission = car.getTransmission();
                    boolean transmissionMatch = false;
                    if (manual && transmission != null && transmission.toLowerCase().contains("manual")) transmissionMatch = true;
                    if (automatic && transmission != null && transmission.toLowerCase().contains("automatic")) transmissionMatch = true;
                    if (!transmissionMatch) matchesFilter = false;
                }

                // Check owner filter
//                if (hasOwnerFilter && matchesFilter) {
//                    // Use the correct method name based on your ApiAllCar class
//                    String owner = car.getOwnerCount(); // Change this to match your actual method
//                    boolean ownerMatch = false;
//                    if (firstOwner && owner != null && (owner.contains("1") || owner.toLowerCase().contains("first"))) ownerMatch = true;
//                    if (secondOwner && owner != null && (owner.contains("2") || owner.toLowerCase().contains("second"))) ownerMatch = true;
//                    if (thirdOwner && owner != null && (owner.contains("3") || owner.toLowerCase().contains("third"))) ownerMatch = true;
//                    if (fourthOwner && owner != null && (owner.contains("4") || owner.toLowerCase().contains("fourth") || owner.contains("+"))) ownerMatch = true;
//                    if (!ownerMatch) matchesFilter = false;
//                }

                if (matchesFilter) {
                    filteredList.add(car);
                }
            }
        }

        // Update adapter
//        if (apiCarAdapter != null) {
//            apiCarAdapter.updateCarList(filteredList);
//            apiCarAdapter.notifyDataSetChanged();
//            Log.d("Filter", "Filtered cars: " + filteredList.size() + " out of " + carList.size());
//        }
    }
}