package mvc.controller.client;

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
import mvc.view.client.ClientActivity;
import mvc.view.client.vehicle.ShowVehicle;

public class CheckVehicleToFindForClient extends AppCompatActivity {
    private ProgressBar progressBar;
    String licenceplate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        String dni=getIntent().getStringExtra("dni");
        showLoading();
        licenceplate=getIntent().getStringExtra("licenceplate");
        if(!TextUtils.isEmpty(licenceplate)){
                Intent intentAdmin=new Intent(CheckVehicleToFindForClient.this, ClientActivity.class);
                startActivity(intentAdmin);
                Toast.makeText(CheckVehicleToFindForClient.this,"Try again please", Toast.LENGTH_LONG).show();
                finish();

                ManagerVehicle mngV=new ManagerVehicle();
                VehicleDTO vehicle=new VehicleDTO(licenceplate,null,null,null,null,0);
                mngV.getVehicle(vehicle, CheckVehicleToFindForClient.this, new VehicleCallback() {
                @Override
                public void onVehicleReceived(VehicleDTO vehicle) {
                    Intent goShow=new Intent(CheckVehicleToFindForClient.this, ShowVehicle.class);
                    goShow.putExtra("vehicle",vehicle);
                    goShow.putExtra("dni",dni);
                    startActivity(goShow);
                    finish();
                    hideLoading();
                }

                @Override
                public void onError(VolleyError error) {
                    Intent goMain=new Intent(CheckVehicleToFindForClient.this, ClientActivity.class);
                    Toast.makeText(CheckVehicleToFindForClient.this,"Not found the vehicle", Toast.LENGTH_LONG).show();
                    goMain.putExtra("dni",dni);
                    startActivity(goMain);
                    finish();
                    hideLoading();
                }

                @Override
                public void onVehiclesReceived(List<VehicleDTO> vehicles) {

                }
            });

        }
    }
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
