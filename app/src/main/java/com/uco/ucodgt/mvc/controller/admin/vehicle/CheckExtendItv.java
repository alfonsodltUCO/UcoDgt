package com.uco.ucodgt.mvc.controller.admin.vehicle;

import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckVehicle.isBetween;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.uco.ucodgt.mvc.controller.worker.penalty.CheckPenaltyToCancel;
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


/**
 * This activity is responsible for Admin that after check the data, allow Admin to check if the ITV (Inspection Technical Vehicle) for a vehicle needs to be extended.
 * It retrieves the vehicle information, checks if the current ITV is expired, and extends it if necessary.
 * @author Alfonso de la torre
 */
public class CheckExtendItv extends AppCompatActivity {

    ProgressBar progressBar;

    String licencePlate;


    /**
     * Called when the activity is starting. Responsible for initializing the activity,
     * retrieving vehicle information, checking if the ITV is expired, and extending it if necessary.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.loading);
        progressBar=findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();

        licencePlate=getIntent().getStringExtra("licencePlate");

        ManagerVehicle mngV=new ManagerVehicle();

        VehicleDTO vehicleToSend=new VehicleDTO();
        vehicleToSend.setLicencePlate(licencePlate);

        mngV.getVehicle(vehicleToSend, CheckExtendItv.this, new VehicleCallback() {
            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {
                ManagerClient mngC=new ManagerClient();
                mngC.getOwner(vehicle, CheckExtendItv.this, new UserCallback() {
                    @Override
                    public void onUserReceived(ClientDTO user) {
                        Date start,end;

                        start=vehicle.getValidItvFrom();
                        end=vehicle.getValidItvTo();

                        if(!isBetween(start,end)){//Check dates are correct (now between start and end)

                            Calendar calendar = Calendar.getInstance();

                            calendar.add(Calendar.YEAR, 1);

                            Date dateTo = calendar.getTime();

                            vehicle.setValidItvFrom(new Date());
                            vehicle.setValidItvTo(dateTo);

                            mngV.updateItv(vehicle,CheckExtendItv.this, new VehicleCallback() {

                                @Override
                                public void onVehicleReceived(VehicleDTO vehicle) {

                                    ManagerEmail mngE=new ManagerEmail();
                                    EmailDTO email=new EmailDTO(user.getEmail(),"Dear "+user.getName()+",\nThe Itv date of vehicle: "+vehicle.getLicencePlate()+" has been modified.\n"+
                                            "The new dates are, from: "+vehicle.getValidItvFrom().toString()+" to: "+vehicle.getValidItvTo().toString()+".\n"+
                                            "Remember to do not violate the rules of system and you will be rewarded.\n"+
                                            "Be safe, be smart, take care.\n"+
                                            "UcoDgt,","Itv date extend!");
                                    mngE.sendEmail(email, CheckExtendItv.this, new EmailCallback() {
                                        @Override
                                        public void onEmailSended(EmailDTO email) {
                                            Intent intent=new Intent(CheckExtendItv.this, AdminActivity.class);
                                            try {
                                                Thread.sleep(2*1000);
                                            }
                                            catch (Exception e) {
                                                System.out.println(e);
                                            }
                                            startActivity(intent);
                                            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                            finish();
                                            Toast.makeText(CheckExtendItv.this,"Itv modified successfully and email sent", Toast.LENGTH_LONG).show();

                                            hideLoading();
                                        }

                                        @Override
                                        public void onError(VolleyError error) {
                                            Intent intent=new Intent(CheckExtendItv.this, AdminActivity.class);
                                            try {
                                                Thread.sleep(2*1000);
                                            }
                                            catch (Exception e) {
                                                System.out.println(e);
                                            }
                                            startActivity(intent);
                                            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                            finish();
                                            Toast.makeText(CheckExtendItv.this,"Itv modified successfully", Toast.LENGTH_LONG).show();

                                            hideLoading();
                                        }
                                    });


                                }

                                @Override
                                public void onError(VolleyError error) {

                                    Intent intent=new Intent(CheckExtendItv.this, AdminActivity.class);
                                    try {
                                        Thread.sleep(2*1000);
                                    }
                                    catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    startActivity(intent);
                                    overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                    finish();
                                    Toast.makeText(CheckExtendItv.this,"An error has occurred try again please", Toast.LENGTH_LONG).show();

                                    hideLoading();
                                }

                                @Override
                                public void onVehiclesReceived(List<VehicleDTO> vehicles) {

                                }
                            });

                        }else{

                            Intent intent=new Intent(CheckExtendItv.this, AdminActivity.class);
                            try {
                                Thread.sleep(2*1000);
                            }
                            catch (Exception e) {
                                System.out.println(e);
                            }
                            startActivity(intent);
                            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                            finish();
                            Toast.makeText(CheckExtendItv.this,"The itv is not expired", Toast.LENGTH_LONG).show();

                            hideLoading();
                        }


                    }

                    @Override
                    public void onError(VolleyError error) {

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
