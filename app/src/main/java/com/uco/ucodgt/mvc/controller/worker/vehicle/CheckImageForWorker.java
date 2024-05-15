package com.uco.ucodgt.mvc.controller.worker.vehicle;

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

import com.uco.ucodgt.mvc.model.business.vehicle.ManagerVehicle;
import com.uco.ucodgt.mvc.model.business.vehicle.VehicleDTO;
import com.uco.ucodgt.mvc.model.data.VehicleCallback;
import com.uco.ucodgt.mvc.view.worker.WorkerActivity;
import com.uco.ucodgt.mvc.view.worker.vehicle.ShowVehicle;


/**
 * An activity for worker to check the image of a vehicle and display its information.
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
        setContentView(com.uco.ucodgt.R.layout.loading);
        numberWorker=getIntent().getStringExtra("numberWorker");
        progressBar=findViewById(com.uco.ucodgt.R.id.progressbar);
        Bitmap image = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("image"),0, Objects.requireNonNull(getIntent().getByteArrayExtra("image")).length);
        ManagerVehicle mngV=new ManagerVehicle();
        showLoading();

        mngV.checkVehicle(image, CheckImageForWorker.this, new VehicleCallback() {
            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {

                runOnUiThread(()->{
                    hideLoading();
                    Intent goShow=new Intent(CheckImageForWorker.this, ShowVehicle.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    goShow.putExtra("vehicle",vehicle);
                    goShow.putExtra("numberWorker",numberWorker);
                    startActivity(goShow);
                    overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                    finish();
                });
            }

            @Override
            public void onError(VolleyError error) {

                runOnUiThread(()->{

                    if(error.networkResponse.statusCode==404){

                        Intent goBack=new Intent(CheckImageForWorker.this, WorkerActivity.class);
                        try {
                            Thread.sleep(2*1000);
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        }
                        Toast.makeText(CheckImageForWorker.this,"Not found this vehicle on Data Base", Toast.LENGTH_LONG).show();
                        goBack.putExtra("numberWorker",numberWorker);
                        startActivity(goBack);
                        overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                        hideLoading();
                        finish();


                    }else{
                        Intent goMain=new Intent(CheckImageForWorker.this, WorkerActivity.class);
                        try {
                            Thread.sleep(2*1000);
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        }
                        Toast.makeText(CheckImageForWorker.this,"Not licence plate recognized", Toast.LENGTH_LONG).show();
                        goMain.putExtra("numberWorker",numberWorker);
                        startActivity(goMain);
                        overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
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
