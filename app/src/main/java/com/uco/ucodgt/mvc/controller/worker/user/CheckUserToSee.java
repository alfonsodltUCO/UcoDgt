package com.uco.ucodgt.mvc.controller.worker.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.List;

import com.uco.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ManagerClient;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.model.business.vehicle.VehicleDTO;
import com.uco.ucodgt.mvc.model.data.UserCallback;
import com.uco.ucodgt.mvc.view.worker.user.ShowUser;


/**
 * This activity is responsible for checking a user associated with a specific vehicle
 * and navigating to the ShowUser activity to display the user's information.
 * @author Alfonso de la torre
 */
public class CheckUserToSee extends AppCompatActivity {


    ProgressBar progressBar;

    String numberWorker;


    /**
     * Called when the activity is starting. Responsible for initializing the activity,
     * retrieving the user associated with a vehicle, and navigating to the ShowUser activity
     * to display the user's information.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.loading);
        progressBar=findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();

        numberWorker=getIntent().getStringExtra("numberWorker");

        String lplate=getIntent().getStringExtra("licencePlate");

        ManagerClient mngC=new ManagerClient();
        VehicleDTO vh=new VehicleDTO();
        vh.setLicencePlate(lplate);

        mngC.getOwner(vh,CheckUserToSee.this, new UserCallback() {

            @Override
            public void onUserReceived(ClientDTO user) {
                Intent goShow=new Intent(CheckUserToSee.this, ShowUser.class);
                goShow.putExtra("numberWorker",numberWorker);
                goShow.putExtra("client",user);
                startActivity(goShow);
                finish();
                hideLoading();
            }

            @Override
            public void onError(VolleyError error) {

            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {

            }

            @Override
            public void onAdminReceived(AdminDTO user) {

            }

            @Override
            public void onWorkersReceived(List<WorkerDTO> workers) {

            }

            @Override
            public void onClientsReceived(List<ClientDTO> clients) {

            }
        });
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
