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

import com.uco.ucodgt.mvc.controller.admin.penalty.CheckPenaltyToAdd;
import com.uco.ucodgt.mvc.model.business.email.EmailDTO;
import com.uco.ucodgt.mvc.model.business.email.ManagerEmail;
import com.uco.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ManagerClient;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.model.business.vehicle.ManagerVehicle;
import com.uco.ucodgt.mvc.model.business.vehicle.VehicleDTO;
import com.uco.ucodgt.mvc.model.data.EmailCallback;
import com.uco.ucodgt.mvc.model.data.UserCallback;
import com.uco.ucodgt.mvc.model.data.VehicleCallback;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
import com.uco.ucodgt.mvc.view.admin.vehicle.AddVehicleActivity;
/**
 * An activity that after check the data allow Admin to check the vehicle details before adding it.
 * @author Alfonso de la torre
 */
public class CheckVehicleToAdd extends AppCompatActivity {
    String licenceplate,color,type,itvfrom,itvto,dni;

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
        itvto=intentReceived.getStringExtra("itvto");


        if(!TextUtils.isEmpty(dni) && !TextUtils.isEmpty(licenceplate) && !TextUtils.isEmpty(color) && !TextUtils.isEmpty(type) && !TextUtils.isEmpty(itvfrom) && !TextUtils.isEmpty(itvto)){

            if(!checkDni(dni)){//Check DNI format

                Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                try {
                    Thread.sleep(2*1000);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                startActivity(intentAdmin);
                overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                finish();
                Toast.makeText(CheckVehicleToAdd.this,"No valid DNI", Toast.LENGTH_LONG).show();

                hideLoading();

            }else{

                if(!checkPlate(licenceplate)){//Check plate format

                    Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    startActivity(intentAdmin);
                    overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                    finish();
                    Toast.makeText(CheckVehicleToAdd.this,"No valid licence plate", Toast.LENGTH_LONG).show();

                    hideLoading();

                }else{

                    if(!checkDates(itvfrom,itvto)){//Check dates is set correctly

                        Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                        try {
                            Thread.sleep(2*1000);
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        }
                        startActivity(intentAdmin);
                        overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                        finish();
                        Toast.makeText(CheckVehicleToAdd.this,"Dates must be first older than second\n And the format is yyyy-mm-dd", Toast.LENGTH_LONG).show();

                        hideLoading();

                    }else{

                        if(!checkColor(color)){//Check valid color for vehicle

                            Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                            try {
                                Thread.sleep(2*1000);
                            }
                            catch (Exception e) {
                                System.out.println(e);
                            }
                            startActivity(intentAdmin);
                            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                            finish();
                            Toast.makeText(CheckVehicleToAdd.this,"please enter a valid color", Toast.LENGTH_LONG).show();

                            hideLoading();

                        }else{

                            if(!checkType(type)){//Check valid type for vehicle

                                Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                                try {
                                    Thread.sleep(2*1000);
                                }
                                catch (Exception e) {
                                    System.out.println(e);
                                }
                                startActivity(intentAdmin);
                                overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                finish();
                                Toast.makeText(CheckVehicleToAdd.this,"please enter a valid type", Toast.LENGTH_LONG).show();
                                hideLoading();

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
                                                VehicleDTO vh=new VehicleDTO(licenceplate,getTypeOf(type),getTypeOfColor(color),format.parse(itvfrom),format.parse(itvto));
                                                mngV.addVehicle(vh,cl, CheckVehicleToAdd.this, new VehicleCallback() {
                                                    @Override
                                                    public void onVehicleReceived(VehicleDTO vehicle) {

                                                        ManagerEmail mngE=new ManagerEmail();
                                                        EmailDTO email=new EmailDTO(user.getEmail(),"Dear "+user.getName()+",\nThe vehicle: "+vehicle.getLicencePlate()+"has been introduced into the system.\n"+
                                                                "Remember to do not violate the rules of system and you will be rewarded.\n"+
                                                                "Be safe, be smart, take care.\n"+
                                                                "UcoDgt,","New vehicle added!");
                                                        mngE.sendEmail(email, CheckVehicleToAdd.this, new EmailCallback() {
                                                            @Override
                                                            public void onEmailSended(EmailDTO email) {

                                                                Toast.makeText(CheckVehicleToAdd.this,"Vehicle added and email sent", Toast.LENGTH_LONG).show();
                                                                try {
                                                                    Thread.sleep(2*1000);
                                                                }
                                                                catch (Exception e) {
                                                                    System.out.println(e);
                                                                }
                                                                Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AdminActivity.class);
                                                                startActivity(intentAdmin);
                                                                overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                                finish();
                                                                hideLoading();
                                                            }

                                                            @Override
                                                            public void onError(VolleyError error) {
                                                                Toast.makeText(CheckVehicleToAdd.this,"Vehicle added", Toast.LENGTH_LONG).show();
                                                                try {
                                                                    Thread.sleep(2*1000);
                                                                }
                                                                catch (Exception e) {
                                                                    System.out.println(e);
                                                                }
                                                                Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AdminActivity.class);
                                                                startActivity(intentAdmin);
                                                                overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                                finish();
                                                                hideLoading();
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onError(VolleyError error) {

                                                        if(error.networkResponse.statusCode==400){

                                                            Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                                                            try {
                                                                Thread.sleep(2*1000);
                                                            }
                                                            catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                            startActivity(intentAdmin);
                                                            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                            finish();
                                                            hideLoading();
                                                            Toast.makeText(CheckVehicleToAdd.this,"the vehicle already exists", Toast.LENGTH_LONG).show();


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
                                        try {
                                            Thread.sleep(2*1000);
                                        }
                                        catch (Exception e) {
                                            System.out.println(e);
                                        }
                                        startActivity(intentAdmin);
                                        overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                        finish();
                                        Toast.makeText(CheckVehicleToAdd.this,"Client doesnt exist", Toast.LENGTH_LONG).show();
                                        hideLoading();

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
            try {
                Thread.sleep(2*1000);
            }
            catch (Exception e) {
                System.out.println(e);
            }
            startActivity(intentAdmin);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
            finish();
            Toast.makeText(CheckVehicleToAdd.this,"Please fill all fields", Toast.LENGTH_LONG).show();

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
