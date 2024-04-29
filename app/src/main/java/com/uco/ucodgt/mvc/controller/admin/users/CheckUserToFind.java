package com.uco.ucodgt.mvc.controller.admin.users;

import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkDni;

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

import com.uco.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ManagerClient;
import com.uco.ucodgt.mvc.model.business.user.worker.ManagerWorker;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.model.data.UserCallback;
import com.uco.ucodgt.mvc.view.admin.user.FindUserActivity;
import com.uco.ucodgt.mvc.view.admin.user.ShowUser;
/**
 * An activity to check the validity of a user's DNI before attempting to find the user in the database.
 * @author Alfonso de la torre
 */
public class CheckUserToFind  extends AppCompatActivity {
    private ProgressBar progressBar;
    String userToFind;
    String dni;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.loading);
        progressBar = findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();
        Intent intent=getIntent();
        userToFind=intent.getStringExtra("userToFind");
        dni=intent.getStringExtra("dni");

        if(!TextUtils.isEmpty(dni) && !TextUtils.isEmpty(userToFind)){

            if(!checkDni(dni)){//Check DNI format

                Intent intentAdmin=new Intent(CheckUserToFind.this, FindUserActivity.class);
                startActivity(intentAdmin);
                Toast.makeText(CheckUserToFind.this,"No valid DNI", Toast.LENGTH_LONG).show();
                finish();

            }else{
                // User found
                Executor executor = Executors.newSingleThreadExecutor();

                executor.execute(() -> {

                    if(userToFind.equals("client")){//User is Client
                        showLoading();
                        ManagerClient mngcl=new ManagerClient();
                        ClientDTO clientToFind=new ClientDTO(dni,null,null,null,null,null,null);
                        mngcl.getUser(clientToFind, CheckUserToFind.this, new UserCallback() {
                            @Override
                            public void onUserReceived(ClientDTO user) {
                                // User found

                                Toast.makeText(CheckUserToFind.this,"Client Found", Toast.LENGTH_LONG).show();
                                Intent intentSeeWorker=new Intent(CheckUserToFind.this, ShowUser.class);
                                intentSeeWorker.putExtra("client", user);
                                intentSeeWorker.putExtra("type","client");
                                startActivity(intentSeeWorker);
                                hideLoading();
                                finish();

                            }

                            @Override
                            public void onError(VolleyError error) {

                                // Error: user not found
                                Toast.makeText(CheckUserToFind.this,"Not found", Toast.LENGTH_LONG).show();
                                Intent intentGoBack=new Intent(CheckUserToFind.this, FindUserActivity.class);
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

                    }else{//User is Worker

                        showLoading();
                        ManagerWorker mngwk=new ManagerWorker();
                        WorkerDTO workerToFind=new WorkerDTO(dni,null,null,null,null,null,null);

                        mngwk.getUser(workerToFind, CheckUserToFind.this, new UserCallback() {
                            @Override
                            public void onUserReceived(ClientDTO user) {
                            }

                            @Override
                            public void onError(VolleyError error) {

                                Toast.makeText(CheckUserToFind.this,"Not found", Toast.LENGTH_LONG).show();
                                Intent intentGoBack=new Intent(CheckUserToFind.this, FindUserActivity.class);
                                startActivity(intentGoBack);
                                hideLoading();
                                finish();
                            }

                            @Override
                            public void onWorkerReceived(WorkerDTO user) {
                                // User found

                                Toast.makeText(CheckUserToFind.this,"Worker Found", Toast.LENGTH_LONG).show();
                                Intent intentSeeWorker=new Intent(CheckUserToFind.this, ShowUser.class);
                                intentSeeWorker.putExtra("worker", user);
                                intentSeeWorker.putExtra("type","worker");
                                startActivity(intentSeeWorker);
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
                });
            }

        }else{

            Intent intentAdmin=new Intent(CheckUserToFind.this, FindUserActivity.class);
            startActivity(intentAdmin);
            Toast.makeText(CheckUserToFind.this,"Please fill all fields", Toast.LENGTH_LONG).show();
            finish();
        }


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
