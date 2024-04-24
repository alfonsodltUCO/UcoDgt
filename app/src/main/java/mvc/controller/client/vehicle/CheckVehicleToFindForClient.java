package mvc.controller.client.vehicle;

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
/**
 * This activity is responsible for checking the details of a vehicle for a client.
 * @author Alfonso de la Torre
 */
public class CheckVehicleToFindForClient extends AppCompatActivity {
    private ProgressBar progressBar;
    String licenceplate;
    /**
     * Called when the activity is starting. Responsible for initializing the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down, this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
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
