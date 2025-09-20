package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText editEmail, editPassword;
    Button btnLogin;
    TextView txtRegister;

    // 🔹 SharedPreferences constants
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🔹 Check if already logged in
        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn = sharedPref.getBoolean(KEY_LOGGED_IN, false);

        if (isLoggedIn) {
            // Directly go to Home screen
            Intent intent = new Intent(MainActivity.this, home.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        // 🔹 Initialize UI
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);

        // 🔹 Login Button Click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    loginapi.login(email, password, (success, response) -> {
                        if (success) {
                            try {
                                // ✅ Parse JSON response (adjust keys as per your API response)
                                JSONObject jsonObject = new JSONObject(response);
                                String token = jsonObject.optString("token", ""); // token key depends on your API

                                // Save to SharedPreferences
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString(KEY_EMAIL, email);
                                editor.putString(KEY_TOKEN, token);
                                editor.putBoolean(KEY_LOGGED_IN, true);
                                editor.apply();

                                runOnUiThread(() -> {
                                    Toast.makeText(MainActivity.this, "Login Successful ✅", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, home.class);
                                    startActivity(intent);
                                    finish();
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                                runOnUiThread(() ->
                                        Toast.makeText(MainActivity.this, "Response parsing error", Toast.LENGTH_SHORT).show()
                                );
                            }
                        } else {
                            runOnUiThread(() ->
                                    Toast.makeText(MainActivity.this, "Login Failed ❌", Toast.LENGTH_SHORT).show()
                            );
                        }
                    });
                }
            }
        });

        // 🔹 Register Click
        txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, home.class);
            startActivity(intent);
        });
    }
}
