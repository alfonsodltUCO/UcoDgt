package mvc.view.admin.penalty;

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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ucodgt.R;

import java.io.ByteArrayOutputStream;

import mvc.controller.vehicle.CheckImage;
import mvc.view.admin.vehicle.GetVehiclePlate;
import mvc.view.admin.vehicle.IntroduceManual;

public class FindPenaltyActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<String> galleryLauncher;
    Button manual;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_PICK = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_vehicle);
        manual=findViewById(R.id.manualWay);
        Button takePhotoButton = findViewById(R.id.takePhotoButton);
        Button pickPhotoButton = findViewById(R.id.pickPhotoButton);
        manual.setOnClickListener(v -> {
            Intent intent = new Intent(FindPenaltyActivity.this, IntroduceManual.class);
            startActivity(intent);
            finish();
        });
        // Initialize ActivityResultLauncher for camera
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        // Aquí puedes lanzar la actividad CheckImage con el bitmap
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
                    // Aquí puedes lanzar la actividad CheckImage con el bitmap seleccionado
                    launchCheckImageActivity(selectedImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        takePhotoButton.setOnClickListener(v -> openCamera());

        pickPhotoButton.setOnClickListener(v -> openGallery());
    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(takePictureIntent);
        }
    }

    private void openGallery() {
        galleryLauncher.launch("image/*");
    }

    private void launchCheckImageActivity(Bitmap bitmap) {
        Intent intentCheckVehiclePlate = new Intent(FindPenaltyActivity.this, CheckImage.class);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
        intentCheckVehiclePlate.putExtra("image", bs.toByteArray());
        startActivity(intentCheckVehiclePlate);
        finish();
    }

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
