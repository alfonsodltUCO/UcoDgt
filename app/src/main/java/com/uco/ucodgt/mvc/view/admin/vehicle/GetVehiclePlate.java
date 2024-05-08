package com.uco.ucodgt.mvc.view.admin.vehicle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.io.ByteArrayOutputStream;

import com.uco.ucodgt.mvc.controller.admin.vehicle.CheckImage;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;

/**
 * Activity class to capture or select an image of a vehicle plate.
 * @author Alfonso de la torre
 */
public class GetVehiclePlate extends AppCompatActivity {

    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<String> galleryLauncher;
    Button manual;

    Button goMain;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_PICK = 102;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.get_vehicle);
        manual=findViewById(com.uco.ucodgt.R.id.manualWay);
        Button takePhotoButton = findViewById(com.uco.ucodgt.R.id.takePhotoButton);
        Button pickPhotoButton = findViewById(com.uco.ucodgt.R.id.pickPhotoButton);
        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);

        goMain.setOnClickListener(v->{
            Intent intent = new Intent(GetVehiclePlate.this, AdminActivity.class);
            startActivity(intent);

        });
        manual.setOnClickListener(v -> {
                Intent intent = new Intent(GetVehiclePlate.this, IntroduceManual.class);
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
                        // Launch CheckImage activity with the captured image bitmap
                        assert imageBitmap != null;
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
                    // Launch CheckImage activity with the selected image bitmap
                    launchCheckImageActivity(selectedImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        takePhotoButton.setOnClickListener(v -> openCamera());

        pickPhotoButton.setOnClickListener(v -> openGallery());
    }

    /**
     * Opens the camera to capture an image if the necessary permission is granted.
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
     * Opens the gallery to select an image if the necessary permission is granted.
     */
    private void openGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_IMAGE_PICK);
        } else {
            galleryLauncher.launch("image/*");
        }
    }

    /**
     * Launches the activity to check the captured or selected image.
     *
     * @param bitmap The bitmap image to be checked.
     */
    private void launchCheckImageActivity(Bitmap bitmap) {
        Intent intentCheckVehiclePlate = new Intent(GetVehiclePlate.this, CheckImage.class);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
        intentCheckVehiclePlate.putExtra("image", bs.toByteArray());
        startActivity(intentCheckVehiclePlate);

    }

    /**
     * Callback for the result from requesting permissions.
     *
     * @param requestCode  The request code passed in requestPermissions.
     * @param permissions  The requested permissions.
     * @param grantResults The grant results for the corresponding permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }

}
