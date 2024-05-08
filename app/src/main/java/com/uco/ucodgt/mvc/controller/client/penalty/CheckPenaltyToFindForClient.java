package com.uco.ucodgt.mvc.controller.client.penalty;


import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckPenalty.checkNumeric;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.List;

import com.uco.ucodgt.mvc.model.business.penalty.ManagerPenalty;
import com.uco.ucodgt.mvc.model.business.penalty.PenaltyDTO;
import com.uco.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.user.worker.ManagerWorker;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.model.data.PenaltyCallback;
import com.uco.ucodgt.mvc.model.data.UserCallback;
import com.uco.ucodgt.mvc.view.client.penalty.ShowPenalty;
import com.uco.ucodgt.mvc.view.client.ClientActivity;
/**
 * This class is responsible for checking a penalty associated with a client.
 * It retrieves penalty details and displays them to the client, along with the worker responsible for the penalty.
 * Author: Alfonso de la Torre
 */
public class CheckPenaltyToFindForClient extends AppCompatActivity {
    private ProgressBar progressBar;
    String id,dni;
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
        dni=getIntent().getStringExtra("dni");
        id=getIntent().getStringExtra("id");

        if(!TextUtils.isEmpty(id)){

            if(!checkNumeric(id)){//Check identifier is numeric

                Intent goMain=new Intent(CheckPenaltyToFindForClient.this, CheckPenaltiesToListForClient.class);
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

            }else{

                ManagerPenalty mngP=new ManagerPenalty();
                PenaltyDTO penalty=new PenaltyDTO(Integer.parseInt(id),null,null,null,null,null,dni,null,null,null,false,null,null);
                mngP.getPenalty(penalty, CheckPenaltyToFindForClient.this, new PenaltyCallback() {


                    @Override
                    public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                    }

                    @Override
                    public void onError(VolleyError error) {

                        Intent goMain=new Intent(CheckPenaltyToFindForClient.this, ClientActivity.class);
                        try {
                            Thread.sleep(2*1000);
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        }
                        Toast.makeText(CheckPenaltyToFindForClient.this,"Not found the penalty", Toast.LENGTH_LONG).show();
                        goMain.putExtra("dni",dni);
                        startActivity(goMain);
                        finish();
                        hideLoading();
                    }

                    @Override
                    public void onPenaltyReceived(PenaltyDTO penalty) {

                        ManagerWorker mngW=new ManagerWorker();

                        WorkerDTO worker=new WorkerDTO();
                        worker.setDni(penalty.getDniWorker());

                        mngW.getUser(worker, CheckPenaltyToFindForClient.this, new UserCallback() {
                            @Override
                            public void onUserReceived(ClientDTO user) {

                            }

                            @Override
                            public void onError(VolleyError error) {

                            }

                            @Override
                            public void onWorkerReceived(WorkerDTO user) {

                                Intent goShow=new Intent(CheckPenaltyToFindForClient.this, ShowPenalty.class);
                                try {
                                    Thread.sleep(2*1000);
                                }
                                catch (Exception e) {
                                    System.out.println(e);
                                }
                                goShow.putExtra("penalty",penalty);
                                goShow.putExtra("dni",dni);
                                goShow.putExtra("worker",user.getNumberOfWorker().toString());
                                startActivity(goShow);
                                finish();
                                hideLoading();
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


                });
            }

        }else{

            Intent goMain=new Intent(CheckPenaltyToFindForClient.this, ClientActivity.class);
            try {
                Thread.sleep(2*1000);
            }
            catch (Exception e) {
                System.out.println(e);
            }
            Toast.makeText(CheckPenaltyToFindForClient.this,"An error has ocurred", Toast.LENGTH_LONG).show();
            goMain.putExtra("dni",dni);
            startActivity(goMain);
            finish();
            hideLoading();
        }
    }
    /**
     * Shows the loading progress.
     */
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    /**
     * Hides the loading progress.
     */
    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
