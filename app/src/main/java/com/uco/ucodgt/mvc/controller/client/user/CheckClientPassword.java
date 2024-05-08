package com.uco.ucodgt.mvc.controller.client.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
 import com.uco.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ManagerClient;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.model.data.UserCallback;
import com.uco.ucodgt.mvc.view.client.ClientActivity;
import com.uco.ucodgt.mvc.view.client.user.IntroduceActualPassword;
import com.uco.ucodgt.mvc.view.client.user.IntroduceNewData;

import java.util.List;


/**
 * This activity is responsible for checking the password of a client user in the application.
 * It retrieves the user's DNI and password from the intent extras, then verifies the password
 * by making a network call. Depending on the result, it navigates to different activities.
 * @author Alfonso de la torre
 */
public class CheckClientPassword extends AppCompatActivity {
    private ProgressBar progressBar;

    String dniRec;
    String password;


    /**
     * Initializes the activity and sets up the UI elements.
     * Retrieves DNI and password from the intent extras.
     * Initiates the process of checking the client's password.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, if any.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(com.uco.ucodgt.R.layout.loading);
        progressBar = findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();

        dniRec=getIntent().getStringExtra("dni");
        password=getIntent().getStringExtra("password");

        ManagerClient mngC=new ManagerClient();
        ClientDTO cl=new ClientDTO(dniRec,null,null,null,null,null,null);

        mngC.getUser(cl, CheckClientPassword.this, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {
                user.setPassword(password);
                mngC.checkLogInClient(user, CheckClientPassword.this, new UserCallback() {
                    @Override
                    public void onUserReceived(ClientDTO user) {

                        Intent goNewData=new Intent(CheckClientPassword.this, IntroduceNewData.class);
                        try {
                            Thread.sleep(2*1000);
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        }
                        goNewData.putExtra("dni",dniRec);
                        Toast.makeText(CheckClientPassword.this,"Password is correct", Toast.LENGTH_LONG).show();
                        startActivity(goNewData);

                        hideLoading();
                    }

                    @Override
                    public void onError(VolleyError error) {

                        Intent goMain=new Intent(CheckClientPassword.this, IntroduceActualPassword.class);
                        try {
                            Thread.sleep(2*1000);
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        }
                        goMain.putExtra("dni",dniRec);
                        Toast.makeText(CheckClientPassword.this,"The password is incorrect", Toast.LENGTH_LONG).show();
                        startActivity(goMain);

                        hideLoading();
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

            @Override
            public void onError(VolleyError error) {

                Intent goMain=new Intent(CheckClientPassword.this, ClientActivity.class);
                try {
                    Thread.sleep(2*1000);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                goMain.putExtra("dni",dniRec);
                Toast.makeText(CheckClientPassword.this,"An error has ocurred try again please", Toast.LENGTH_LONG).show();
                startActivity(goMain);

                hideLoading();
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
