package com.uco.ucodgt.mvc.controller.admin.vehicle;

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
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
import com.uco.ucodgt.mvc.view.admin.vehicle.ShowVehicles;
/**
 * An activity that after check the data allow Admin to check the list of vehicles and display them.
 * @author Alfonso de la torre
 */
public class CheckVehiclesToList extends AppCompatActivity {
    ProgressBar progressBar;
    List<VehicleDTO> vehiclelist;
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
        String dni=getIntent().getStringExtra("dni");

        if(!TextUtils.isEmpty(dni)){
            // Check vehicles by DNI

            ManagerVehicle mngV=new ManagerVehicle();
            ClientDTO client=new ClientDTO(dni,null,null,null,null,null,null);
            mngV.getVehicles(client,CheckVehiclesToList.this, new VehicleCallback() {
                @Override
                public void onVehicleReceived(VehicleDTO vehicle) {

                }

                @Override
                public void onError(VolleyError error) {

                    Intent emptyLists = new Intent(CheckVehiclesToList.this, AdminActivity.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    startActivity(emptyLists);
                    Toast.makeText(CheckVehiclesToList.this,"Not found any vehicle",Toast.LENGTH_LONG).show();
                    hideLoading();

                }

                @Override
                public void onVehiclesReceived(List<VehicleDTO> vehicles) {

                    vehiclelist=vehicles;
                    Intent notEmptyLists = new Intent(CheckVehiclesToList.this, ShowVehicles.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    notEmptyLists.putExtra("vehicles", (Serializable) vehiclelist);
                    Toast.makeText(CheckVehiclesToList.this,"found vehicles",Toast.LENGTH_LONG).show();
                    startActivity(notEmptyLists);
                    hideLoading();

                }
            });
        }else{
            // Check all vehicles
            ManagerVehicle mngV=new ManagerVehicle();

            mngV.getVehicles(CheckVehiclesToList.this, new VehicleCallback() {
                @Override
                public void onVehicleReceived(VehicleDTO vehicle) {

                }

                @Override
                public void onError(VolleyError error) {

                    Intent emptyLists = new Intent(CheckVehiclesToList.this, AdminActivity.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    startActivity(emptyLists);
                    Toast.makeText(CheckVehiclesToList.this,"Not found any vehicle",Toast.LENGTH_LONG).show();
                    hideLoading();

                }

                @Override
                public void onVehiclesReceived(List<VehicleDTO> vehicles) {

                    vehiclelist=vehicles;
                    Intent notEmptyLists = new Intent(CheckVehiclesToList.this, ShowVehicles.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    notEmptyLists.putExtra("vehicles", (Serializable) vehiclelist);
                    Toast.makeText(CheckVehiclesToList.this,"found vehicles",Toast.LENGTH_LONG).show();
                    startActivity(notEmptyLists);
                    hideLoading();

                }
            });


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
