package com.uco.ucodgt.mvc.controller.worker.vehicle;

import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckVehicle.checkPlate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.List;

import com.uco.ucodgt.mvc.model.business.vehicle.ManagerVehicle;
import com.uco.ucodgt.mvc.model.business.vehicle.VehicleDTO;
import com.uco.ucodgt.mvc.model.data.VehicleCallback;
import com.uco.ucodgt.mvc.view.worker.WorkerActivity;
import com.uco.ucodgt.mvc.view.worker.vehicle.IntroduceManual;
import com.uco.ucodgt.mvc.view.worker.vehicle.ShowVehicle;

/**
 * An activity for worker to check the validity of a vehicle's license plate before attempting to find it in the database.
 * @author Alfosno de la torre
 */
public class CheckVehicleToFindForWorker extends AppCompatActivity {
    private ProgressBar progressBar;
    String licenceplate;
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
        progressBar=findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();
        numberWorker=getIntent().getStringExtra("numberWorker");
        licenceplate=getIntent().getStringExtra("licenceplate");

        if(!TextUtils.isEmpty(licenceplate)){

            if(!checkPlate(licenceplate)){

                Intent intent=new Intent(CheckVehicleToFindForWorker.this, IntroduceManual.class);
                try {
                    Thread.sleep(2*1000);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                intent.putExtra("numberWorker",numberWorker);
                startActivity(intent);
                Toast.makeText(CheckVehicleToFindForWorker.this,"No valid licence plate", Toast.LENGTH_LONG).show();


            }else{

                ManagerVehicle mngV=new ManagerVehicle();
                VehicleDTO vehicle=new VehicleDTO(licenceplate,null,null,null,null,0);

                mngV.getVehicle(vehicle, CheckVehicleToFindForWorker.this, new VehicleCallback() {
                    @Override
                    public void onVehicleReceived(VehicleDTO vehicle) {

                        Intent goShow=new Intent(CheckVehicleToFindForWorker.this, ShowVehicle.class);
                        try {
                            Thread.sleep(2*1000);
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        }
                        goShow.putExtra("vehicle",vehicle);
                        goShow.putExtra("numberWorker",numberWorker);
                        startActivity(goShow);

                        hideLoading();

                    }

                    @Override
                    public void onError(VolleyError error) {

                        Intent goMain=new Intent(CheckVehicleToFindForWorker.this, WorkerActivity.class);
                        try {
                            Thread.sleep(2*1000);
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        }
                        goMain.putExtra("numberWorker",numberWorker);
                        Toast.makeText(CheckVehicleToFindForWorker.this,"Not found the vehicle", Toast.LENGTH_LONG).show();
                        startActivity(goMain);

                        hideLoading();

                    }

                    @Override
                    public void onVehiclesReceived(List<VehicleDTO> vehicles) {

                    }
                });
            }
        }else{

            Intent intent=new Intent(CheckVehicleToFindForWorker.this, IntroduceManual.class);
            try {
                Thread.sleep(2*1000);
            }
            catch (Exception e) {
                System.out.println(e);
            }
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            Toast.makeText(CheckVehicleToFindForWorker.this,"Fill the field please", Toast.LENGTH_LONG).show();

        }

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
