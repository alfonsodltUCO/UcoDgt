package mvc.controller.vehicle;

import static mvc.controller.commonFunctions.ForCheckVehicle.checkPlate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.util.List;

import mvc.model.business.vehicle.ManagerVehicle;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.VehicleCallback;
import mvc.view.admin.AdminActivity;
import mvc.view.vehicle.IntroduceManual;
import mvc.view.vehicle.ShowVehicle;

public class CheckVehicleToFind extends AppCompatActivity {
    private ProgressBar progressBar;
    String licenceplate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        showLoading();
        licenceplate=getIntent().getStringExtra("licenceplate");
        if(!TextUtils.isEmpty(licenceplate)){
            if(!checkPlate(licenceplate)){
                Intent intentAdmin=new Intent(CheckVehicleToFind.this, IntroduceManual.class);
                startActivity(intentAdmin);
                Toast.makeText(CheckVehicleToFind.this,"No valid licence plate", Toast.LENGTH_LONG).show();
                finish();
            }else{
                ManagerVehicle mngV=new ManagerVehicle();
                VehicleDTO vehicle=new VehicleDTO(licenceplate,null,null,null,null,0);
                mngV.getVehicle(vehicle,CheckVehicleToFind.this, new VehicleCallback() {
                    @Override
                    public void onVehicleReceived(VehicleDTO vehicle) {
                        Intent goShow=new Intent(CheckVehicleToFind.this, ShowVehicle.class);
                        goShow.putExtra("vehicle",vehicle);
                        startActivity(goShow);
                        finish();
                        hideLoading();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Intent goMain=new Intent(CheckVehicleToFind.this, AdminActivity.class);
                        Toast.makeText(CheckVehicleToFind.this,"Not found the vehicle", Toast.LENGTH_LONG).show();
                        startActivity(goMain);
                        finish();
                        hideLoading();
                    }

                    @Override
                    public void onVehiclesReceived(List<VehicleDTO> vehicles) {

                    }
                });
            }
        }else{
            Intent intentAdmin=new Intent(CheckVehicleToFind.this, IntroduceManual.class);
            startActivity(intentAdmin);
            Toast.makeText(CheckVehicleToFind.this,"Fill the field please", Toast.LENGTH_LONG).show();
            finish();
        }

    }
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
