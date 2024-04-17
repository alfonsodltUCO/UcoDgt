package mvc.controller.penalty;

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

import mvc.controller.vehicle.CheckVehicleToFind;
import mvc.model.business.vehicle.ManagerVehicle;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.VehicleCallback;
import mvc.view.admin.AdminActivity;
import mvc.view.admin.vehicle.IntroduceManual;
import mvc.view.admin.vehicle.ShowVehicle;

public class CheckPenaltyToFind extends AppCompatActivity {
    private ProgressBar progressBar;
    String id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        showLoading();
        id=getIntent().getStringExtra("id");
        if(!TextUtils.isEmpty(id)){
            ManagerVehicle mngV=new ManagerVehicle();
            VehicleDTO vehicle=new VehicleDTO(licenceplate,null,null,null,null,0);
            mngV.getVehicle(vehicle, mvc.controller.vehicle.CheckVehicleToFind.this, new VehicleCallback() {
                @Override
                public void onVehicleReceived(VehicleDTO vehicle) {
                    Intent goShow=new Intent(mvc.controller.vehicle.CheckVehicleToFind.this, ShowVehicle.class);
                    goShow.putExtra("vehicle",vehicle);
                    startActivity(goShow);
                    finish();
                    hideLoading();
                }

                @Override
                public void onError(VolleyError error) {
                    Intent goMain=new Intent(mvc.controller.vehicle.CheckVehicleToFind.this, AdminActivity.class);
                    Toast.makeText(mvc.controller.vehicle.CheckVehicleToFind.this,"Not found the vehicle", Toast.LENGTH_LONG).show();
                    startActivity(goMain);
                    finish();
                    hideLoading();
                }

                @Override
                public void onVehiclesReceived(List<VehicleDTO> vehicles) {

                }
            });

        }else{
            Intent intentAdmin=new Intent(mvc.controller.vehicle.CheckVehicleToFind.this, IntroduceManual.class);
            startActivity(intentAdmin);
            Toast.makeText(mvc.controller.vehicle.CheckVehicleToFind.this,"Fill the field please", Toast.LENGTH_LONG).show();
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
