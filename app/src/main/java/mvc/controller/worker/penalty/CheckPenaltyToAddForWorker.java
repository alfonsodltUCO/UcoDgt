package mvc.controller.worker.penalty;

import static mvc.controller.commonFunctions.ForCheckListPenalty.checkPenalty;
import static mvc.controller.commonFunctions.ForCheckPenalty.checkPoints;
import static mvc.controller.commonFunctions.ForCheckPenalty.checkQuantity;
import static mvc.controller.commonFunctions.ForCheckPenalty.checkReasonOf;
import static mvc.controller.commonFunctions.ForCheckPenalty.checkStateOf;
import static mvc.controller.commonFunctions.ForCheckUser.checkDni;
import static mvc.controller.commonFunctions.ForCheckVehicle.checkPlate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mvc.model.business.penalty.ManagerPenalty;
import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.penalty.list.ListPenaltyDTO;
import mvc.model.business.penalty.stateof;
import mvc.model.business.penalty.typeof;
import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.ManagerWorker;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.ListPenaltyCallback;
import mvc.model.data.PenaltyCallback;
import mvc.model.data.UserCallback;
import mvc.view.worker.WorkerActivity;
import mvc.view.worker.penalty.AddPenaltyActivity;

/**
 * An activity to check penalty to add.
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
        setContentView(R.layout.loading);
        progressBar = findViewById(R.id.progressbar);
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
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            Toast.makeText(CheckPenaltyToAddForWorker.this,"Invalid value for informed at the moment\nMust be Yes/No", Toast.LENGTH_LONG).show();
            hideLoading();
            finish();
        }

        if(!TextUtils.isEmpty(points) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(licenceplate) && !TextUtils.isEmpty(quantity) && !TextUtils.isEmpty(dniC) && !TextUtils.isEmpty(state) && !TextUtils.isEmpty(reason) && !TextUtils.isEmpty(place) && !TextUtils.isEmpty(informed) && !TextUtils.isEmpty(locality)){

            if(!checkDni(dniC)){

                Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
                intent.putExtra("numberWorker",numberWorker);
                startActivity(intent);
                Toast.makeText(CheckPenaltyToAddForWorker.this,"Invalid format DNI of client", Toast.LENGTH_LONG).show();
                hideLoading();
                finish();
            }else{

                if(!checkStateOf(state)){

                    Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
                    intent.putExtra("numberWorker",numberWorker);
                    startActivity(intent);
                    Toast.makeText(CheckPenaltyToAddForWorker.this,"Invalid value for state", Toast.LENGTH_LONG).show();
                    hideLoading();
                    finish();
                }else{
                    if(!checkReasonOf(reason)){

                        Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
                        intent.putExtra("numberWorker",numberWorker);
                        startActivity(intent);
                        Toast.makeText(CheckPenaltyToAddForWorker.this,"Invalid format for reason", Toast.LENGTH_LONG).show();
                        hideLoading();
                        finish();

                    }else{
                        if(!checkPlate(licenceplate)){


                            Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
                            intent.putExtra("numberWorker",numberWorker);
                            startActivity(intent);
                            Toast.makeText(CheckPenaltyToAddForWorker.this,"Invalid format for licence plate", Toast.LENGTH_LONG).show();
                            hideLoading();
                            finish();

                        }else{

                            if(!checkPoints(points)){

                                Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
                                intent.putExtra("numberWorker",numberWorker);
                                startActivity(intent);
                                Toast.makeText(CheckPenaltyToAddForWorker.this,"Points must be a number\n", Toast.LENGTH_LONG).show();
                                hideLoading();
                                finish();

                            }else {

                                if(!checkQuantity(quantity)){//Check quantity is valid format


                                    Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
                                    intent.putExtra("numberWorker",numberWorker);
                                    startActivity(intent);
                                    Toast.makeText(CheckPenaltyToAddForWorker.this,"Quantity must be a number\n", Toast.LENGTH_LONG).show();
                                    hideLoading();
                                    finish();

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
                                                                    intent.putExtra("numberWorker",numberWorker);
                                                                    startActivity(intent);
                                                                    Toast.makeText(CheckPenaltyToAddForWorker.this,"User doesnt have this vehicle", Toast.LENGTH_LONG).show();
                                                                    hideLoading();
                                                                    finish();

                                                                }else if(error.networkResponse.statusCode==400){


                                                                    Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
                                                                    intent.putExtra("numberWorker",numberWorker);
                                                                    startActivity(intent);
                                                                    Toast.makeText(CheckPenaltyToAddForWorker.this,"Worker doesnt exist\nOr vehicle doesnt exist", Toast.LENGTH_LONG).show();
                                                                    hideLoading();
                                                                    finish();

                                                                }else if(error.networkResponse.statusCode==422) {

                                                                    Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
                                                                    intent.putExtra("numberWorker",numberWorker);
                                                                    startActivity(intent);
                                                                    Toast.makeText(CheckPenaltyToAddForWorker.this, "Client doesnt exists", Toast.LENGTH_LONG).show();
                                                                    hideLoading();
                                                                    finish();

                                                                }else{

                                                                    Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
                                                                    intent.putExtra("numberWorker",numberWorker);
                                                                    startActivity(intent);
                                                                    Toast.makeText(CheckPenaltyToAddForWorker.this,"An error has ocurred, check users and vehicle exist", Toast.LENGTH_LONG).show();
                                                                    hideLoading();
                                                                    finish();
                                                                }
                                                            }

                                                            @Override
                                                            public void onPenaltyReceived(PenaltyDTO penalty) {

                                                                Intent intent=new Intent(CheckPenaltyToAddForWorker.this, WorkerActivity.class);
                                                                intent.putExtra("numberWorker",numberWorker);
                                                                startActivity(intent);
                                                                Toast.makeText(CheckPenaltyToAddForWorker.this,"Penalty added", Toast.LENGTH_LONG).show();
                                                                hideLoading();
                                                                finish();

                                                            }
                                                        });
                                                    } else {

                                                        Intent intent=new Intent(CheckPenaltyToAddForWorker.this, AddPenaltyActivity.class);
                                                        intent.putExtra("numberWorker",numberWorker);
                                                        startActivity(intent);
                                                        Toast.makeText(CheckPenaltyToAddForWorker.this,"Points and quantity must be between the range of penalty reason", Toast.LENGTH_LONG).show();
                                                        hideLoading();
                                                        finish();
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
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            Toast.makeText(CheckPenaltyToAddForWorker.this,"Please fill all fields", Toast.LENGTH_LONG).show();
            hideLoading();
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
