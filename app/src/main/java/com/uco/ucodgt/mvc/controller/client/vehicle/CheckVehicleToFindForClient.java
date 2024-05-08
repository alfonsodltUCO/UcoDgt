package com.uco.ucodgt.mvc.controller.client.vehicle;

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
import com.uco.ucodgt.mvc.view.client.ClientActivity;
import com.uco.ucodgt.mvc.view.client.vehicle.ShowVehicle;
/**
 * This activity is responsible for checking the details of a vehicle for a client.
 * @author Alfonso de la Torre
 */
public class CheckVehicleToFindForClient extends AppCompatActivity {
    private ProgressBar progressBar;
    String licenceplate;
    /**
     * Called when the activity is starting. Responsible for initializing the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down, this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.loading);
        progressBar=findViewById(com.uco.ucodgt.R.id.progressbar);
        String dni=getIntent().getStringExtra("dni");
        showLoading();
        licenceplate=getIntent().getStringExtra("licenceplate");

        if(!TextUtils.isEmpty(licenceplate)){

            ManagerVehicle mngV=new ManagerVehicle();
            VehicleDTO vehicle=new VehicleDTO(licenceplate,null,null,null,null,0);
            mngV.getVehicle(vehicle, CheckVehicleToFindForClient.this, new VehicleCallback() {
            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {

                Intent goShow=new Intent(CheckVehicleToFindForClient.this, ShowVehicle.class);
                try {
                    Thread.sleep(2*1000);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                goShow.putExtra("vehicle",vehicle);
                goShow.putExtra("dni",dni);
                startActivity(goShow);
                finish();
                hideLoading();
            }

            @Override
            public void onError(VolleyError error) {

                Intent goMain=new Intent(CheckVehicleToFindForClient.this, ClientActivity.class);
                Toast.makeText(CheckVehicleToFindForClient.this,"Not found the vehicle", Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(2*1000);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                goMain.putExtra("dni",dni);
                startActivity(goMain);
                finish();
                hideLoading();
            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehicles) {

            }
            });
        }
    }
    /**
     * Shows the loading progress bar.
     */
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    /**
     * Hides the loading progress bar.
     */
    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
