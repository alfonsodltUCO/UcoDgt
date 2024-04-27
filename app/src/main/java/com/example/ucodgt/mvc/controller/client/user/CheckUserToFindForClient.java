package com.example.ucodgt.mvc.controller.client.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.example.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.example.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.example.ucodgt.mvc.model.business.user.client.ManagerClient;
import com.example.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.example.ucodgt.mvc.model.data.UserCallback;
import com.example.ucodgt.mvc.view.client.ClientActivity;
import com.example.ucodgt.mvc.view.client.user.ShowUser;
/**
 * This class is responsible for checking and finding a user for the client.
 * It retrieves the user information and displays it in the ShowUser activity.
 * @author Alfonso de la torre
 */
public class CheckUserToFindForClient extends AppCompatActivity {
    private ProgressBar progressBar;
     String dni;
    /**
     * Called when the activity is starting. Responsible for initializing the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down, this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.example.ucodgt.R.layout.loading);
        progressBar = findViewById(com.example.ucodgt.R.id.progressbar);
        showLoading();
        Intent intent=getIntent();
        dni=intent.getStringExtra("dni");

        if(!TextUtils.isEmpty(dni)){// Check if dni is not empty

            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {

                showLoading();
                ManagerClient mngcl=new ManagerClient();
                ClientDTO clientToFind=new ClientDTO(dni,null,null,null,null,null,null);

                mngcl.getUser(clientToFind, CheckUserToFindForClient.this, new UserCallback() {
                    @Override
                    public void onUserReceived(ClientDTO user) {// User found, show details in ShowUser activity
                        Toast.makeText(CheckUserToFindForClient.this,"Client Found", Toast.LENGTH_LONG).show();
                        Intent intentSeeUser=new Intent(CheckUserToFindForClient.this, ShowUser.class);
                        intentSeeUser.putExtra("client", user);
                        intentSeeUser.putExtra("dni",dni);
                        startActivity(intentSeeUser);
                        hideLoading();
                        finish();

                    }

                    @Override
                    public void onError(VolleyError error) {

                        Toast.makeText(CheckUserToFindForClient.this,"An error has occurred try again please", Toast.LENGTH_LONG).show();
                        Intent intentGoBack=new Intent(CheckUserToFindForClient.this, ClientActivity.class);
                        intentGoBack.putExtra("dni",dni);
                        startActivity(intentGoBack);
                        hideLoading();
                        finish();
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

            });

        }else{// If dni is empty, return to ClientActivity

            Intent intentAdmin=new Intent(CheckUserToFindForClient.this, ClientActivity.class);
            intent.putExtra("dni",dni);
            startActivity(intentAdmin);
            Toast.makeText(CheckUserToFindForClient.this,"An error has occurred try again please", Toast.LENGTH_LONG).show();
            finish();
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
