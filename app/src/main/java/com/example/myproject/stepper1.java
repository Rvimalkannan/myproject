package com.example.myproject;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class stepper1 extends AppCompatActivity {

    private static final int MAX_IMAGES = 12;

    private GridLayout selectedImagesGrid;
    private Button btnContinue;
    private ScrollView scrollbarImage;
    private final ArrayList<Uri> imageUris = new ArrayList<>();

    private ActivityResultLauncher<Intent> pickImagesLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepper1);

        LinearLayout uploadContainer = findViewById(R.id.upload_container);
        selectedImagesGrid = findViewById(R.id.selected_images_grid);
        btnContinue = findViewById(R.id.btnContinue);
        scrollbarImage = findViewById(R.id.scrollbarImage);

        // Picker
        pickImagesLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        ClipData clipData = result.getData().getClipData();
                        Uri singleImage = result.getData().getData();

                        int added = 0;
                        if (clipData != null) {
                            scrollbarImage.setVisibility(View.VISIBLE);
                            int count = clipData.getItemCount();
                            for (int i = 0; i < count; i++) {
                                if (imageUris.size() >= MAX_IMAGES) break;
                                Uri uri = clipData.getItemAt(i).getUri();
                                imageUris.add(uri);
                                added++;
                            }
                        } else if (singleImage != null && imageUris.size() < MAX_IMAGES) {
                            scrollbarImage.setVisibility(View.VISIBLE);
                            imageUris.add(singleImage);
                            added++;
                        }

                        if (added == 0 && imageUris.size() >= MAX_IMAGES) {
                            Toast.makeText(this, "Maximum 12 images allowed", Toast.LENGTH_SHORT).show();
                        }

                        displaySelectedImages();
                        updateContinueState();
                    }
                });

        uploadContainer.setOnClickListener(v -> openImagePicker());

        btnContinue.setOnClickListener(v -> {
            if (imageUris.isEmpty()) {
                Toast.makeText(this, "Please upload at least 1 image", Toast.LENGTH_SHORT).show();
                return;
            }
            // Navigate to stepper2 with the image URIs
            Intent intent = new Intent(this, stepper2.class);
            ArrayList<String> asStrings = new ArrayList<>();
            for (Uri u : imageUris) asStrings.add(u.toString());
            intent.putStringArrayListExtra("selected_images", asStrings);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        });

        updateContinueState();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        pickImagesLauncher.launch(intent);
    }

    private void displaySelectedImages() {
        selectedImagesGrid.removeAllViews();

        int screenW = getResources().getDisplayMetrics().widthPixels;
        int col = 3;
        int marginPx = (int) (8 * getResources().getDisplayMetrics().density);
        int itemW = screenW / col - (marginPx * 2);

        for (Uri uri : imageUris) {
            ImageView iv = new ImageView(this);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = itemW;
            params.height = itemW;           // square thumbnails
            params.setMargins(marginPx, marginPx, marginPx, marginPx);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setImageURI(uri);
            selectedImagesGrid.addView(iv);
        }
    }

    private void updateContinueState() {
        boolean enabled = !imageUris.isEmpty();
        btnContinue.setEnabled(enabled);
        btnContinue.setAlpha(enabled ? 1f : 0.5f);
    }
}
