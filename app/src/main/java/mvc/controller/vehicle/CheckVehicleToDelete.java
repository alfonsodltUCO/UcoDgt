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
import mvc.view.admin.vehicle.DeleteVehicleActivity;

public class CheckVehicleToDelete extends AppCompatActivity {
    private ProgressBar progressBar;
    String licencePlate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar = findViewById(R.id.progressbar);
        Intent intent=getIntent();
        showLoading();
        licencePlate=intent.getStringExtra("licencePlate");
        if(!TextUtils.isEmpty(licencePlate)) {
            if(!checkPlate(licencePlate)){
                Intent intentAdmin=new Intent(CheckVehicleToDelete.this, DeleteVehicleActivity.class);
                startActivity(intentAdmin);
                Toast.makeText(CheckVehicleToDelete.this,"No valid plate", Toast.LENGTH_LONG).show();
                finish();
            }else{
                showLoading();
                ManagerVehicle mngV=new ManagerVehicle();
                VehicleDTO vehicle=new VehicleDTO(licencePlate,null,null,null,null,0);
                mngV.deleteVehicle(vehicle, CheckVehicleToDelete.this, new VehicleCallback(){

                    @Override
                    public void onVehicleReceived(VehicleDTO vehicle) {
                        Toast.makeText(CheckVehicleToDelete.this,"Vehicle deleted", Toast.LENGTH_LONG).show();
                        Intent intentGoBack=new Intent(CheckVehicleToDelete.this, AdminActivity.class);
                        startActivity(intentGoBack);
                        hideLoading();
                        finish();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        if(error.networkResponse.statusCode==404) {
                            Toast.makeText(CheckVehicleToDelete.this,"Not found", Toast.LENGTH_LONG).show();
                            Intent intentGoBack=new Intent(CheckVehicleToDelete.this, DeleteVehicleActivity.class);
                            startActivity(intentGoBack);
                            hideLoading();
                            finish();
                        }
                    }

                    @Override
                    public void onVehiclesReceived(List<VehicleDTO> vehicles) {

                    }
                });
            }
        }else{
            Intent intentAdmin=new Intent(CheckVehicleToDelete.this, DeleteVehicleActivity.class);
            startActivity(intentAdmin);
            Toast.makeText(CheckVehicleToDelete.this,"Please fill all fields", Toast.LENGTH_LONG).show();
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