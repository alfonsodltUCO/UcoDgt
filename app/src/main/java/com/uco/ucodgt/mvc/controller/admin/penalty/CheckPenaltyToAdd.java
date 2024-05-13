package com.uco.ucodgt.mvc.controller.admin.penalty;

import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckListPenalty.checkPenalty;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckPenalty.checkDate;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.uco.ucodgt.mvc.model.business.penalty.ManagerPenalty;
import com.uco.ucodgt.mvc.model.business.penalty.PenaltyDTO;
import com.uco.ucodgt.mvc.model.business.penalty.list.ListPenaltyDTO;
import com.uco.ucodgt.mvc.model.business.penalty.stateof;
import com.uco.ucodgt.mvc.model.business.penalty.typeof;
import com.uco.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ManagerClient;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.model.data.ListPenaltyCallback;
import com.uco.ucodgt.mvc.model.data.PenaltyCallback;
import com.uco.ucodgt.mvc.model.data.UserCallback;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
import com.uco.ucodgt.mvc.view.admin.penalty.AddPenaltyActivity;

/**
 * An activity that after check the data allow Admin to add a penalty for a client.
 * The data introduced will be checked to maintain the system correctly.
 * @author Alfonso de la torre
 */
public class CheckPenaltyToAdd extends AppCompatActivity {
    private ProgressBar progressBar;

    String dniC,dniW,date, state,reason,description, place, informed,locality,licenceplate,quantity,points;
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

