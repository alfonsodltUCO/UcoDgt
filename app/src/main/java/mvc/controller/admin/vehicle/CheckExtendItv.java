package mvc.controller.admin.vehicle;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.util.Date;
import java.util.List;

import mvc.model.business.penalty.ManagerPenalty;
import mvc.model.business.vehicle.ManagerVehicle;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.VehicleCallback;

public class CheckExtendItv extends AppCompatActivity {

    ProgressBar progressBar;

    String licencePlate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        showLoading();

        licencePlate=getIntent().getStringExtra("licencePlate");

        ManagerVehicle mngV=new ManagerVehicle();

        VehicleDTO vehicleToSend=new VehicleDTO();
        vehicleToSend.setLicencePlate(licencePlate);

        mngV.getVehicle(vehicleToSend, CheckExtendItv.this, new VehicleCallback() {
            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {

                Date start,end;

                start=vehicle.getValidItvFrom();
                end=vehicle.getValidItvTo();


            }

            @Override
            public void onError(VolleyError error) {


            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehicles) {

            }
        });

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
