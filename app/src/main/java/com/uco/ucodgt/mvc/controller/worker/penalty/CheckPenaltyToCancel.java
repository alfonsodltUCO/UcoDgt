package com.uco.ucodgt.mvc.controller.worker.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.uco.ucodgt.mvc.model.business.user.client.ManagerClient;
import com.uco.ucodgt.mvc.model.business.user.worker.ManagerWorker;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.model.data.PenaltyCallback;
import com.uco.ucodgt.mvc.model.data.UserCallback;
import com.uco.ucodgt.mvc.view.worker.WorkerActivity;


/**
 * This activity allow worker after check data for canceling a penalty associated with a client giving back points and money.
 * It communicates with the backend to cancel the penalty and then redirects the user back to the WorkerActivity.
 * @author Alfonso de la torre
 */
public class CheckPenaltyToCancel extends AppCompatActivity {

    String id,numberWorker,points,dniClient;

    private ProgressBar progressBar;


    /**
     * Called when the activity is starting. Responsible for initializing the activity,
     * retrieving necessary data from the intent, canceling the penalty, and handling the response.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.loading);
        progressBar = findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();

        id=getIntent().getStringExtra("id");
        numberWorker=getIntent().getStringExtra("numberWorker");
        points=getIntent().getStringExtra("points");
        dniClient=getIntent().getStringExtra("dni");

        ManagerPenalty mngP=new ManagerPenalty();

        PenaltyDTO penaltyToFind=new PenaltyDTO(Integer.parseInt(id),null,null,null,null,null,null,null,null,null,false,null,null);

        mngP.getPenalty(penaltyToFind, CheckPenaltyToCancel.this, new PenaltyCallback() {
            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

            }

            @Override
            public void onError(VolleyError error) {

                Intent intent=new Intent(CheckPenaltyToCancel.this, WorkerActivity.class);
                try {
                    Thread.sleep(2*1000);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                intent.putExtra("numberWorker",numberWorker);
                startActivity(intent);
                overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                finish();
                Toast.makeText(CheckPenaltyToCancel.this,"An error has happened, contact the administrator please", Toast.LENGTH_LONG).show();
                hideLoading();

            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {
                if(penalty.getState().toString().equals("cancelled")){

                    Intent intent=new Intent(CheckPenaltyToCancel.this, WorkerActivity.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    intent.putExtra("numberWorker",numberWorker);
                    startActivity(intent);
                    overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                    finish();
                    Toast.makeText(CheckPenaltyToCancel.this,"The penalty is already cancelled", Toast.LENGTH_LONG).show();
                    hideLoading();

                }else{

                    ManagerWorker mngW=new ManagerWorker();
                    WorkerDTO wrk=new WorkerDTO(null,null,null,null,null,null,Integer.parseInt(numberWorker));

                    mngW.getUserByNumber(wrk, CheckPenaltyToCancel.this, new UserCallback() {
                        @Override
                        public void onUserReceived(ClientDTO user) {

                        }

                        @Override
                        public void onError(VolleyError error) {
                            Intent intent=new Intent(CheckPenaltyToCancel.this, WorkerActivity.class);
                            try {
                                Thread.sleep(2*1000);
                            }
                            catch (Exception e) {
                                System.out.println(e);
                            }
                            intent.putExtra("numberWorker",numberWorker);
                            startActivity(intent);
                            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                            finish();
                            Toast.makeText(CheckPenaltyToCancel.this,"An error has happened, contact the administrator please", Toast.LENGTH_LONG).show();
                            hideLoading();

                        }

                        @Override
                        public void onWorkerReceived(WorkerDTO user) {
                            PenaltyDTO penaltyToSend=new PenaltyDTO(Integer.parseInt(id),Integer.parseInt(points),null,Float.parseFloat("0"),null,null,dniClient,user.getDni(),null,null,false,null,null);

                            mngP.cancelPenalty(penaltyToSend,CheckPenaltyToCancel.this, new PenaltyCallback() {
                                @Override
                                public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                                }

                                @Override
                                public void onError(VolleyError error) {

                                    if(error.networkResponse.statusCode==405){

                                        Intent intent=new Intent(CheckPenaltyToCancel.this, WorkerActivity.class);
                                        try {
                                            Thread.sleep(2*1000);
                                        }
                                        catch (Exception e) {
                                            System.out.println(e);
                                        }
                                        intent.putExtra("numberWorker",numberWorker);
                                        startActivity(intent);
                                        overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                        finish();
                                        Toast.makeText(CheckPenaltyToCancel.this,"This penalty was not imposed by you", Toast.LENGTH_LONG).show();
                                        hideLoading();


                                    }else{

                                        Intent intent=new Intent(CheckPenaltyToCancel.this, WorkerActivity.class);
                                        try {
                                            Thread.sleep(2*1000);
                                        }
                                        catch (Exception e) {
                                            System.out.println(e);
                                        }
                                        intent.putExtra("numberWorker",numberWorker);
                                        startActivity(intent);
                                        overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                        finish();
                                        Toast.makeText(CheckPenaltyToCancel.this,"An error has happened, contact the administrator please", Toast.LENGTH_LONG).show();
                                        hideLoading();

                                    }
                                }

                                @Override
                                public void onPenaltyReceived(PenaltyDTO penalty) {
                                    ManagerClient mngC=new ManagerClient();
                                    ClientDTO cl=new ClientDTO(penalty.getDniClient(),null,null,null,null,null,null);
                                    mngC.getUser(cl, CheckPenaltyToCancel.this, new UserCallback() {

                                        @Override
                                        public void onUserReceived(ClientDTO user) {

                                            Intent intentE= new Intent(Intent.ACTION_SEND);
                                            intentE.putExtra(Intent.EXTRA_EMAIL,new String[]{user.getEmail()});
                                            intentE.putExtra(Intent.EXTRA_SUBJECT,"Penalty cancelled!");
                                            intentE.putExtra(Intent.EXTRA_TEXT,"User "+user.getDni()+",\nThe penalty "+penalty.getId()+"have been removed, sorry for this error.\n"+
                                                    "The reason was: "+penalty.getReason().toString()+", the date of this penalty was imposed on: "+
                                                    penalty.getDate().toString()+"\n."+"The quantity and the licence points for the penalty is: "+
                                                    penalty.getPoints().toString()+", "+penalty.getQuantity().toString()+".\n"+
                                                    "Just for remember that the points will be give back to you and the quantity will not have to be paid." +
                                                    "UcoDgt,");
                                            intentE.setType("message/rfc822");
                                            startActivity(Intent.createChooser(intentE,"Choose email client:"));
                                            Intent intent=new Intent(CheckPenaltyToCancel.this, WorkerActivity.class);
                                            try {
                                                Thread.sleep(2*1000);
                                            }
                                            catch (Exception e) {
                                                System.out.println(e);
                                            }
                                            intent.putExtra("numberWorker",numberWorker);
                                            startActivity(intent);
                                            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                            finish();
                                            Toast.makeText(CheckPenaltyToCancel.this,"Penalty was cancelled", Toast.LENGTH_LONG).show();
                                            hideLoading();
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
                            });
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
