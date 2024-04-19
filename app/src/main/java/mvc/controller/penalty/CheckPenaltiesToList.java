package mvc.controller.penalty;

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

import java.io.Serializable;
import java.util.List;

import mvc.controller.vehicle.CheckVehiclesToList;
import mvc.model.business.penalty.ManagerPenalty;
import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.vehicle.ManagerVehicle;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.PenaltyCallback;
import mvc.model.data.VehicleCallback;
import mvc.view.admin.AdminActivity;
import mvc.view.admin.penalty.ShowPenalties;
import mvc.view.admin.vehicle.ShowVehicles;

public class CheckPenaltiesToList extends AppCompatActivity {
    ProgressBar progressBar;
    List<PenaltyDTO> penalties;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        showLoading();
        String date1=getIntent().getStringExtra("date1");
        String date2=getIntent().getStringExtra("date2");
        String lic=getIntent().getStringExtra("licencePlate");
        String dni=getIntent().getStringExtra("dni");
        if(!TextUtils.isEmpty(lic)){//por vehiculo
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
                    Toast.makeText(CheckPenaltiesToList.this,"Not found any penalty for vehicle", Toast.LENGTH_LONG).show();
                    startActivity(goMain);
                    hideLoading();
                    finish();
                }

                @Override
                public void onPenaltyReceived(PenaltyDTO penalty) {

                }
            });

        } else if (!TextUtils.isEmpty(dni)) {//por usuario
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
                    Toast.makeText(CheckPenaltiesToList.this, "Not found any penalty for vehicle", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(CheckPenaltiesToList.this, "Not found any penalty for vehicle", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(CheckPenaltiesToList.this, "Not found any penalty for vehicle", Toast.LENGTH_LONG).show();
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
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

}
