package mvc.view.vehicle;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.ucodgt.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import mvc.controller.vehicle.CheckImage;

public class GetVehiclePlate extends AppCompatActivity {


    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_PICK = 102;
    private ImageView imageView;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<String> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_vehicle);

        imageView = findViewById(R.id.imageView);
        Button takePhotoButton = findViewById(R.id.takePhotoButton);
        Button pickPhotoButton = findViewById(R.id.pickPhotoButton);


        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        pickPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Initialize ActivityResultLauncher for camera
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        imageView.setImageBitmap(imageBitmap);
                        Intent intentCheckVehiclePlate=new Intent(GetVehiclePlate.this, CheckImage.class);

                        ByteArrayOutputStream bs = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                        intentCheckVehiclePlate.putExtra("image", bs.toByteArray());
                        startActivity(intentCheckVehiclePlate);
                        finish();
                    }
                }
            }
        });

        // Initialize ActivityResultLauncher for gallery
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if (result != null) {
                try {
                    Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), result);
                    imageView.setImageBitmap(selectedImage);
                    Intent intentCheckVehiclePlate=new Intent(GetVehiclePlate.this, CheckImage.class);
                    intentCheckVehiclePlate.putExtra("image", selectedImage);
                    startActivity(intentCheckVehiclePlate);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }
}
