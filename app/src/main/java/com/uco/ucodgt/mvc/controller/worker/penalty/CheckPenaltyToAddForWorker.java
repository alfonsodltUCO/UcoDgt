package com.uco.ucodgt.mvc.controller.worker.penalty;

import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckListPenalty.checkPenalty;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckPenalty.checkPoints;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckPenalty.checkQuantity;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckPenalty.checkReasonOf;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckPenalty.checkStateOf;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkDni;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckVehicle.checkPlate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.uco.ucodgt.mvc.controller.admin.penalty.CheckPenaltyToAdd;
import com.uco.ucodgt.mvc.model.business.penalty.ManagerPenalty;
import com.uco.ucodgt.mvc.model.business.penalty.PenaltyDTO;
import com.uco.ucodgt.mvc.model.business.penalty.list.ListPenaltyDTO;
import com.uco.ucodgt.mvc.model.business.penalty.stateof;
import com.uco.ucodgt.mvc.model.business.penalty.typeof;
import com.uco.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ManagerClient;
import com.uco.ucodgt.mvc.model.business.user.worker.ManagerWorker;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.model.data.ListPenaltyCallback;
import com.uco.ucodgt.mvc.model.data.PenaltyCallback;
import com.uco.ucodgt.mvc.model.data.UserCallback;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
import com.uco.ucodgt.mvc.view.worker.WorkerActivity;
import com.uco.ucodgt.mvc.view.worker.penalty.AddPenaltyActivity;

/**
 * An activity that allow worker after check data to check penalty to add.
 * @author Alfonso de la torre
 */
public class CheckPenaltyToAddForWorker extends AppCompatActivity {

    private ProgressBar progressBar;
    String numberWorker;

