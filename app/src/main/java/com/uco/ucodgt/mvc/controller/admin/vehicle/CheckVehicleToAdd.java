package com.uco.ucodgt.mvc.controller.admin.vehicle;


import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkDni;

import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckVehicle.checkColor;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckVehicle.checkDates;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckVehicle.checkPlate;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckVehicle.checkType;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckVehicle.getTypeOf;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckVehicle.getTypeOfColor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.uco.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ManagerClient;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.model.business.vehicle.ManagerVehicle;
import com.uco.ucodgt.mvc.model.business.vehicle.VehicleDTO;
import com.uco.ucodgt.mvc.model.data.UserCallback;
import com.uco.ucodgt.mvc.model.data.VehicleCallback;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
import com.uco.ucodgt.mvc.view.admin.vehicle.AddVehicleActivity;
/**
 * An activity to check the vehicle details before adding it.
 * @author Alfonso de la torre
 */
public class CheckVehicleToAdd extends AppCompatActivity {
    String licenceplate,color,type,itvfrom,itvto,dni,insurance;

    private ProgressBar progressBar;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.loading);
        progressBar=findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();
        Intent intentReceived=getIntent();
        licenceplate=intentReceived.getStringExtra("licenceplate");
        color=intentReceived.getStringExtra("color");
        type=intentReceived.getStringExtra("cartype");
        itvfrom=intentReceived.getStringExtra("itvfrom");
        dni=intentReceived.getStringExtra("dni");
        insurance=intentReceived.getStringExtra("insurance");
        itvto=intentReceived.getStringExtra("itvto");


        if(!TextUtils.isEmpty(dni) && !TextUtils.isEmpty(licenceplate) && !TextUtils.isEmpty(color) && !TextUtils.isEmpty(type) && !TextUtils.isEmpty(itvfrom) && !TextUtils.isEmpty(itvto) && !TextUtils.isEmpty(insurance)){

            if(!checkDni(dni)){//Check DNI format

                Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                startActivity(intentAdmin);
                Toast.makeText(CheckVehicleToAdd.this,"No valid DNI", Toast.LENGTH_LONG).show();
                finish();
                hideLoading();

            }else{

                if(!checkPlate(licenceplate)){//Check plate format

                    Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                    startActivity(intentAdmin);
                    Toast.makeText(CheckVehicleToAdd.this,"No valid licence plate", Toast.LENGTH_LONG).show();
                    finish();
                    hideLoading();

                }else{

                    if(!checkDates(itvfrom,itvto)){//Check dates is set correctly

                        Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                        startActivity(intentAdmin);
                        Toast.makeText(CheckVehicleToAdd.this,"Dates must be first older than second\n And the format is yyyy-mm-dd", Toast.LENGTH_LONG).show();
                        finish();
                        hideLoading();

                    }else{

                        if(!checkColor(color)){//Check valid color for vehicle

                            Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                            startActivity(intentAdmin);
                            Toast.makeText(CheckVehicleToAdd.this,"please enter a valid color", Toast.LENGTH_LONG).show();
                            finish();
                            hideLoading();

                        }else{

                            if(!checkType(type)){//Check valid type for vehicle

                                Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                                startActivity(intentAdmin);
                                Toast.makeText(CheckVehicleToAdd.this,"please enter a valid type", Toast.LENGTH_LONG).show();
                                hideLoading();
                                finish();
                            }else{

                                ManagerClient mngcl=new ManagerClient();
                                ClientDTO cl=new ClientDTO(dni,null,null,null,null,null,null);
                                mngcl.getUser(cl,CheckVehicleToAdd.this,new UserCallback(){

                                    @Override
                                    public void onUserReceived(ClientDTO user) {

                                        runOnUiThread(()->{

                                            ManagerVehicle mngV=new ManagerVehicle();
                                            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                            try {
                                                VehicleDTO vh=new VehicleDTO(licenceplate,getTypeOf(type),getTypeOfColor(color),format.parse(itvfrom),format.parse(itvto),Integer.parseInt(insurance));
                                                mngV.addVehicle(vh,cl, CheckVehicleToAdd.this, new VehicleCallback() {
                                                    @Override
                                                    public void onVehicleReceived(VehicleDTO vehicle) {

                                                        Toast.makeText(CheckVehicleToAdd.this,"added", Toast.LENGTH_LONG).show();
                                                        Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AdminActivity.class);
                                                        startActivity(intentAdmin);
                                                        hideLoading();
                                                        finish();

                                                    }

                                                    @Override
                                                    public void onError(VolleyError error) {

                                                        if(error.networkResponse.statusCode==400){

                                                            Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                                                            startActivity(intentAdmin);
                                                            hideLoading();
                                                            Toast.makeText(CheckVehicleToAdd.this,"the vehicle already exists", Toast.LENGTH_LONG).show();
                                                            finish();

                                                        } else if (error.networkResponse.statusCode==404) {//The id introduces doesn't exist

                                                            Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                                                            startActivity(intentAdmin);
                                                            hideLoading();
                                                            Toast.makeText(CheckVehicleToAdd.this,"the insurance id doesnt exist", Toast.LENGTH_LONG).show();
                                                            finish();

                                                        }
                                                    }

                                                    @Override
                                                    public void onVehiclesReceived(List<VehicleDTO> vehicles) {

                                                    }
                                                });
                                            } catch (ParseException e) {
                                                throw new RuntimeException(e);
                                            }
                                        });
                                    }
                                    @Override
                                    public void onError(VolleyError error) {

                                        Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                                        startActivity(intentAdmin);
                                        Toast.makeText(CheckVehicleToAdd.this,"Client doesnt exist", Toast.LENGTH_LONG).show();
                                        hideLoading();
                                        finish();
                                    }
                                    @Override
                                    public void onWorkerReceived(WorkerDTO user) {}
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
                        }
                    }
                }
            }
        }else{

            Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
            startActivity(intentAdmin);
            Toast.makeText(CheckVehicleToAdd.this,"Please fill all fields", Toast.LENGTH_LONG).show();
            finish();
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
