package mvc.controller.client.penalty;

import static mvc.controller.commonFunctions.ForCheckPenalty.checkDatesPenalties;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.io.Serializable;
import java.util.List;

import mvc.model.business.penalty.ManagerPenalty;
import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.penalty.stateof;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.PenaltyCallback;
import mvc.view.client.ClientActivity;
import mvc.view.client.penalty.ShowPenalties;

/**
 * An activity to check penalties and display them for a client.
 * @author Alfonso de la torre
 */
public class CheckPenaltiesToListForClient extends AppCompatActivity {
    ProgressBar progressBar;
    List<PenaltyDTO> penalties;
    String dni;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        dni=getIntent().getStringExtra("dni");
        progressBar=findViewById(R.id.progressbar);
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
                    goShow.putExtra("penalties",(Serializable) penalties);
                    goShow.putExtra("dni",dni);
                    startActivity(goShow);
                    hideLoading();
                    finish();
                }

                @Override
                public void onError(VolleyError error) {

                    Intent goMain=new Intent(CheckPenaltiesToListForClient.this, ClientActivity.class);
                    Toast.makeText(CheckPenaltiesToListForClient.this,"Not found any penalty", Toast.LENGTH_LONG).show();
                    goMain.putExtra("dni",dni);
                    startActivity(goMain);
                    hideLoading();
                    finish();
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
                    Toast.makeText(CheckPenaltiesToListForClient.this, "Dates must be yyyy-mm-dd\nStart mus be older than end", Toast.LENGTH_LONG).show();
                    startActivity(goMain);
                    hideLoading();
                    finish();
                }else{

                    PenaltyDTO penalty=new PenaltyDTO(null,null,null,null,null,null,dni,null,null,null,false,null,null);
                    mngP.getPenalties(date1,date2,penalty, CheckPenaltiesToListForClient.this, new PenaltyCallback() {

                        @Override
                        public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                            Intent goShow = new Intent(CheckPenaltiesToListForClient.this, ShowPenalties.class);
                            goShow.putExtra("penalties", (Serializable) penalties);
                            startActivity(goShow);
                            hideLoading();
                            finish();
                        }

                        @Override
                        public void onError(VolleyError error) {

                            Intent goMain = new Intent(CheckPenaltiesToListForClient.this, ClientActivity.class);
                            Toast.makeText(CheckPenaltiesToListForClient.this, "Not found any penalty", Toast.LENGTH_LONG).show();
                            startActivity(goMain);
                            hideLoading();
                            finish();
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
                            goShow.putExtra("penalties", (Serializable) penalties);
                            goShow.putExtra("dni",dni);
                            startActivity(goShow);
                            hideLoading();
                            finish();
                        }

                        @Override
                        public void onError(VolleyError error) {

                            Intent goMain = new Intent(CheckPenaltiesToListForClient.this, ClientActivity.class);
                            Toast.makeText(CheckPenaltiesToListForClient.this, "Not found any penalty", Toast.LENGTH_LONG).show();
                            startActivity(goMain);
                            goMain.putExtra("dni",dni);
                            hideLoading();
                            finish();
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
                            goShow.putExtra("penalties", (Serializable) penalties);
                            startActivity(goShow);
                            hideLoading();
                            finish();
                        }

                        @Override
                        public void onError(VolleyError error) {

                            Intent goMain = new Intent(CheckPenaltiesToListForClient.this, ClientActivity.class);
                            Toast.makeText(CheckPenaltiesToListForClient.this, "Not found any penalty", Toast.LENGTH_LONG).show();
                            startActivity(goMain);
                            hideLoading();
                            finish();
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
