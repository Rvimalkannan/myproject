package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextInputEditText editPassword;
    android.widget.EditText editEmail;
    Button btnLogin;
    TextView txtRegister;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn = sharedPref.getBoolean(KEY_LOGGED_IN, false);

        if (isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, home.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);

        // Fill email if passed from registration
        String prefilledEmail = getIntent().getStringExtra("email");
        if (prefilledEmail != null) editEmail.setText(prefilledEmail);

        btnLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else {
                loginapi.login(email, password, (success, response) -> {
                    if (success) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String token = jsonObject.optString("token", "");

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
        });

        // Open Registration page
        txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, registrationform.class);
            startActivity(intent);
        });
    }
}
