package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    EditText editEmail, editPassword;
    Button btnLogin;
    TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Make sure your XML file is activity_main.xml

        // 🔹 Initialize UI components
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

                // Simple validation
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    loginapi.login(email, password, new loginapi.LoginCallback() {
                        @Override
                        public void onResult(boolean success, String response) {
                            if (success) {
                                runOnUiThread(() ->
                                        Toast.makeText(MainActivity.this, "Login Successful ✅", Toast.LENGTH_SHORT).show()
                                );
                                // Redirect to Home
                                Intent intent = new Intent(MainActivity.this, home.class);
                                startActivity(intent);
                            } else {
                                runOnUiThread(() ->
                                        Toast.makeText(MainActivity.this, "Login Failed: ", Toast.LENGTH_SHORT).show()
                                );
                            }
                        }
                    });
                }
            }
        });

        // 🔹 Register Text Click
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open RegisterActivity (create RegisterActivity.java)
                Intent intent = new Intent(MainActivity.this, home.class);
                startActivity(intent);
            }
        });
    }
}
