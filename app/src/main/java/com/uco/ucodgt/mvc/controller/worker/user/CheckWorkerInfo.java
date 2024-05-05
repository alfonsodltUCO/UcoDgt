package com.uco.ucodgt.mvc.controller.worker.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.List;

import com.uco.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.user.worker.ManagerWorker;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.model.data.UserCallback;
import com.uco.ucodgt.mvc.view.worker.WorkerActivity;
import com.uco.ucodgt.mvc.view.worker.user.ShowWorker;

/**
 * This activity checks the information of a worker and navigates to the ShowWorker activity to display the details.
 * @author Alfonso de la torre
 */
public class CheckWorkerInfo extends AppCompatActivity {

    String numberWorker;
    private ProgressBar progressBar;

    /**
     * Called when the activity is starting. Responsible for initializing the activity,
     * retrieving the information of the worker, and navigating to the ShowWorker activity
     * to display the worker's details.
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

        ManagerWorker mngW=new ManagerWorker();
        WorkerDTO wrk=new WorkerDTO(null,null,null,null,null,null,Integer.parseInt(numberWorker));


        mngW.getUserByNumber(wrk, CheckWorkerInfo.this, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {

            }

            @Override
            public void onError(VolleyError error) {

                Intent intent=new Intent(CheckWorkerInfo.this, WorkerActivity.class);
                intent.putExtra("numberWorker",numberWorker);
                Toast.makeText(CheckWorkerInfo.this,"An error has occured try again please", Toast.LENGTH_LONG).show();
                startActivity(intent);
                hideLoading();
                finish();
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {

                Intent intent=new Intent(CheckWorkerInfo.this, ShowWorker.class);
                intent.putExtra("numberWorker",numberWorker);
                intent.putExtra("worker",user);
                startActivity(intent);
                hideLoading();
                finish();
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
