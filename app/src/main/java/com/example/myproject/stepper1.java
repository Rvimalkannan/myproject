package com.example.myproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class stepper1 extends AppCompatActivity {

    private LinearLayout uploadBox;
    private Button btnContinue;
    private List<Uri> selectedImages = new ArrayList<>();

    // Activity Result API for selecting multiple images
    private ActivityResultLauncher<String> pickImagesLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper1);

        // Initialize views
        uploadBox = findViewById(R.id.upload_container);
        btnContinue = findViewById(R.id.btnContinue);

        // Initialize launcher (MULTIPLE image picker)
        pickImagesLauncher = registerForActivityResult(
                new ActivityResultContracts.GetMultipleContents(),
                uris -> {
                    if (uris != null && !uris.isEmpty()) {
                        for (Uri uri : uris) {
                            if (selectedImages.size() >= 8) {
                                Toast.makeText(this, "Maximum 8 images allowed", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            selectedImages.add(uri);
                        }
                        updateUploadBoxState();
                        Toast.makeText(this,
                                selectedImages.size() + " image(s) selected",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Upload image click
        uploadBox.setOnClickListener(v -> pickImagesLauncher.launch("image/*"));

        // Continue button
        btnContinue.setOnClickListener(v -> {
            if (selectedImages.isEmpty()) {
                Toast.makeText(this, "Please upload at least 1 image", Toast.LENGTH_SHORT).show();
                return;
            }
            // Save selected images and proceed to next step
            proceedToNextStep();
        });

        // Initial button state
        updateUploadBoxState();
    }

    private void updateUploadBoxState() {
        // Enable continue button if at least one image is selected
        btnContinue.setEnabled(!selectedImages.isEmpty());
        btnContinue.setAlpha(selectedImages.isEmpty() ? 0.5f : 1.0f);
    }

    private void proceedToNextStep() {
        Intent intent = new Intent(this, stepper2.class);  // Changed from stepper3 to stepper2
        // Add selected images to intent
        ArrayList<String> imageUris = new ArrayList<>();
        for (Uri uri : selectedImages) {
            imageUris.add(uri.toString());
        }
        intent.putStringArrayListExtra("selected_images", imageUris);
        startActivity(intent);
    }
}
