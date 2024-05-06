package com.uco.ucodgt.mvc.controller.worker.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.uco.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
 import com.uco.ucodgt.mvc.model.business.user.worker.ManagerWorker;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.model.data.UserCallback;
  import com.uco.ucodgt.mvc.view.worker.WorkerActivity;
import com.uco.ucodgt.mvc.view.worker.user.IntroduceActualPassword;
import com.uco.ucodgt.mvc.view.worker.user.IntroduceNewData;

import java.util.List;

public class CheckWorkerPassword extends AppCompatActivity {
    private ProgressBar progressBar;

    String numberWorker;
    String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.loading);

        setContentView(com.uco.ucodgt.R.layout.loading);
        progressBar = findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();

        numberWorker=getIntent().getStringExtra("numberWorker");
        password=getIntent().getStringExtra("password");

        ManagerWorker mngW=new ManagerWorker();
        WorkerDTO wk=new WorkerDTO(null,password,null,null,null,null,Integer.parseInt(numberWorker));

        mngW.getUserByNumber(wk, CheckWorkerPassword.this, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {

            }

            @Override
            public void onError(VolleyError error) {

                Intent goMain=new Intent(CheckWorkerPassword.this, WorkerActivity.class);
                goMain.putExtra("numberWorker",numberWorker);
                Toast.makeText(CheckWorkerPassword.this,"An error has ocurred try again please", Toast.LENGTH_LONG).show();
                startActivity(goMain);
                finish();
                hideLoading();
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {
                mngW.checkLogInWorker(user, CheckWorkerPassword.this, new UserCallback() {
                    @Override
                    public void onUserReceived(ClientDTO user) {
                    }

                    @Override
                    public void onError(VolleyError error) {

                        Intent goMain=new Intent(CheckWorkerPassword.this, IntroduceActualPassword.class);
                        goMain.putExtra("numberWorker",numberWorker);
                        Toast.makeText(CheckWorkerPassword.this,"The password is incorrect", Toast.LENGTH_LONG).show();
                        startActivity(goMain);
                        finish();
                        hideLoading();
                    }

                    @Override
                    public void onWorkerReceived(WorkerDTO user) {

                        Intent goNewData=new Intent(CheckWorkerPassword.this, IntroduceNewData.class);
                        goNewData.putExtra("numberWorker",numberWorker);
                        goNewData.putExtra("dni",user.getDni());
                        Toast.makeText(CheckWorkerPassword.this,"Password is correct", Toast.LENGTH_LONG).show();
                        startActivity(goNewData);
                        finish();
                        hideLoading();
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