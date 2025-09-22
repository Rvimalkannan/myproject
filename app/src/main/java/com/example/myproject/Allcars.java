package com.example.myproject;

import android.content.res.ColorStateList;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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

    // Show Variant Filter Buttons
    Button btnSedan, btnSUV, btnHatchback, btnLuxury;

    // Fuel Type Filter Buttons
    Button btnPetrol, btnDiesel, btnCNG, btnElectric;

    // Transmission Filter Buttons
    Button btnManual, btnAutomatic;

    // Number of Owners Filter Buttons
    Button btnFirstOwner, btnSecondOwner, btnThirdOwner, btnFourthOwner;

    Button btnReset;
    Button btnApply;
    TextView tvCancel;

    // Track button states
    private boolean isSelectedSedan = false, isSelectedSUV = false, isSelectedHatchback = false, isSelectedLuxury = false;
    private boolean isSelectedPetrol = false, isSelectedDiesel = false, isSelectedCNG = false, isSelectedElectric = false;
    private boolean isSelectedManual = false, isSelectedAutomatic = false;
    private boolean isSelectedFirstOwner = false, isSelectedSecondOwner = false, isSelectedThirdOwner = false, isSelectedFourthOwner = false;

    // Colors for text states
    private int selectedTextColor;
    private int normalTextColor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_allcars, container, false);

        // Initialize colors
        selectedTextColor = ContextCompat.getColor(getContext(), android.R.color.holo_green_dark);
        normalTextColor = ContextCompat.getColor(getContext(), android.R.color.darker_gray);

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
        // Show Variant buttons
        btnSedan = view.findViewById(R.id.btnSedan);
        btnSUV = view.findViewById(R.id.btnSUV);
        btnHatchback = view.findViewById(R.id.btnHatchback);
        btnLuxury = view.findViewById(R.id.btnLuxury);

        // Fuel Type buttons
        btnPetrol = view.findViewById(R.id.btnPetrol);
        btnDiesel = view.findViewById(R.id.btnDiesel);
        btnCNG = view.findViewById(R.id.btnCNG);
        btnElectric = view.findViewById(R.id.btnElectric);

        // Transmission buttons
        btnManual = view.findViewById(R.id.btnManual);
        btnAutomatic = view.findViewById(R.id.btnAutomatic);

        // Number of Owners buttons
        btnFirstOwner = view.findViewById(R.id.btnFirstOwner);
        btnSecondOwner = view.findViewById(R.id.btnSecondOwner);
        btnThirdOwner = view.findViewById(R.id.btnThirdOwner);
        btnFourthOwner = view.findViewById(R.id.btnFourthOwner);

        // Action buttons
        btnReset = view.findViewById(R.id.btnReset);
        btnApply = view.findViewById(R.id.btnApplyFilter);
        tvCancel = view.findViewById(R.id.tvCancel);

        // Remove background tint and initialize button colors
        removeBackgroundTintAndInitialize();

        // Setup filter button listeners
        setupFilterButtonListeners();
    }

    private void removeBackgroundTintAndInitialize() {
        // All filter buttons array
        Button[] allFilterButtons = {
                btnSedan, btnSUV, btnHatchback, btnLuxury,
                btnPetrol, btnDiesel, btnCNG, btnElectric,
                btnManual, btnAutomatic,
                btnFirstOwner, btnSecondOwner, btnThirdOwner, btnFourthOwner
        };

        // Remove background tint and set normal text color for all filter buttons
        for (Button button : allFilterButtons) {
            // Remove the violet/purple background tint
            button.setBackgroundTintList(null);

            // Set normal text color
            button.setTextColor(normalTextColor);
        }
    }

    private void setupFilterButtonListeners() {
        // Show Variant buttons
        btnSedan.setOnClickListener(v -> toggleButton(btnSedan, "sedan"));
        btnSUV.setOnClickListener(v -> toggleButton(btnSUV, "suv"));
        btnHatchback.setOnClickListener(v -> toggleButton(btnHatchback, "hatchback"));
        btnLuxury.setOnClickListener(v -> toggleButton(btnLuxury, "luxury"));

        // Fuel Type buttons
        btnPetrol.setOnClickListener(v -> toggleButton(btnPetrol, "petrol"));
        btnDiesel.setOnClickListener(v -> toggleButton(btnDiesel, "diesel"));
        btnCNG.setOnClickListener(v -> toggleButton(btnCNG, "cng"));
        btnElectric.setOnClickListener(v -> toggleButton(btnElectric, "electric"));

        // Transmission buttons
        btnManual.setOnClickListener(v -> toggleButton(btnManual, "manual"));
        btnAutomatic.setOnClickListener(v -> toggleButton(btnAutomatic, "automatic"));

        // Number of Owners buttons (single select)
        btnFirstOwner.setOnClickListener(v -> selectOwnerButton(btnFirstOwner, "first"));
        btnSecondOwner.setOnClickListener(v -> selectOwnerButton(btnSecondOwner, "second"));
        btnThirdOwner.setOnClickListener(v -> selectOwnerButton(btnThirdOwner, "third"));
        btnFourthOwner.setOnClickListener(v -> selectOwnerButton(btnFourthOwner, "fourth"));
    }

    private void toggleButton(Button button, String type) {
        boolean isSelected = button.isSelected();
        button.setSelected(!isSelected);

        // Update text color based on selection state
        if (!isSelected) {
            // Button is now selected - set selected color
            button.setTextColor(selectedTextColor);
        } else {
            // Button is now deselected - set normal color
            button.setTextColor(normalTextColor);
        }

        // Update state variables
        switch (type) {
            case "sedan": isSelectedSedan = !isSelected; break;
            case "suv": isSelectedSUV = !isSelected; break;
            case "hatchback": isSelectedHatchback = !isSelected; break;
            case "luxury": isSelectedLuxury = !isSelected; break;
            case "petrol": isSelectedPetrol = !isSelected; break;
            case "diesel": isSelectedDiesel = !isSelected; break;
            case "cng": isSelectedCNG = !isSelected; break;
            case "electric": isSelectedElectric = !isSelected; break;
            case "manual": isSelectedManual = !isSelected; break;
            case "automatic": isSelectedAutomatic = !isSelected; break;
        }
    }

    private void selectOwnerButton(Button selectedButton, String type) {
        // Reset all owner buttons
        Button[] ownerButtons = {btnFirstOwner, btnSecondOwner, btnThirdOwner, btnFourthOwner};

        for (Button button : ownerButtons) {
            button.setSelected(false);
            button.setTextColor(normalTextColor);
        }

        // Reset state variables
        isSelectedFirstOwner = false;
        isSelectedSecondOwner = false;
        isSelectedThirdOwner = false;
        isSelectedFourthOwner = false;

        // Set selected button and state
        selectedButton.setSelected(true);
        selectedButton.setTextColor(selectedTextColor);

        switch (type) {
            case "first": isSelectedFirstOwner = true; break;
            case "second": isSelectedSecondOwner = true; break;
            case "third": isSelectedThirdOwner = true; break;
            case "fourth": isSelectedFourthOwner = true; break;
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
            for (ApiAllCar car : carList) {
                // Search in car name, variant, location, fuel type - using correct method names
                if ((car.getCarName() != null && car.getCarName().toLowerCase().contains(lowerCaseQuery)) ||
                        (car.getVariant() != null && car.getVariant().toLowerCase().contains(lowerCaseQuery)) ||
                        (car.getLocation() != null && car.getLocation().toLowerCase().contains(lowerCaseQuery)) ||
                        (car.getFuelType() != null && car.getFuelType().toLowerCase().contains(lowerCaseQuery))) {
                    searchFilteredList.add(car);
                }
            }
        }

        filteredList = searchFilteredList;
        if (apiCarAdapter != null) {
            apiCarAdapter.updateCarList(filteredList);
            apiCarAdapter.notifyDataSetChanged();
        }
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
        // Get all filter buttons
        Button[] allFilterButtons = {
                btnSedan, btnSUV, btnHatchback, btnLuxury,
                btnPetrol, btnDiesel, btnCNG, btnElectric,
                btnManual, btnAutomatic,
                btnFirstOwner, btnSecondOwner, btnThirdOwner, btnFourthOwner
        };

        // Reset all filter buttons
        for (Button button : allFilterButtons) {
            button.setSelected(false);
            button.setTextColor(normalTextColor);
        }

        // Reset all state variables
        isSelectedSedan = false;
        isSelectedSUV = false;
        isSelectedHatchback = false;
        isSelectedLuxury = false;
        isSelectedPetrol = false;
        isSelectedDiesel = false;
        isSelectedCNG = false;
        isSelectedElectric = false;
        isSelectedManual = false;
        isSelectedAutomatic = false;
        isSelectedFirstOwner = false;
        isSelectedSecondOwner = false;
        isSelectedThirdOwner = false;
        isSelectedFourthOwner = false;
    }

    private void applyFilters() {
        if (carList == null || carList.isEmpty()) {
            return;
        }

        filteredList.clear();

        // Check if any filters are applied
        boolean hasVariantFilter = isSelectedSedan || isSelectedSUV || isSelectedHatchback || isSelectedLuxury;
        boolean hasFuelFilter = isSelectedPetrol || isSelectedDiesel || isSelectedCNG || isSelectedElectric;
        boolean hasTransmissionFilter = isSelectedManual || isSelectedAutomatic;
        boolean hasOwnerFilter = isSelectedFirstOwner || isSelectedSecondOwner || isSelectedThirdOwner || isSelectedFourthOwner;

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
                    if (isSelectedSedan && variant != null && variant.toLowerCase().contains("sedan")) variantMatch = true;
                    if (isSelectedSUV && variant != null && variant.toLowerCase().contains("suv")) variantMatch = true;
                    if (isSelectedHatchback && variant != null && variant.toLowerCase().contains("hatchback")) variantMatch = true;
                    if (isSelectedLuxury && variant != null && variant.toLowerCase().contains("luxury")) variantMatch = true;
                    if (!variantMatch) matchesFilter = false;
                }

                // Check fuel filter
                if (hasFuelFilter && matchesFilter) {
                    String fuel = car.getFuelType();
                    boolean fuelMatch = false;
                    if (isSelectedPetrol && fuel != null && fuel.toLowerCase().contains("petrol")) fuelMatch = true;
                    if (isSelectedDiesel && fuel != null && fuel.toLowerCase().contains("diesel")) fuelMatch = true;
                    if (isSelectedCNG && fuel != null && fuel.toLowerCase().contains("cng")) fuelMatch = true;
                    if (isSelectedElectric && fuel != null && fuel.toLowerCase().contains("electric")) fuelMatch = true;
                    if (!fuelMatch) matchesFilter = false;
                }

                // Check transmission filter
                if (hasTransmissionFilter && matchesFilter) {
                    String transmission = car.getTransmission();
                    boolean transmissionMatch = false;
                    if (isSelectedManual && transmission != null && transmission.toLowerCase().contains("manual")) transmissionMatch = true;
                    if (isSelectedAutomatic && transmission != null && transmission.toLowerCase().contains("automatic")) transmissionMatch = true;
                    if (!transmissionMatch) matchesFilter = false;
                }

                // Check owner filter - using correct method name
                if (hasOwnerFilter && matchesFilter) {
                    String owner = car.getNoOfOwners(); // Using correct method name
                    boolean ownerMatch = false;
                    if (isSelectedFirstOwner && owner != null && (owner.contains("1") || owner.toLowerCase().contains("first"))) ownerMatch = true;
                    if (isSelectedSecondOwner && owner != null && (owner.contains("2") || owner.toLowerCase().contains("second"))) ownerMatch = true;
                    if (isSelectedThirdOwner && owner != null && (owner.contains("3") || owner.toLowerCase().contains("third"))) ownerMatch = true;
                    if (isSelectedFourthOwner && owner != null && (owner.contains("4") || owner.toLowerCase().contains("fourth") || owner.contains("+"))) ownerMatch = true;
                    if (!ownerMatch) matchesFilter = false;
                }

                if (matchesFilter) {
                    filteredList.add(car);
                }
            }
        }

        // Update adapter
        if (apiCarAdapter != null) {
            apiCarAdapter.updateCarList(filteredList);
            apiCarAdapter.notifyDataSetChanged();
            Log.d("Filter", "Filtered cars: " + filteredList.size() + " out of " + carList.size());
        }
    }
}