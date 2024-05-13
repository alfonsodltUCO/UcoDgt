package com.uco.ucodgt.mvc.controller;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.VolleyError;
import com.uco.ucodgt.mvc.controller.admin.users.CheckUsersToList;
import com.uco.ucodgt.mvc.controller.client.user.CheckClientPoints;
import com.uco.ucodgt.mvc.controller.worker.vehicle.CheckImageForWorker;
import com.uco.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ManagerClient;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.model.data.UserCallback;
import com.uco.ucodgt.mvc.view.MainActivity;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
import com.uco.ucodgt.mvc.view.admin.user.AddUserActivity;
import com.uco.ucodgt.mvc.view.admin.user.DeleteUserActivity;
import com.uco.ucodgt.mvc.view.admin.user.FindUserActivity;
import com.uco.ucodgt.mvc.view.client.IntroduceRegisterData;
import com.uco.ucodgt.mvc.view.worker.vehicle.GetVehiclePlate;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * This activity check an image of a DNI to do the register of a client
 * @author Alfonso de la torre
 */
public class CheckDniImage extends AppCompatActivity {
    private ProgressBar progressBar;

    private ActivityResultLauncher<Intent> cameraLauncher;

    private static final int REQUEST_IMAGE_CAPTURE = 101;


    /**
     * Called when the activity is starting. This method creates the
     *        overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);activity.
     * It initializes the UI elements and retrieves data from the intent,
     * then performs a login check for the user.
     * @param savedInstanceState A Bundle containing the activity's previously saved state, if there was one.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.loading);

        progressBar = findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();
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
        openCamera();

    }


    /**
     * Opens the camera to capture an image if the necessary permission is granted.
     */
    private void openCamera() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(takePictureIntent);
        }
    }

    /**
     * Launches the activity to check the captured or selected image.
     *
     * @param bitmap The bitmap image to be checked.
     */
    private void launchCheckImageActivity(Bitmap bitmap) {

        ManagerClient mngC= new ManagerClient();

        mngC.checkDniImage(bitmap,this, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                mngC.getUser(user, CheckDniImage.this, new UserCallback() {
                    @Override
                    public void onUserReceived(ClientDTO user) {

                        Intent intentClient=new Intent(CheckDniImage.this, MainActivity.class);
                        startActivity(intentClient);
                        overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                        Toast.makeText(CheckDniImage.this,"This user is already register please Log In with your credentials\nIf you think is an error contact administrator please",Toast.LENGTH_LONG).show();
                        finish();
                        hideLoading();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        final String[] options = {"Yes", "No"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(CheckDniImage.this);
                        builder.setTitle("Is:"+ user.getDni()+" your DNI")
                                .setItems(options, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        switch (which) {
                                            case 0:
                                                Intent intentClient=new Intent(CheckDniImage.this, IntroduceRegisterData.class);
                                                intentClient.putExtra("dni",user.getDni());
                                                startActivity(intentClient);
                                                overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                finish();
                                                hideLoading();

                                                break;
                                            case 1:
                                                Intent intentDeleteUser=new Intent(CheckDniImage.this, MainActivity.class);
                                                startActivity(intentDeleteUser);
                                                Toast.makeText(CheckDniImage.this,"Introduce again the DNI please",Toast.LENGTH_LONG).show();
                                                overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);

                                                break;
                                        }
                                    }
                                });
                        builder.create().show();

                    }

                    @Override
                    public void onWorkerReceived(WorkerDTO user) {

                    }

                    @Override
                    public void onAdminReceived(AdminDTO user) {

                    }

                    @Override
                    public void onWorkersReceived(List<WorkerDTO> workers) {

                    }

                    @Override
                    public void onClientsReceived(List<ClientDTO> clients) {

                    }
                });
            }

            @Override
            public void onError(VolleyError error) {
                Intent intentClient=new Intent(CheckDniImage.this, MainActivity.class);
                startActivity(intentClient);
                overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                Toast.makeText(CheckDniImage.this,"Introduce the Dni again please with better light",Toast.LENGTH_LONG).show();
                finish();
                hideLoading();

            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {

            }

            @Override
            public void onAdminReceived(AdminDTO user) {

            }

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });

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
    /**
     * Shows the progress bar indicator.
     */
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }
    /**
     * Hides the progress bar indicator.
     */
    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