        date=getIntent().getStringExtra("date");
        dniC=getIntent().getStringExtra("dniC");
        dniW=getIntent().getStringExtra("dniW");
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
        Date dateOfPenalty;
        try {
            dateOfPenalty = format.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if(informed.equals("yes") || informed.equals("Yes") || informed.equals("YES")){
            val=true;
        }else if(informed.equals("no") || informed.equals("No") || informed.equals("NO")){
            val=false;
        }else{
            Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
            try {
                Thread.sleep(2*1000);
            }
            catch (Exception e) {
                System.out.println(e);
            }
            startActivity(intentAdmin);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
            finish();
            Toast.makeText(CheckPenaltyToAdd.this,"Invalid value for informed at the moment\nMust be Yes/No", Toast.LENGTH_LONG).show();
            hideLoading();

        }

        if(!TextUtils.isEmpty(points) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(licenceplate) && !TextUtils.isEmpty(quantity) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(dniC) && !TextUtils.isEmpty(dniW) && !TextUtils.isEmpty(state) && !TextUtils.isEmpty(reason) && !TextUtils.isEmpty(place) && !TextUtils.isEmpty(informed) && !TextUtils.isEmpty(locality)){
            if(!checkDate(date)){//Check if date given is today
                Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                try {
                    Thread.sleep(2*1000);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                startActivity(intentAdmin);
                overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                finish();
                Toast.makeText(CheckPenaltyToAdd.this,"Invalid date\nMust be today", Toast.LENGTH_LONG).show();
                hideLoading();

            }else{
                if(!checkDni(dniC)){//Check DNI format of Client
                    Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                    try {
                        Thread.sleep(2*1000);
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    startActivity(intentAdmin);
                    overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                    finish();
                    Toast.makeText(CheckPenaltyToAdd.this,"Invalid format DNI of client", Toast.LENGTH_LONG).show();
                    hideLoading();

                }else{
                    if(!checkDni(dniW)){//Check DNI format of Worker

                        Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                        try {
                            Thread.sleep(2*1000);
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        }
                        startActivity(intentAdmin);
                        overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                        finish();
                        Toast.makeText(CheckPenaltyToAdd.this,"Invalid format DNI of worker", Toast.LENGTH_LONG).show();
                        hideLoading();


                    }else{


                        if(!checkStateOf(state)){//Check State of penalty (paid,processed,cancelled)
                            Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                            try {
                                Thread.sleep(2*1000);
                            }
                            catch (Exception e) {
                                System.out.println(e);
                            }
                            startActivity(intentAdmin);
                            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                            finish();
                            Toast.makeText(CheckPenaltyToAdd.this,"Invalid value for state", Toast.LENGTH_LONG).show();
                            hideLoading();


                        }else{

                            if(!checkReasonOf(reason)){//Check Reason of penalty
                                Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                                try {
                                    Thread.sleep(2*1000);
                                }
                                catch (Exception e) {
                                    System.out.println(e);
                                }
                                startActivity(intentAdmin);
                                overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                finish();
                                Toast.makeText(CheckPenaltyToAdd.this,"Invalid format for reason", Toast.LENGTH_LONG).show();
                                hideLoading();


                            }else{

                                if(!checkPlate(licenceplate)){//Check format of licence plate

                                    Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                                    try {
                                        Thread.sleep(2*1000);
                                    }
                                    catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    startActivity(intentAdmin);
                                    overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                    finish();
                                    Toast.makeText(CheckPenaltyToAdd.this,"Invalid format for licence plate", Toast.LENGTH_LONG).show();
                                    hideLoading();


                                }else{

                                    if(!checkPoints(points)){//Check format of licence points

                                        Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                                        try {
                                            Thread.sleep(2*1000);
                                        }
                                        catch (Exception e) {
                                            System.out.println(e);
                                        }
                                        startActivity(intentAdmin);
                                        overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                        finish();
                                        Toast.makeText(CheckPenaltyToAdd.this,"Points must be a number\n", Toast.LENGTH_LONG).show();
                                        hideLoading();


                                    }else {

                                        if(!checkQuantity(quantity)){//Check format and value of quantity

                                            Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                                            try {
                                                Thread.sleep(2*1000);
                                            }
                                            catch (Exception e) {
                                                System.out.println(e);
                                            }
                                            startActivity(intentAdmin);
                                            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                            finish();
                                            Toast.makeText(CheckPenaltyToAdd.this,"Quantity must be a number\n", Toast.LENGTH_LONG).show();
                                            hideLoading();


                                        }else{

                                            PenaltyDTO penalty=new PenaltyDTO(null,Integer.parseInt(points),dateOfPenalty,Float.parseFloat(quantity), typeof.valueOf(reason), stateof.valueOf(state),dniC,dniW,description,place,val,locality,licenceplate);

                                            checkPenalty(penalty,CheckPenaltyToAdd.this, new ListPenaltyCallback() {
                                                @Override
                                                public void onError(VolleyError error) {

                                                }

                                                @Override
                                                public void onListReceived(ListPenaltyDTO listPenalty) {

                                                    if ((penalty.getPoints() >= listPenalty.getMinP() && penalty.getPoints() <= listPenalty.getMaxP()) &&
                                                            (penalty.getQuantity() >= listPenalty.getMinQ() && penalty.getQuantity() <= listPenalty.getMaxQ())) {//Check points and quantity is between the range for this type of penalty
                                                        ManagerPenalty mngP=new ManagerPenalty();

                                                        mngP.addPenalty(penalty,CheckPenaltyToAdd.this, new PenaltyCallback() {
                                                            @Override
                                                            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                                                            }

                                                            @Override
                                                            public void onError(VolleyError error) {

                                                                if(error.networkResponse.statusCode==404){//Client doesn't have the vehicle introduced
                                                                    Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                                                                    try {
                                                                        Thread.sleep(2*1000);
                                                                    }
                                                                    catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                    startActivity(intentAdmin);
                                                                    overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                                    finish();
                                                                    Toast.makeText(CheckPenaltyToAdd.this,"User doesnt have this vehicle", Toast.LENGTH_LONG).show();
                                                                    hideLoading();


                                                                }else if(error.networkResponse.statusCode==400){//The worker or the vehicle doesn't exist

                                                                    Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                                                                    try {
                                                                        Thread.sleep(2*1000);
                                                                    }
                                                                    catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                    startActivity(intentAdmin);
                                                                    overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                                    finish();
                                                                    Toast.makeText(CheckPenaltyToAdd.this,"Worker doesnt exist\nOr vehicle doesnt exist", Toast.LENGTH_LONG).show();
                                                                    hideLoading();


                                                                }else if(error.networkResponse.statusCode==422) {//Client doesn't exist

                                                                    Intent intentAdmin = new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                                                                    try {
                                                                        Thread.sleep(2*1000);
                                                                    }
                                                                    catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                    startActivity(intentAdmin);
                                                                    overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                                    finish();
                                                                    Toast.makeText(CheckPenaltyToAdd.this, "Client doesnt exists", Toast.LENGTH_LONG).show();
                                                                    hideLoading();


                                                                }else{

                                                                    Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                                                                    try {
                                                                        Thread.sleep(2*1000);
                                                                    }
                                                                    catch (Exception e) {
                                                                        System.out.println(e);
                                                                    }
                                                                    startActivity(intentAdmin);
                                                                    overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                                    finish();
                                                                    Toast.makeText(CheckPenaltyToAdd.this,"An error has ocurred, check users and vehicle exist", Toast.LENGTH_LONG).show();
                                                                    hideLoading();

                                                                }
                                                            }

                                                            @Override
                                                            public void onPenaltyReceived(PenaltyDTO penalty) {
                                                                ManagerClient mngC=new ManagerClient();
                                                                ClientDTO cl=new ClientDTO(penalty.getDniClient(),null,null,null,null,null,null);
                                                                mngC.getUser(cl, CheckPenaltyToAdd.this, new UserCallback() {
                                                                    @Override
                                                                    public void onUserReceived(ClientDTO user) {

                                                                        Intent i = new Intent(Intent.ACTION_SEND);
                                                                        i.setType("message/rfc822");
                                                                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{user.getEmail()});
                                                                        i.putExtra(Intent.EXTRA_SUBJECT, "New penalty");
                                                                        i.putExtra(Intent.EXTRA_TEXT   , "User "+penalty.getDniClient()+",\nYou have received a new penalty.\n"+
                                                                                "The reason was: "+penalty.getReason().toString()+", the date of this penalty was imposed on: "+
                                                                                penalty.getDate().toString()+"\n."+"The quantity and the licence points for the penalty is: "+
                                                                                penalty.getPoints().toString()+", "+penalty.getQuantity().toString()+".\n"+
                                                                                "If you decide to pay it until 1 month a discount of 50% will be aplied. "+"For more information please visite the app.\n"+
                                                                                "Please remember to not violate the rules, you could hurt others and also yourself.\n"+
                                                                                "Be safe, be smart, take care.");
                                                                        try {
                                                                            startActivity(Intent.createChooser(i, "Send mail..."));
                                                                        } catch (android.content.ActivityNotFoundException ex) {
                                                                            Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AdminActivity.class);
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
                                                                            Toast.makeText(CheckPenaltyToAdd.this, "Penalty added, but no email was sent.There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AdminActivity.class);
                                                                        try {
                                                                            Thread.sleep(2*1000);
                                                                        }
                                                                        catch (Exception e) {
                                                                            System.out.println(e);
                                                                        }
                                                                        startActivity(intentAdmin);
                                                                        overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                                        finish();
                                                                        Toast.makeText(CheckPenaltyToAdd.this,"Penalty added", Toast.LENGTH_LONG).show();
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

                                                        Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
                                                        try {
                                                            Thread.sleep(2*1000);
                                                        }
                                                        catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                        startActivity(intentAdmin);
                                                        overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                        finish();
                                                        Toast.makeText(CheckPenaltyToAdd.this,"Points and quantity must be between the range of penalty reason", Toast.LENGTH_LONG).show();
                                                        hideLoading();

                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }else{

            Intent intentAdmin=new Intent(CheckPenaltyToAdd.this, AddPenaltyActivity.class);
            try {
                Thread.sleep(2*1000);
            }
            catch (Exception e) {
                System.out.println(e);
            }
            startActivity(intentAdmin);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
            finish();
            Toast.makeText(CheckPenaltyToAdd.this,"Please fill all fields", Toast.LENGTH_LONG).show();
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
