package com.uco.ucodgt.mvc.controller.client.user;

import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkAdminEmailNotExists;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkClientEmailNotExists;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkDateOfBirth;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkPassword;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkValidEmail;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkWorkerEmailNotExists;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.uco.ucodgt.mvc.controller.admin.users.CheckUserToAdd;
import com.uco.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;

import com.uco.ucodgt.mvc.model.business.user.client.ManagerClient;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.model.data.UserCallback;

import com.uco.ucodgt.mvc.view.client.ClientActivity;
import com.uco.ucodgt.mvc.view.client.user.IntroduceNewData;
import java.util.List;


public class CheckDataForUpdate extends AppCompatActivity {
    private ProgressBar progressBar;

    String dniRec,email,pasword;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.loading);
        progressBar = findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();

        dniRec=getIntent().getStringExtra("dni");
        email=getIntent().getStringExtra("email");
        pasword=getIntent().getStringExtra("password");


        if(!checkValidEmail(email)){
            showLoading();
            Intent intent = new Intent(CheckDataForUpdate.this, IntroduceNewData.class);
            intent.putExtra("dni",dniRec);
            startActivity(intent);
            Toast.makeText(CheckDataForUpdate.this, "The email is not valid format", Toast.LENGTH_LONG).show();
            hideLoading();
            finish();
        }else {
            if(!checkPassword(pasword)){
                showLoading();
                Intent intent = new Intent(CheckDataForUpdate.this, IntroduceNewData.class);
                intent.putExtra("dni",dniRec);
                startActivity(intent);
                Toast.makeText(CheckDataForUpdate.this, "The password is not valid\nMust be 8 characters al least\nOne number\nOne special character\nOne capital letter", Toast.LENGTH_LONG).show();
                hideLoading();
                finish();
            }else{

                checkAdminEmailNotExists(email, CheckDataForUpdate.this,new UserCallback(){//Check if user exists

                    @Override
                    public void onUserReceived(ClientDTO user) {}
                    @Override
                    public void onError(VolleyError error) {
                        runOnUiThread(() -> checkClientEmailNotExists(email,CheckDataForUpdate.this,new UserCallback(){
                            @Override
                            public void onUserReceived(ClientDTO user) {

                                runOnUiThread(() -> {

                                    showLoading();
                                    Intent intent = new Intent(CheckDataForUpdate.this, IntroduceNewData.class);
                                    intent.putExtra("dni",dniRec);
                                    startActivity(intent);
                                    Toast.makeText(CheckDataForUpdate.this, "The email is not valid, use other please", Toast.LENGTH_LONG).show();
                                    hideLoading();
                                    finish();

                                });
                            }
                            @Override
                            public void onError(VolleyError error1) { runOnUiThread(() ->
                                    checkWorkerEmailNotExists(email,CheckDataForUpdate.this,new UserCallback(){

                                @Override
                                public void onUserReceived(ClientDTO user) {

                                }

                                @Override
                                public void onError(VolleyError error11) {
                                    runOnUiThread(() -> {
                                        ManagerClient mngC=new ManagerClient();
                                        ClientDTO cl=new ClientDTO(dniRec,pasword,null,null,null,email,null);
                                        mngC.updateUser(cl,CheckDataForUpdate.this, new UserCallback() {
                                            @Override
                                            public void onUserReceived(ClientDTO user) {

                                                showLoading();
                                                Intent intent = new Intent(CheckDataForUpdate.this, ClientActivity.class);
                                                intent.putExtra("dni",dniRec);
                                                startActivity(intent);
                                                Toast.makeText(CheckDataForUpdate.this, "Update done", Toast.LENGTH_LONG).show();
                                                hideLoading();
                                                finish();
                                            }

                                            @Override
                                            public void onError(VolleyError error) {
                                                showLoading();
                                                Intent intent = new Intent(CheckDataForUpdate.this, IntroduceNewData.class);
                                                intent.putExtra("dni",dniRec);
                                                startActivity(intent);
                                                Toast.makeText(CheckDataForUpdate.this, "An error has occurred, try again please", Toast.LENGTH_LONG).show();
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
                                }

                                @Override
                                public void onWorkerReceived(WorkerDTO user) {

                                    runOnUiThread(() -> {

                                        showLoading();
                                        Intent intent = new Intent(CheckDataForUpdate.this, IntroduceNewData.class);
                                        intent.putExtra("dni",dniRec);
                                        startActivity(intent);
                                        Toast.makeText(CheckDataForUpdate.this, "The email is not valid, use other please", Toast.LENGTH_LONG).show();
                                        hideLoading();
                                        finish();
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
                            }));}
                            @Override
                            public void onWorkerReceived(WorkerDTO user) {}
                            @Override
                            public void onAdminReceived(AdminDTO user) {}

                            @Override
                            public void onWorkersReceived(List<WorkerDTO> workers) {

                            }

                            @Override
                            public void onClientsReceived(List<ClientDTO> clients) {

                            }
                        }));
                    }
                    @Override
                    public void onWorkerReceived(WorkerDTO user) {}

                    @Override
                    public void onAdminReceived(AdminDTO user) {

                        runOnUiThread(() -> {

                            showLoading();
                            Intent intent = new Intent(CheckDataForUpdate.this, IntroduceNewData.class);
                            intent.putExtra("dni",dniRec);
                            startActivity(intent);
                            Toast.makeText(CheckDataForUpdate.this, "The email is not valid, use other please", Toast.LENGTH_LONG).show();
                            hideLoading();
                            finish();

                        });


                    }

                    @Override
                    public void onWorkersReceived(List<WorkerDTO> workers) {

                    }

                    @Override
                    public void onClientsReceived(List<ClientDTO> clients) {

                    }
                });
            }
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
