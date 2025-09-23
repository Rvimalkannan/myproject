package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class registrationform extends AppCompatActivity {

    private TextInputEditText etFirstName, etLastName, etCompanyName, etEmail;
    private TextInputEditText etPhone, etLicense, etAddress, etCity;
    private TextInputEditText etZipCode, etYearsInBusiness, etPassword, etConfirmPassword;
    private AutoCompleteTextView spinnerBusinessType, spinnerState;
    private MaterialButton btnCreateAccount;
    private View tvSignIn;

    private TextInputLayout tilFirstName, tilLastName, tilCompanyName, tilEmail;
    private TextInputLayout tilPhone, tilLicense, tilAddress, tilCity;
    private TextInputLayout tilZipCode, tilYears, tilPassword, tilConfirmPassword;

    private String[] businessTypes = {"Auto Dealership","Motorcycle Dealership","RV Dealership","Boat Dealership","Equipment Dealer","Parts Dealer","Service Center","Other"};
    private String[] states = {"Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura","Uttar Pradesh","Uttarakhand","West Bengal"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationform);

        initViews();
        setupSpinners();
        setupClickListeners();
    }

    private void initViews() {
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etCompanyName = findViewById(R.id.etCompanyName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etLicense = findViewById(R.id.etLicense);
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etZipCode = findViewById(R.id.etZipCode);
        etYearsInBusiness = findViewById(R.id.etYearsInBusiness);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        spinnerBusinessType = findViewById(R.id.spinnerBusinessType);
        spinnerState = findViewById(R.id.spinnerState);

        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        tvSignIn = findViewById(R.id.tvSignIn);

        tilFirstName = (TextInputLayout) etFirstName.getParent().getParent();
        tilLastName = (TextInputLayout) etLastName.getParent().getParent();
        tilCompanyName = (TextInputLayout) etCompanyName.getParent().getParent();
        tilEmail = (TextInputLayout) etEmail.getParent().getParent();
        tilPhone = (TextInputLayout) etPhone.getParent().getParent();
        tilLicense = (TextInputLayout) etLicense.getParent().getParent();
        tilAddress = (TextInputLayout) etAddress.getParent().getParent();
        tilCity = (TextInputLayout) etCity.getParent().getParent();
        tilZipCode = (TextInputLayout) etZipCode.getParent().getParent();
        tilYears = (TextInputLayout) etYearsInBusiness.getParent().getParent();
        tilPassword = (TextInputLayout) etPassword.getParent().getParent();
        tilConfirmPassword = (TextInputLayout) etConfirmPassword.getParent().getParent();
    }

    private void setupSpinners() {
        spinnerBusinessType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, businessTypes));
        spinnerState.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, states));
    }

    private void setupClickListeners() {
        btnCreateAccount.setOnClickListener(v -> {
            if (validateForm()) {
                simulateAccountCreation(etEmail.getText().toString().trim());
            }
        });

        tvSignIn.setOnClickListener(v -> {
            // Return to Login
            Intent intent = new Intent(registrationform.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean validateForm() {
        boolean isValid = true;
        clearErrors();

        if (TextUtils.isEmpty(etFirstName.getText().toString().trim())) { tilFirstName.setError("First name is required"); isValid = false; }
        if (TextUtils.isEmpty(etLastName.getText().toString().trim())) { tilLastName.setError("Last name is required"); isValid = false; }
        if (TextUtils.isEmpty(etCompanyName.getText().toString().trim())) { tilCompanyName.setError("Company name is required"); isValid = false; }

        String email = etEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) { tilEmail.setError("Email is required"); isValid = false; }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { tilEmail.setError("Enter valid email"); isValid = false; }

        if (TextUtils.isEmpty(etPhone.getText().toString().trim()) || etPhone.getText().toString().trim().length()<10) { tilPhone.setError("Enter valid phone"); isValid = false; }
        if (TextUtils.isEmpty(etLicense.getText().toString().trim())) { tilLicense.setError("License required"); isValid = false; }
        if (TextUtils.isEmpty(spinnerBusinessType.getText().toString().trim()) || spinnerBusinessType.getText().toString().equals("Select business type")) { Toast.makeText(this,"Select business type",Toast.LENGTH_SHORT).show(); isValid=false; }
        if (TextUtils.isEmpty(etAddress.getText().toString().trim())) { tilAddress.setError("Address required"); isValid=false; }
        if (TextUtils.isEmpty(etCity.getText().toString().trim())) { tilCity.setError("City required"); isValid=false; }
        if (TextUtils.isEmpty(spinnerState.getText().toString().trim()) || spinnerState.getText().toString().equals("Select state")) { Toast.makeText(this,"Select state",Toast.LENGTH_SHORT).show(); isValid=false; }
        if (TextUtils.isEmpty(etZipCode.getText().toString().trim()) || etZipCode.getText().toString().trim().length()!=5) { tilZipCode.setError("Enter valid ZIP"); isValid=false; }

        String years = etYearsInBusiness.getText().toString().trim();
        if (TextUtils.isEmpty(years)) { tilYears.setError("Years required"); isValid=false; }
        else { try { int y=Integer.parseInt(years); if(y<0||y>100){ tilYears.setError("Enter valid years"); isValid=false; } } catch(Exception e){ tilYears.setError("Enter valid number"); isValid=false; } }

        String pass = etPassword.getText().toString();
        String confirmPass = etConfirmPassword.getText().toString();
        if (TextUtils.isEmpty(pass)) { tilPassword.setError("Password required"); isValid=false; }
        else if(!isValidPassword(pass)){ tilPassword.setError("Min 8 char, upper, lower, digit"); isValid=false; }
        if (TextUtils.isEmpty(confirmPass)) { tilConfirmPassword.setError("Confirm password"); isValid=false; }
        else if(!pass.equals(confirmPass)) { tilConfirmPassword.setError("Passwords do not match"); isValid=false; }

        return isValid;
    }

    private void clearErrors() {
        tilFirstName.setError(null); tilLastName.setError(null); tilCompanyName.setError(null); tilEmail.setError(null);
        tilPhone.setError(null); tilLicense.setError(null); tilAddress.setError(null); tilCity.setError(null);
        tilZipCode.setError(null); tilYears.setError(null); tilPassword.setError(null); tilConfirmPassword.setError(null);
    }

    private boolean isValidPassword(String password) {
        if(password.length()<8) return false;
        boolean u=false,l=false,d=false;
        for(char c:password.toCharArray()){ if(Character.isUpperCase(c)) u=true; else if(Character.isLowerCase(c)) l=true; else if(Character.isDigit(c)) d=true; }
        return u&&l&&d;
    }

    private void simulateAccountCreation(String email) {
        btnCreateAccount.setEnabled(false);
        btnCreateAccount.setText("Creating Account...");
        btnCreateAccount.postDelayed(() -> {
            btnCreateAccount.setEnabled(true);
            btnCreateAccount.setText("CREATE DEALER ACCOUNT");
            Toast.makeText(registrationform.this,"Account created successfully ✅",Toast.LENGTH_LONG).show();

            // Return to login with email pre-filled
            Intent intent = new Intent(registrationform.this, MainActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();
        }, 2000);
    }
}
