package com.uco.ucodgt.mvc.controller.client.vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.io.Serializable;
import java.util.List;

import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.vehicle.ManagerVehicle;
import com.uco.ucodgt.mvc.model.business.vehicle.VehicleDTO;
import com.uco.ucodgt.mvc.model.data.VehicleCallback;
import com.uco.ucodgt.mvc.view.client.ClientActivity;
import com.uco.ucodgt.mvc.view.client.vehicle.ShowVehicles;

/**
 * Method used by client to handle the view the list of client's vehicles
 * @author Alfonso de la Torre
 */

public class CheckVehiclesToListForClient extends AppCompatActivity  {

    ProgressBar progressBar;
    List<VehicleDTO> vehiclelist;
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
        showLoading();
        String dni=getIntent().getStringExtra("dni");

        if(!TextUtils.isEmpty(dni)){
            // Retrieve client's vehicles from ManagerVehicle

            ManagerVehicle mngV=new ManagerVehicle();
            ClientDTO client=new ClientDTO(dni,null,null,null,null,null,null);
            mngV.getVehicles(client, CheckVehiclesToListForClient.this, new VehicleCallback() {
                @Override
                public void onVehicleReceived(VehicleDTO vehicle) {

                }

                @Override
                public void onError(VolleyError error) {

                    Intent emptyLists = new Intent(CheckVehiclesToListForClient.this, ClientActivity.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    emptyLists.putExtra("dni",dni);
                    startActivity(emptyLists);
                    overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                    finish();
                    Toast.makeText(CheckVehiclesToListForClient.this,"Not found any vehicle",Toast.LENGTH_LONG).show();
                    hideLoading();

                }

                @Override
                public void onVehiclesReceived(List<VehicleDTO> vehicles) {

                    // If vehicles received, display them in ShowVehicles activity
                    vehiclelist=vehicles;
                    Intent notEmptyLists = new Intent(CheckVehiclesToListForClient.this, ShowVehicles.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    notEmptyLists.putExtra("vehicles", (Serializable) vehiclelist);
                    Toast.makeText(CheckVehiclesToListForClient.this,"found vehicles",Toast.LENGTH_LONG).show();
                    notEmptyLists.putExtra("dni",dni);
                    startActivity(notEmptyLists);
                    overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                    finish();
                    hideLoading();

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