    String dniC, state,reason,description, place, informed,locality,licenceplate,quantity,points;
    boolean val;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.loading);
        progressBar = findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();

        numberWorker=getIntent().getStringExtra("numberWorker");
        dniC=getIntent().getStringExtra("dniC");
        state=getIntent().getStringExtra("state");
        reason=getIntent().getStringExtra("reason");
        place=getIntent().getStringExtra("place");
        informed=getIntent().getStringExtra("informed");
        locality=getIntent().getStringExtra("locality");
        licenceplate=getIntent().getStringExtra("licenceplate");
        quantity=getIntent().getStringExtra("quantity");
        points=getIntent().getStringExtra("points");
        description=getIntent().getStringExtra("description");

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


        if(informed.equals("yes") || informed.equals("Yes") || informed.equals("YES")){
            val=true;
        }else if(informed.equals("no") || informed.equals("No") || informed.equals("NO")){
            val=false;
        }else{
            Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
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
            Toast.makeText(CheckPenaltyToAddForWorker.this,"Invalid value for informed at the moment\nMust be Yes/No", Toast.LENGTH_LONG).show();
            hideLoading();

        }

        if(!TextUtils.isEmpty(points) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(licenceplate) && !TextUtils.isEmpty(quantity) && !TextUtils.isEmpty(dniC) && !TextUtils.isEmpty(state) && !TextUtils.isEmpty(reason) && !TextUtils.isEmpty(place) && !TextUtils.isEmpty(informed) && !TextUtils.isEmpty(locality)){

            if(!checkDni(dniC)){

                Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
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
                Toast.makeText(CheckPenaltyToAddForWorker.this,"Invalid format DNI of client", Toast.LENGTH_LONG).show();
                hideLoading();

            }else{

                if(!checkStateOf(state)){

                    Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
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
                    Toast.makeText(CheckPenaltyToAddForWorker.this,"Invalid value for state", Toast.LENGTH_LONG).show();
                    hideLoading();

                }else{
                    if(!checkReasonOf(reason)){

                        Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
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
                        Toast.makeText(CheckPenaltyToAddForWorker.this,"Invalid format for reason", Toast.LENGTH_LONG).show();
                        hideLoading();


                    }else{
                        if(!checkPlate(licenceplate)){


                            Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
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
                            Toast.makeText(CheckPenaltyToAddForWorker.this,"Invalid format for licence plate", Toast.LENGTH_LONG).show();
                            hideLoading();


                        }else{

                            if(!checkPoints(points)){

                                Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
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
                                Toast.makeText(CheckPenaltyToAddForWorker.this,"Points must be a number\n", Toast.LENGTH_LONG).show();
                                hideLoading();


                            }else {

                                if(!checkQuantity(quantity)){//Check quantity is valid format


                                    Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
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
                                    Toast.makeText(CheckPenaltyToAddForWorker.this,"Quantity must be a number\n", Toast.LENGTH_LONG).show();
                                    hideLoading();


                                }else{

                                    ManagerWorker mngW=new ManagerWorker();
                                    WorkerDTO worker=new WorkerDTO(null,null,null,null,null,null,Integer.parseInt(numberWorker));

                                    mngW.getUserByNumber(worker,CheckPenaltyToAddForWorker.this, new UserCallback() {
                                        @Override
                                        public void onUserReceived(ClientDTO user) {

                                        }

                                        @Override
                                        public void onError(VolleyError error) {
                                        }

                                        @Override
                                        public void onWorkerReceived(WorkerDTO user) {
                                            PenaltyDTO penalty=new PenaltyDTO(null,Integer.parseInt(points),new Date(),Float.parseFloat(quantity), typeof.valueOf(reason), stateof.valueOf(state),dniC,user.getDni(),description,place,val,locality,licenceplate);

                                            checkPenalty(penalty,CheckPenaltyToAddForWorker.this, new ListPenaltyCallback() {
                                                @Override
                                                public void onError(VolleyError error) {

                                                }

                                                @Override
                                                public void onListReceived(ListPenaltyDTO listPenalty) {

                                                    if ((penalty.getPoints() >= listPenalty.getMinP() && penalty.getPoints() <= listPenalty.getMaxP()) &&
                                                            (penalty.getQuantity() >= listPenalty.getMinQ() && penalty.getQuantity() <= listPenalty.getMaxQ())) {
                                                        ManagerPenalty mngP=new ManagerPenalty();

                                                        mngP.addPenalty(penalty,CheckPenaltyToAddForWorker.this, new PenaltyCallback() {
                                                            @Override
                                                            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                                                            }

                                                            @Override
                                                            public void onError(VolleyError error) {

                                                                if(error.networkResponse.statusCode==404){

                                                                    Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
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
                                                                    Toast.makeText(CheckPenaltyToAddForWorker.this,"User doesnt have this vehicle", Toast.LENGTH_LONG).show();
                                                                    hideLoading();


                                                                }else if(error.networkResponse.statusCode==400){


                                                                    Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
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
                                                                    Toast.makeText(CheckPenaltyToAddForWorker.this,"Worker doesnt exist\nOr vehicle doesnt exist", Toast.LENGTH_LONG).show();
                                                                    hideLoading();


                                                                }else if(error.networkResponse.statusCode==422) {

                                                                    Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
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
                                                                    Toast.makeText(CheckPenaltyToAddForWorker.this, "Client doesnt exists", Toast.LENGTH_LONG).show();
                                                                    hideLoading();


                                                                }else{

                                                                    Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
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
                                                                    Toast.makeText(CheckPenaltyToAddForWorker.this,"An error has ocurred, check users and vehicle exist", Toast.LENGTH_LONG).show();
                                                                    hideLoading();

                                                                }
                                                            }

                                                            @Override
                                                            public void onPenaltyReceived(PenaltyDTO penalty) {
                                                                ManagerClient mngC=new ManagerClient();
                                                                ClientDTO cl=new ClientDTO(penalty.getDniClient(),null,null,null,null,null,null);
                                                                mngC.getUser(cl, CheckPenaltyToAddForWorker.this, new UserCallback() {
                                                                    @Override
                                                                    public void onUserReceived(ClientDTO user) {

                                                                        Intent intent= new Intent(Intent.ACTION_SEND);
                                                                        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{user.getEmail()});
                                                                        intent.putExtra(Intent.EXTRA_SUBJECT,"New penalty");
                                                                        intent.putExtra(Intent.EXTRA_TEXT,"User "+penalty.getDniClient()+",\nYou have received a new penalty.\n"+
                                                                                "The reason was: "+penalty.getReason().toString()+", the date of this penalty was imposed on: "+
                                                                                penalty.getDate().toString()+"\n."+"The quantity and the licence points for the penalty is: "+
                                                                                penalty.getPoints().toString()+", "+penalty.getQuantity().toString()+".\n"+
                                                                                "If you decide to pay it until 1 month a discount of 50% will be aplied. "+"For more information please visite the app.\n"+
                                                                                "Please remember to not violate the rules, you could hurt others and also yourself.\n"+
                                                                                "Be safe, be smart, take care.");
                                                                        intent.setType("message/rfc822");
                                                                        startActivity(Intent.createChooser(intent,"Choose email client:"));
                                                                        try {
                                                                            Thread.sleep(10*1000);
                                                                        }
                                                                        catch (Exception e) {
                                                                            System.out.println(e);
                                                                        }
                                                                        Intent intentAdmin=new Intent(CheckPenaltyToAddForWorker.this, AdminActivity.class);
                                                                        try {
                                                                            Thread.sleep(2*1000);
                                                                        }
                                                                        catch (Exception e) {
                                                                            System.out.println(e);
                                                                        }
                                                                        startActivity(intentAdmin);
                                                                        overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                                        finish();
                                                                        Toast.makeText(CheckPenaltyToAddForWorker.this,"Penalty added", Toast.LENGTH_LONG).show();
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
                                                    } else {

                                                        Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
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
                                                        Toast.makeText(CheckPenaltyToAddForWorker.this,"Points and quantity must be between the range of penalty reason", Toast.LENGTH_LONG).show();
                                                        hideLoading();

                                                    }
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
                        }
                    }
                }
            }
        }else{

            Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
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
            Toast.makeText(CheckPenaltyToAddForWorker.this,"Please fill all fields", Toast.LENGTH_LONG).show();
            hideLoading();

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
