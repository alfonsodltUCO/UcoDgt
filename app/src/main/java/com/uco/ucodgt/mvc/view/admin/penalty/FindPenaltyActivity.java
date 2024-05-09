package com.uco.ucodgt.mvc.view.admin.penalty;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.io.ByteArrayOutputStream;

import com.uco.ucodgt.mvc.controller.admin.vehicle.CheckImage;
import com.uco.ucodgt.mvc.view.admin.vehicle.IntroduceManual;
/**
 * Activity for Admin to find a penalty using either the device's camera or gallery for scan a licence plate.
 * @author Alfonso de la torre
 */
public class FindPenaltyActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<String> galleryLauncher;
    Button manual;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_PICK = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.get_vehicle);
        manual=findViewById(com.uco.ucodgt.R.id.manualWay);
        Button takePhotoButton = findViewById(com.uco.ucodgt.R.id.takePhotoButton);
        Button pickPhotoButton = findViewById(com.uco.ucodgt.R.id.pickPhotoButton);
        // Navigate to IntroduceManual activity when manual button is clicked

        manual.setOnClickListener(v -> {

            Intent intent = new Intent(FindPenaltyActivity.this, IntroduceManual.class);
            startActivity(intent);

        });
        // Initialize ActivityResultLauncher for camera
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            if (result.getResultCode() == RESULT_OK) {

                Intent data = result.getData();

                if (data != null) {

                    Bundle extras = data.getExtras();

                    if (extras != null) {

                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        // Launch CheckImage activity with the captured bitmap
                        launchCheckImageActivity(imageBitmap);
                    }
                }
            }
        });

        // Initialize ActivityResultLauncher for gallery
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {

            if (result != null) {

                try {
                    Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), result);
                    // Launch CheckImage activity with the selected bitmap
                    launchCheckImageActivity(selectedImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Open camera when take photo button is clicked
        takePhotoButton.setOnClickListener(v -> openCamera());

        // Open gallery when pick photo button is clicked
        pickPhotoButton.setOnClickListener(v -> openGallery());
    }
    /**
     * Opens the device's camera to take a photo.
     * If the camera permission is not granted, requests it.
     */
    private void openCamera() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        } else {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(takePictureIntent);
        }
    }

    /**
     * Opens the device's gallery to pick a photo.
     */
    private void openGallery() {
         if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
             ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_IMAGE_PICK);
        } else {
             galleryLauncher.launch("image/*");
        }
    }

    /**
     * Launches the CheckImage activity with the provided bitmap.
     *
     * @param bitmap The bitmap image to be passed to the CheckImage activity.
     */
    private void launchCheckImageActivity(Bitmap bitmap) {

        Intent intentCheckVehiclePlate = new Intent(FindPenaltyActivity.this, CheckImage.class);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
        intentCheckVehiclePlate.putExtra("image", bs.toByteArray());
        startActivity(intentCheckVehiclePlate);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_PICK) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }
}
