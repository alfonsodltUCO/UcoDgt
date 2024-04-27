package com.example.ucodgt.mvc.controller.admin.users;

import static com.example.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkDni;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.example.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.example.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.example.ucodgt.mvc.model.business.user.client.ManagerClient;
import com.example.ucodgt.mvc.model.business.user.worker.ManagerWorker;
import com.example.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.example.ucodgt.mvc.model.data.UserCallback;
import com.example.ucodgt.mvc.view.admin.AdminActivity;
import com.example.ucodgt.mvc.view.admin.user.DeleteUserActivity;
/**
 * An activity to check the validity of a user's DNI before attempting to delete the user from the database.
 */
public class CheckUserToDelete extends AppCompatActivity {
    private ProgressBar progressBar;
    String dni,userToDelete,type;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.example.ucodgt.R.layout.loading);
        progressBar = findViewById(com.example.ucodgt.R.id.progressbar);
        Intent intent=getIntent();
        showLoading();
        userToDelete=intent.getStringExtra("userToDelete");
        type=intent.getStringExtra("type");
        dni=intent.getStringExtra("dni");

        if(!TextUtils.isEmpty(dni) && !TextUtils.isEmpty(userToDelete)){

            if(!checkDni(dni)){//Check dni format

                Intent intentAdmin=new Intent(CheckUserToDelete.this, DeleteUserActivity.class);
                startActivity(intentAdmin);
                Toast.makeText(CheckUserToDelete.this,"No valid DNI", Toast.LENGTH_LONG).show();
                finish();

            }else{

                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {

                    if(userToDelete.equals("client")){//Delete a Client

                        showLoading();
                        ManagerClient mngcl=new ManagerClient();
                        ClientDTO clientToFind=new ClientDTO(dni,null,null,null,null,null,null);

                        mngcl.deleteUser(clientToFind, CheckUserToDelete.this, new UserCallback() {

                            @Override
                            public void onUserReceived(ClientDTO user) {
                                // Client deleted

                                Toast.makeText(CheckUserToDelete.this,"Client deleted", Toast.LENGTH_LONG).show();
                                Intent intentGoBack=new Intent(CheckUserToDelete.this, AdminActivity.class);
                                startActivity(intentGoBack);
                                hideLoading();
                                finish();

                            }

                            @Override
                            public void onError(VolleyError error) {
                                // Error: user not found or other error

                                if(error.networkResponse.statusCode==404) {

                                    Toast.makeText(CheckUserToDelete.this,"Not found", Toast.LENGTH_LONG).show();
                                    Intent intentGoBack=new Intent(CheckUserToDelete.this, DeleteUserActivity.class);
                                    startActivity(intentGoBack);

                                }else {

                                    Toast.makeText(CheckUserToDelete.this,"An error has ocurred", Toast.LENGTH_LONG).show();
                                    Intent intentGoBack=new Intent(CheckUserToDelete.this, DeleteUserActivity.class);
                                    startActivity(intentGoBack);

                                }
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

                    }else{//Delete a Worker

                        showLoading();
                        ManagerWorker mngwk=new ManagerWorker();
                        WorkerDTO workerToFind=new WorkerDTO(dni,null,null,null,null,null,null);

                        mngwk.deleteUser(workerToFind, CheckUserToDelete.this, new UserCallback() {

                            @Override
                            public void onUserReceived(ClientDTO user) {
                            }

                            @Override
                            public void onError(VolleyError error) {

                                // Error: user not found or other error
                                Toast.makeText(CheckUserToDelete.this,"Not found", Toast.LENGTH_LONG).show();
                                Intent intentGoBack=new Intent(CheckUserToDelete.this, DeleteUserActivity.class);
                                startActivity(intentGoBack);
                                hideLoading();
                                finish();

                            }

                            @Override
                            public void onWorkerReceived(WorkerDTO user) {
                                // Worker deleted

                                Toast.makeText(CheckUserToDelete.this,"Worker deleted", Toast.LENGTH_LONG).show();
                                Intent intentGoBack=new Intent(CheckUserToDelete.this, AdminActivity.class);
                                startActivity(intentGoBack);
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

        }else if(!TextUtils.isEmpty(type)){

            if(type.equals("client")){//Delete a Client

                showLoading();
                ManagerClient mngcl=new ManagerClient();
                ClientDTO clientToFind=new ClientDTO(dni,null,null,null,null,null,null);

                mngcl.deleteUser(clientToFind, CheckUserToDelete.this, new UserCallback() {

                    @Override
                    public void onUserReceived(ClientDTO user) {

                        // Client deleted
                        Toast.makeText(CheckUserToDelete.this,"Client deleted", Toast.LENGTH_LONG).show();
                        Intent intentGoBack=new Intent(CheckUserToDelete.this, AdminActivity.class);
                        startActivity(intentGoBack);
                        hideLoading();
                        finish();

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
                })
                ;
            }else{//Delete a Worker

                showLoading();
                ManagerWorker mngwk=new ManagerWorker();
                WorkerDTO workerToFind=new WorkerDTO(dni,null,null,null,null,null,null);
                mngwk.deleteUser(workerToFind, CheckUserToDelete.this, new UserCallback() {
                    @Override
                    public void onUserReceived(ClientDTO user) {
                    }

                    @Override
                    public void onError(VolleyError error) {


                    }

                    @Override
                    public void onWorkerReceived(WorkerDTO user) {


                        Toast.makeText(CheckUserToDelete.this,"Worker deleted", Toast.LENGTH_LONG).show();
                        Intent intentGoBack=new Intent(CheckUserToDelete.this, AdminActivity.class);
                        startActivity(intentGoBack);
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
        }else{

            Intent intentAdmin=new Intent(CheckUserToDelete.this, DeleteUserActivity.class);
            startActivity(intentAdmin);
            Toast.makeText(CheckUserToDelete.this,"Please fill all fields", Toast.LENGTH_LONG).show();
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
