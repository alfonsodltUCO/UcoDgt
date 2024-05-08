package com.uco.ucodgt.mvc.controller.worker.penalty;

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
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.vehicle.VehicleDTO;
import com.uco.ucodgt.mvc.model.data.PenaltyCallback;
import com.uco.ucodgt.mvc.view.worker.WorkerActivity;
import com.uco.ucodgt.mvc.view.worker.penalty.ShowPenalties;

/**
 * An activity to check penalties to list.
 * @author Alfonso de la torre
 */
public class CheckPenaltiesToListForWorker extends AppCompatActivity {
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
        progressBar=findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();

        numberWorker=getIntent().getStringExtra("numberWorker");
        String lic=getIntent().getStringExtra("licencePlate");
        String dni=getIntent().getStringExtra("dni");

        if(!TextUtils.isEmpty(lic)){//List penalties by licence plate

            VehicleDTO vh=new VehicleDTO(lic,null,null,null,null,0);
            ManagerPenalty mngP=new ManagerPenalty();

            mngP.getPenalties(vh, CheckPenaltiesToListForWorker.this, new PenaltyCallback() {

                @Override
                public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                    Intent goShow=new Intent(CheckPenaltiesToListForWorker.this, ShowPenalties.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    goShow.putExtra("penalties",(Serializable) penalties);
                    goShow.putExtra("numberWorker",numberWorker);
                    startActivity(goShow);
                    hideLoading();

                }

                @Override
                public void onError(VolleyError error) {

                    Intent goMain=new Intent(CheckPenaltiesToListForWorker.this, WorkerActivity.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    goMain.putExtra("numberWorker",numberWorker);
                    Toast.makeText(CheckPenaltiesToListForWorker.this,"Not found any penalty", Toast.LENGTH_LONG).show();
                    startActivity(goMain);
                    hideLoading();

                }

                @Override
                public void onPenaltyReceived(PenaltyDTO penalty) {

                }
            });

        } else if (!TextUtils.isEmpty(dni)) {//List penalties by DNI

            ClientDTO client=new ClientDTO(dni,null,null,null,null,null,null);
            ManagerPenalty mngP=new ManagerPenalty();
            mngP.getPenalties(client, CheckPenaltiesToListForWorker.this, new PenaltyCallback() {

                @Override
                public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                    Intent goShow = new Intent(CheckPenaltiesToListForWorker.this, ShowPenalties.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    goShow.putExtra("numberWorker",numberWorker);
                    goShow.putExtra("penalties", (Serializable) penalties);
                    startActivity(goShow);
                    hideLoading();

                }

                @Override
                public void onError(VolleyError error) {

                    Intent goMain = new Intent(CheckPenaltiesToListForWorker.this, WorkerActivity.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    goMain.putExtra("numberWorker",numberWorker);
                    Toast.makeText(CheckPenaltiesToListForWorker.this, "Not found any penalty", Toast.LENGTH_LONG).show();
                    startActivity(goMain);
                    hideLoading();

                }

                @Override
                public void onPenaltyReceived(PenaltyDTO penalty) {

                }
            });
        }else{
            Intent goMain = new Intent(CheckPenaltiesToListForWorker.this, WorkerActivity.class);
            try {
                Thread.sleep(2*1000);
            }
            catch (Exception e) {
                System.out.println(e);
            }
            goMain.putExtra("numberWorker",numberWorker);
            Toast.makeText(CheckPenaltiesToListForWorker.this, "An error has occurred try again please", Toast.LENGTH_LONG).show();
            startActivity(goMain);
            hideLoading();

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
