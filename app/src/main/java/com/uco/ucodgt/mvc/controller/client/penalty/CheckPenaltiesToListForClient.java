package com.uco.ucodgt.mvc.controller.client.penalty;

import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckPenalty.checkDatesPenalties;

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

import com.uco.ucodgt.mvc.model.business.penalty.ManagerPenalty;
import com.uco.ucodgt.mvc.model.business.penalty.PenaltyDTO;
import com.uco.ucodgt.mvc.model.business.penalty.stateof;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.vehicle.VehicleDTO;
import com.uco.ucodgt.mvc.model.data.PenaltyCallback;
import com.uco.ucodgt.mvc.view.client.ClientActivity;
import com.uco.ucodgt.mvc.view.client.penalty.ShowPenalties;

/**
 * An activity that allow client after check data to check penalties and display them for his/her-self.
 * @author Alfonso de la torre
 */
public class CheckPenaltiesToListForClient extends AppCompatActivity {
    ProgressBar progressBar;
     String dni;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.loading);
        dni=getIntent().getStringExtra("dni");
        progressBar=findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();
        String state=getIntent().getStringExtra("state");
        String date1=getIntent().getStringExtra("date1");
        String date2=getIntent().getStringExtra("date2");
        String lic=getIntent().getStringExtra("licencePlate");
        String dni=getIntent().getStringExtra("dni");

        if(!TextUtils.isEmpty(lic)){// Check penalties by vehicle

            VehicleDTO vh=new VehicleDTO(lic,null,null,null,null,0);
            ManagerPenalty mngP=new ManagerPenalty();
            mngP.getPenalties(vh, CheckPenaltiesToListForClient.this, new PenaltyCallback() {

                @Override
                public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                    Intent goShow=new Intent(CheckPenaltiesToListForClient.this, ShowPenalties.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    goShow.putExtra("penalties",(Serializable) penalties);
                    goShow.putExtra("dni",dni);
                    startActivity(goShow);
                    hideLoading();

                }

                @Override
                public void onError(VolleyError error) {

                    Intent goMain=new Intent(CheckPenaltiesToListForClient.this, ClientActivity.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    Toast.makeText(CheckPenaltiesToListForClient.this,"Not found any penalty", Toast.LENGTH_LONG).show();
                    goMain.putExtra("dni",dni);
                    startActivity(goMain);
                    hideLoading();

                }

                @Override
                public void onPenaltyReceived(PenaltyDTO penalty) {

                }
            });

        }else{
            if((!TextUtils.isEmpty(date1)) && (!TextUtils.isEmpty(date2))){
                // Check penalties by date range

                ManagerPenalty mngP=new ManagerPenalty();

                if(!checkDatesPenalties(date1,date2)){

                    Intent goMain = new Intent(CheckPenaltiesToListForClient.this, ClientActivity.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    Toast.makeText(CheckPenaltiesToListForClient.this, "Dates must be yyyy-mm-dd\nStart mus be older than end", Toast.LENGTH_LONG).show();
                    startActivity(goMain);
                    hideLoading();

                }else{

                    PenaltyDTO penalty=new PenaltyDTO(null,null,null,null,null,null,dni,null,null,null,false,null,null);
                    mngP.getPenalties(date1,date2,penalty, CheckPenaltiesToListForClient.this, new PenaltyCallback() {

                        @Override
                        public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                            Intent goShow = new Intent(CheckPenaltiesToListForClient.this, ShowPenalties.class);
                            try {
                                Thread.sleep(2*1000);
                            }
                            catch (Exception e) {
                                System.out.println(e);
                            }
                            goShow.putExtra("penalties", (Serializable) penalties);
                            startActivity(goShow);
                            hideLoading();

                        }

                        @Override
                        public void onError(VolleyError error) {

                            Intent goMain = new Intent(CheckPenaltiesToListForClient.this, ClientActivity.class);
                            try {
                                Thread.sleep(2*1000);
                            }
                            catch (Exception e) {
                                System.out.println(e);
                            }
                            Toast.makeText(CheckPenaltiesToListForClient.this, "Not found any penalty", Toast.LENGTH_LONG).show();
                            startActivity(goMain);
                            hideLoading();

                        }

                        @Override
                        public void onPenaltyReceived(PenaltyDTO penalty) {

                        }
                    });
                }

            }else{
                if(!TextUtils.isEmpty(state)) {
                    // Check penalties by state

                    ManagerPenalty mngP=new ManagerPenalty();
                    PenaltyDTO penalty=new PenaltyDTO(null,null,null,null,null, stateof.valueOf(state),dni,null,null,null,false,null,null);
                    mngP.getPenalties(penalty, CheckPenaltiesToListForClient.this, new PenaltyCallback() {

                        @Override
                        public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                            Intent goShow = new Intent(CheckPenaltiesToListForClient.this, ShowPenalties.class);
                            try {
                                Thread.sleep(2*1000);
                            }
                            catch (Exception e) {
                                System.out.println(e);
                            }
                            goShow.putExtra("penalties", (Serializable) penalties);
                            goShow.putExtra("dni",dni);
                            startActivity(goShow);
                            hideLoading();

                        }

                        @Override
                        public void onError(VolleyError error) {

                            Intent goMain = new Intent(CheckPenaltiesToListForClient.this, ClientActivity.class);
                            try {
                                Thread.sleep(2*1000);
                            }
                            catch (Exception e) {
                                System.out.println(e);
                            }
                            Toast.makeText(CheckPenaltiesToListForClient.this, "Not found any penalty", Toast.LENGTH_LONG).show();
                            goMain.putExtra("dni",dni);
                            startActivity(goMain);
                            hideLoading();

                        }

                        @Override
                        public void onPenaltyReceived(PenaltyDTO penalty) {

                        }
                    });
                }else{
                    // Check penalties by client

                    ClientDTO client=new ClientDTO(dni,null,null,null,null,null,null);
                    ManagerPenalty mngP=new ManagerPenalty();
                    mngP.getPenalties(client, CheckPenaltiesToListForClient.this, new PenaltyCallback() {

                        @Override
                        public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                            Intent goShow = new Intent(CheckPenaltiesToListForClient.this, ShowPenalties.class);
                            try {
                                Thread.sleep(2*1000);
                            }
                            catch (Exception e) {
                                System.out.println(e);
                            }
                            goShow.putExtra("penalties", (Serializable) penalties);
                            goShow.putExtra("dni",dni);
                            startActivity(goShow);
                            hideLoading();

                        }

                        @Override
                        public void onError(VolleyError error) {

                            Intent goMain = new Intent(CheckPenaltiesToListForClient.this, ClientActivity.class);
                            Toast.makeText(CheckPenaltiesToListForClient.this, "Not found any penalty", Toast.LENGTH_LONG).show();
                            try {
                                Thread.sleep(2*1000);
                            }
                            catch (Exception e) {
                                System.out.println(e);
                            }
                            goMain.putExtra("dni",dni);
                            startActivity(goMain);
                            hideLoading();

                        }

                        @Override
                        public void onPenaltyReceived(PenaltyDTO penalty) {

                        }
                    });
                }

            }

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
