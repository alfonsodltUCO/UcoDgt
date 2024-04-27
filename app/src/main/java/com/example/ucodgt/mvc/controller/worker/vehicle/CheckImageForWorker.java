package com.example.ucodgt.mvc.controller.worker.vehicle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.List;
import java.util.Objects;

import com.example.ucodgt.mvc.model.business.vehicle.ManagerVehicle;
import com.example.ucodgt.mvc.model.business.vehicle.VehicleDTO;
import com.example.ucodgt.mvc.model.data.VehicleCallback;
import com.example.ucodgt.mvc.view.worker.WorkerActivity;
import com.example.ucodgt.mvc.view.worker.vehicle.ShowVehicle;


/**
 * An activity to check the image of a vehicle and display its information.
 * @author Alfonso de la torre
 */
public class CheckImageForWorker extends AppCompatActivity{
    ProgressBar progressBar;
    String numberWorker;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.example.ucodgt.R.layout.loading);

        progressBar=findViewById(com.example.ucodgt.R.id.progressbar);
        showLoading();
        Bitmap image = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("image"),0, Objects.requireNonNull(getIntent().getByteArrayExtra("image")).length);
        ManagerVehicle mngV=new ManagerVehicle();

        mngV.checkVehicle(image, CheckImageForWorker.this, new VehicleCallback() {
            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {

                runOnUiThread(()->{
                    hideLoading();
                    Intent goShow=new Intent(CheckImageForWorker.this, ShowVehicle.class);
                    goShow.putExtra("vehicle",vehicle);
                    goShow.putExtra("numberWorker",numberWorker);
                    startActivity(goShow);
                    finish();
                });
            }

            @Override
            public void onError(VolleyError error) {

                runOnUiThread(()->{

                    if(error.networkResponse.statusCode==404){

                        Intent goBack=new Intent(CheckImageForWorker.this, WorkerActivity.class);
                        Toast.makeText(CheckImageForWorker.this,"Not found this vehicle on Data Base", Toast.LENGTH_LONG).show();
                        goBack.putExtra("numberWorker",numberWorker);
                        startActivity(goBack);
                        hideLoading();
                        finish();

                    }else{
                        Intent goMain=new Intent(CheckImageForWorker.this, WorkerActivity.class);
                        Toast.makeText(CheckImageForWorker.this,"Not licence plate recognized", Toast.LENGTH_LONG).show();
                        goMain.putExtra("numberWorker",numberWorker);
                        startActivity(goMain);
                        hideLoading();
                        finish();

                    }
                });
            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehicles) {

            }
        });

    }


    /**
     * Show loading progress bar.
     */
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    /**
     * Hide loading progress bar.
     */
    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
