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

import java.io.Serializable;
import java.util.List;

import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.vehicle.ManagerVehicle;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.VehicleCallback;
import mvc.view.client.ClientActivity;
import mvc.view.client.vehicle.ShowVehicles;

/**
 * Method used to handle the view the list of client's vehicles
 * @author Alfonso de la Torre
 */

public class CheckVehiclesToListForClient extends AppCompatActivity  {

    ProgressBar progressBar;
    List<VehicleDTO> vehiclelist;
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
        showLoading();
        String dni=getIntent().getStringExtra("dni");

        if(!TextUtils.isEmpty(dni)){
            // Retrieve client's vehicles from ManagerVehicle

            ManagerVehicle mngV=new ManagerVehicle();
            ClientDTO client=new ClientDTO(dni,null,null,null,null,null,null);
            mngV.getVehicles(client, CheckVehiclesToListForClient.this, new VehicleCallback() {
                @Override
                public void onVehicleReceived(VehicleDTO vehicle) {

                }

                @Override
                public void onError(VolleyError error) {

                    Intent emptyLists = new Intent(CheckVehiclesToListForClient.this, ClientActivity.class);
                    startActivity(emptyLists);
                    emptyLists.putExtra("dni",dni);
                    Toast.makeText(CheckVehiclesToListForClient.this,"Not found any vehicle",Toast.LENGTH_LONG).show();
                    hideLoading();
                    finish();
                }

                @Override
                public void onVehiclesReceived(List<VehicleDTO> vehicles) {

                    // If vehicles received, display them in ShowVehicles activity
                    vehiclelist=vehicles;
                    Intent notEmptyLists = new Intent(CheckVehiclesToListForClient.this, ShowVehicles.class);
                    notEmptyLists.putExtra("vehicles", (Serializable) vehiclelist);
                    Toast.makeText(CheckVehiclesToListForClient.this,"found vehicles",Toast.LENGTH_LONG).show();
                    notEmptyLists.putExtra("dni",dni);
                    startActivity(notEmptyLists);
                    hideLoading();
                    finish();
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
