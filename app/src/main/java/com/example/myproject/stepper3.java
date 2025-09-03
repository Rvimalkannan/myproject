package com.example.myproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class stepper3 extends AppCompatActivity {

    // UI Components
    private ScrollView stepper3;
    private ImageView btnBack, btnCalendar;
    private TextView tvProgress;
    private ProgressBar progressBar;
    private Spinner spinnerFuelType, spinnerTransmission, spinnerOwnerCount;
    private EditText etKmsDriven, etDescription, etInsuranceEndDate;
    private Button btnPrevious, btnContinue;

    // Date picker
    private DatePickerDialog datePickerDialog;
    private String selectedDate = "";

    // Selected images from previous step
    private ArrayList<String> selectedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper3);

        Intent intentImage = getIntent();
        ArrayList<String> selectedImages = intentImage.getStringArrayListExtra("selected_images");

//        // Get selected images and other data from previous step
//        selectedImages = getIntent().getStringArrayListExtra("selected_images");

        initializeViews();
        setupSpinners();
        setupClickListeners();
        setupValidation();
    }

    private void initializeViews() {
        // Initialize all views
        stepper3 = findViewById(R.id.stepper3);
        btnBack = findViewById(R.id.btnBack);
        tvProgress = findViewById(R.id.tvProgress);
        progressBar = findViewById(R.id.progressBar);
        spinnerFuelType = findViewById(R.id.spinnerFuelType);
        spinnerTransmission = findViewById(R.id.spinnerTransmission);
        spinnerOwnerCount = findViewById(R.id.spinnerOwnerCount);
        etKmsDriven = findViewById(R.id.etKmsDriven);
        etDescription = findViewById(R.id.etDescription);
        etInsuranceEndDate = findViewById(R.id.etInsuranceEndDate);
        btnCalendar = findViewById(R.id.btnCalendar);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnContinue = findViewById(R.id.btnContinue);
    }

    private void setupSpinners() {
        // Fuel Type Spinner
        String[] fuelTypes = {"Select Fuel Type", "Petrol", "Diesel", "Electric", "Hybrid", "CNG", "LPG"};
        ArrayAdapter<String> fuelAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, fuelTypes) {
            @Override
            public boolean isEnabled(int position) {
                // Disable first item (hint)
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    tv.setTextColor(getResources().getColor(android.R.color.black));
                }
                return view;
            }
        };
        fuelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFuelType.setAdapter(fuelAdapter);
        spinnerFuelType.setSelection(1); // Default to "Petrol"

        // Transmission Spinner
        String[] transmissionTypes = {"Select Transmission", "Automatic", "Manual", "CVT", "AMT"};
        ArrayAdapter<String> transmissionAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, transmissionTypes) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    tv.setTextColor(getResources().getColor(android.R.color.black));
                }
                return view;
            }
        };
        transmissionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTransmission.setAdapter(transmissionAdapter);
        spinnerTransmission.setSelection(1); // Default to "Automatic"

        // Owner Count Spinner
        String[] ownerCounts = {"Select Owner Count", "1st Owner", "2nd Owner", "3rd Owner", "4+ Owners"};
        ArrayAdapter<String> ownerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ownerCounts) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    tv.setTextColor(getResources().getColor(android.R.color.black));
                }
                return view;
            }
        };
        ownerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOwnerCount.setAdapter(ownerAdapter);
        spinnerOwnerCount.setSelection(4); // Default to "4+ Owners"
    }

    private void setupClickListeners() {
        // Back button click listener
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Previous button click listener
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to previous step (Step 2)
                goToPreviousStep();
            }
        });

        // Continue button click listener
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    proceedToNextStep();
                }
            }
        });

        // Insurance end date click listeners
        etInsuranceEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // Spinner listeners for validation
        setupSpinnerListeners();
    }

    private void setupSpinnerListeners() {
        spinnerFuelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String selectedFuelType = parent.getItemAtPosition(position).toString();
                    // Handle fuel type selection
                    Toast.makeText(stepper3.this, "Selected: " + selectedFuelType, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerTransmission.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String selectedTransmission = parent.getItemAtPosition(position).toString();
                    // Handle transmission selection
                    Toast.makeText(stepper3.this, "Selected: " + selectedTransmission, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerOwnerCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    String selectedOwnerCount = parent.getItemAtPosition(position).toString();
                    // Handle owner count selection
                    Toast.makeText(stepper3.this, "Selected: " + selectedOwnerCount, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupValidation() {
        // Add text change listeners for real-time validation
        etKmsDriven.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateKmsDriven();
                }
            }
        });

        etDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateDescription();
                }
            }
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Format the date
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, month, dayOfMonth);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        selectedDate = sdf.format(selectedCalendar.getTime());
                        etInsuranceEndDate.setText(selectedDate);
                        etInsuranceEndDate.setError(null);

                        // Show success message
                        Toast.makeText(stepper3.this, "Date selected: " + selectedDate, Toast.LENGTH_SHORT).show();
                    }
                }, year, month, day);

        // Set minimum date to today
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        // Set maximum date to 5 years from now
        Calendar maxDate = Calendar.getInstance();
        maxDate.add(Calendar.YEAR, 5);
        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());

        datePickerDialog.show();
    }

    private boolean validateForm() {
        boolean isValid = true;

        // Validate Fuel Type
        if (spinnerFuelType.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select fuel type", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        // Validate Transmission
        if (spinnerTransmission.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select transmission type", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        // Validate Owner Count
        if (spinnerOwnerCount.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select owner count", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        // Validate KMs Driven
        if (!validateKmsDriven()) {
            isValid = false;
        }

        // Validate Description
        if (!validateDescription()) {
            isValid = false;
        }

        // Validate Insurance End Date
        String insuranceDate = etInsuranceEndDate.getText().toString().trim();
        if (insuranceDate.isEmpty() || insuranceDate.equals("Select Date")) {
            etInsuranceEndDate.setError("Please select insurance end date");
            etInsuranceEndDate.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    private boolean validateKmsDriven() {
        String kmsDriven = etKmsDriven.getText().toString().trim();

        if (kmsDriven.isEmpty()) {
            etKmsDriven.setError("Please enter kilometers driven");
            etKmsDriven.requestFocus();
            return false;
        }

        try {
            int kms = Integer.parseInt(kmsDriven);
            if (kms < 0) {
                etKmsDriven.setError("Kilometers cannot be negative");
                etKmsDriven.requestFocus();
                return false;
            }
            if (kms > 999999) {
                etKmsDriven.setError("Please enter a valid kilometer value");
                etKmsDriven.requestFocus();
                return false;
            }
            etKmsDriven.setError(null);
            return true;
        } catch (NumberFormatException e) {
            etKmsDriven.setError("Please enter a valid number");
            etKmsDriven.requestFocus();
            return false;
        }
    }

    private boolean validateDescription() {
        String description = etDescription.getText().toString().trim();

        if (description.isEmpty()) {
            etDescription.setError("Please enter a description");
            etDescription.requestFocus();
            return false;
        }

        if (description.length() < 10) {
            etDescription.setError("Description must be at least 10 characters long");
            etDescription.requestFocus();
            return false;
        }

        etDescription.setError(null);
        return true;
    }

    private void proceedToNextStep() {
        // Create intent for stepper4
        Intent intent = new Intent(stepper3.this, stepper4.class);

        // Pass selected images along with other data
        intent.putStringArrayListExtra("selected_images", selectedImages);

        // Add current step data
        String fuelType = spinnerFuelType.getSelectedItem().toString();
        String transmission = spinnerTransmission.getSelectedItem().toString();
        String kmsDriven = etKmsDriven.getText().toString().trim();
        String ownerCount = spinnerOwnerCount.getSelectedItem().toString();
        String description = etDescription.getText().toString().trim();

        // Pass all previous data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            intent.putExtras(extras);
        }

        intent.putExtra("fuel_type", fuelType);
        intent.putExtra("transmission", transmission);
        intent.putExtra("kms_driven", kmsDriven);
        intent.putExtra("owner_count", ownerCount);
        intent.putExtra("description", description);

        // Start stepper4 activity
        startActivity(intent);
    }

    private void goToPreviousStep() {
        // Save current data before going back
        saveCurrentFormData();

        Toast.makeText(this, "Going back to Step 2 of 4", Toast.LENGTH_SHORT).show();

        // Navigate to previous step
        // Intent intent = new Intent(this, Step2Activity.class);
        // startActivity(intent);

        onBackPressed();
    }

    private void saveCurrentFormData() {
        // Save current form data to shared preferences for later retrieval
        android.content.SharedPreferences prefs = getSharedPreferences("CarFormData", MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = prefs.edit();

        if (spinnerFuelType.getSelectedItemPosition() > 0) {
            editor.putString("step3_fuelType", spinnerFuelType.getSelectedItem().toString());
        }
        if (spinnerTransmission.getSelectedItemPosition() > 0) {
            editor.putString("step3_transmission", spinnerTransmission.getSelectedItem().toString());
        }
        if (spinnerOwnerCount.getSelectedItemPosition() > 0) {
            editor.putString("step3_ownerCount", spinnerOwnerCount.getSelectedItem().toString());
        }

        editor.putString("step3_kmsDriven", etKmsDriven.getText().toString().trim());
        editor.putString("step3_description", etDescription.getText().toString().trim());
        editor.putString("step3_insuranceDate", etInsuranceEndDate.getText().toString().trim());

        editor.apply();
    }

    private void saveCarDataToPreferences(Bundle carData) {
        android.content.SharedPreferences prefs = getSharedPreferences("CarFormData", MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = prefs.edit();

        for (String key : carData.keySet()) {
            editor.putString(key, carData.getString(key));
        }

        editor.putBoolean("step3_completed", true);
        editor.apply();

        Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
    }

    private void logCarData(Bundle carData) {
        System.out.println("=== Car Form Data (Step 3) ===");
        for (String key : carData.keySet()) {
            System.out.println(key + ": " + carData.getString(key));
        }
        System.out.println("===============================");
    }

    private void loadSavedData() {
        // Load previously saved data if available
        android.content.SharedPreferences prefs = getSharedPreferences("CarFormData", MODE_PRIVATE);

        String savedKms = prefs.getString("step3_kmsDriven", "");
        if (!savedKms.isEmpty()) {
            etKmsDriven.setText(savedKms);
        }

        String savedDescription = prefs.getString("step3_description", "");
        if (!savedDescription.isEmpty()) {
            etDescription.setText(savedDescription);
        }

        String savedInsuranceDate = prefs.getString("step3_insuranceDate", "");
        if (!savedInsuranceDate.isEmpty()) {
            etInsuranceEndDate.setText(savedInsuranceDate);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load saved data when activity resumes
        loadSavedData();
    }

    @Override
    public void onBackPressed() {
        // Save current form data before going back
        saveCurrentFormData();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close date picker if still open
        if (datePickerDialog != null && datePickerDialog.isShowing()) {
            datePickerDialog.dismiss();
        }
    }
}