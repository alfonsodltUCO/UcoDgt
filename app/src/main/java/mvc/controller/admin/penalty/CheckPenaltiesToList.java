package mvc.controller.admin.penalty;

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
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.PenaltyCallback;
import mvc.view.admin.AdminActivity;
import mvc.view.admin.penalty.ShowPenalties;
/**
 * An activity to check penalties to list.
 * @author Alfonso de la torre
 */
public class CheckPenaltiesToList extends AppCompatActivity {
    ProgressBar progressBar;
    List<PenaltyDTO> penalties;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        showLoading();
        String state=getIntent().getStringExtra("state");
        String date1=getIntent().getStringExtra("date1");
        String date2=getIntent().getStringExtra("date2");
        String lic=getIntent().getStringExtra("licencePlate");
        String dni=getIntent().getStringExtra("dni");

        if(!TextUtils.isEmpty(lic)){

            VehicleDTO vh=new VehicleDTO(lic,null,null,null,null,0);
            ManagerPenalty mngP=new ManagerPenalty();

            mngP.getPenalties(vh,CheckPenaltiesToList.this, new PenaltyCallback() {

                @Override
                public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                    Intent goShow=new Intent(CheckPenaltiesToList.this, ShowPenalties.class);
                    goShow.putExtra("penalties",(Serializable) penalties);
                    startActivity(goShow);
                    hideLoading();
                    finish();
                }

                @Override
                public void onError(VolleyError error) {

                    Intent goMain=new Intent(CheckPenaltiesToList.this,AdminActivity.class);
                    Toast.makeText(CheckPenaltiesToList.this,"Not found any penalty", Toast.LENGTH_LONG).show();
                    startActivity(goMain);
                    hideLoading();
                    finish();
                }

                @Override
                public void onPenaltyReceived(PenaltyDTO penalty) {

                }
            });

        } else if (!TextUtils.isEmpty(dni)) {

            ClientDTO client=new ClientDTO(dni,null,null,null,null,null,null);
            ManagerPenalty mngP=new ManagerPenalty();
            mngP.getPenalties(client,CheckPenaltiesToList.this, new PenaltyCallback() {

                @Override
                public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                    Intent goShow = new Intent(CheckPenaltiesToList.this, ShowPenalties.class);
                    goShow.putExtra("penalties", (Serializable) penalties);
                    startActivity(goShow);
                    hideLoading();
                    finish();
                }

                @Override
                public void onError(VolleyError error) {

                    Intent goMain = new Intent(CheckPenaltiesToList.this, AdminActivity.class);
                    Toast.makeText(CheckPenaltiesToList.this, "Not found any penalty", Toast.LENGTH_LONG).show();
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

                ManagerPenalty mngP=new ManagerPenalty();

                if(!checkDatesPenalties(date1,date2)){

                    Intent goMain = new Intent(CheckPenaltiesToList.this, AdminActivity.class);
                    Toast.makeText(CheckPenaltiesToList.this, "Dates must be yyyy-mm-dd\nStart mus be older than end", Toast.LENGTH_LONG).show();
                    startActivity(goMain);
                    hideLoading();
                    finish();

                }else{

                    mngP.getPenalties(date1,date2,CheckPenaltiesToList.this, new PenaltyCallback() {

                        @Override
                        public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                            Intent goShow = new Intent(CheckPenaltiesToList.this, ShowPenalties.class);
                            goShow.putExtra("penalties", (Serializable) penalties);
                            startActivity(goShow);
                            hideLoading();
                            finish();
                        }

                        @Override
                        public void onError(VolleyError error) {

                            Intent goMain = new Intent(CheckPenaltiesToList.this, AdminActivity.class);
                            Toast.makeText(CheckPenaltiesToList.this, "Not found any penalty", Toast.LENGTH_LONG).show();
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

                    ManagerPenalty mngP=new ManagerPenalty();

                    mngP.getPenalties(state,CheckPenaltiesToList.this, new PenaltyCallback() {

                        @Override
                        public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                            Intent goShow = new Intent(CheckPenaltiesToList.this, ShowPenalties.class);
                            goShow.putExtra("penalties", (Serializable) penalties);
                            startActivity(goShow);
                            hideLoading();
                            finish();
                        }

                        @Override
                        public void onError(VolleyError error) {

                            Intent goMain = new Intent(CheckPenaltiesToList.this, AdminActivity.class);
                            Toast.makeText(CheckPenaltiesToList.this, "Not found any penalty", Toast.LENGTH_LONG).show();
                            startActivity(goMain);
                            hideLoading();
                            finish();
                        }

                        @Override
                        public void onPenaltyReceived(PenaltyDTO penalty) {

                        }
                    });
                }else{

                    ManagerPenalty mngP=new ManagerPenalty();

                    mngP.getPenalties(CheckPenaltiesToList.this, new PenaltyCallback() {

                        @Override
                        public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                            Intent goShow = new Intent(CheckPenaltiesToList.this, ShowPenalties.class);
                            goShow.putExtra("penalties", (Serializable) penalties);
                            startActivity(goShow);
                            hideLoading();
                            finish();

                        }

                        @Override
                        public void onError(VolleyError error) {

                            Intent goMain = new Intent(CheckPenaltiesToList.this, AdminActivity.class);
                            Toast.makeText(CheckPenaltiesToList.this, "Not found any penalty", Toast.LENGTH_LONG).show();
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