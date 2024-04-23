package mvc.controller.admin;

import static mvc.controller.commonFunctions.ForCheckUser.checkDni;

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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.client.ManagerClient;
import mvc.model.business.user.worker.ManagerWorker;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;
import mvc.view.admin.AdminActivity;
import mvc.view.admin.user.DeleteUserActivity;

public class CheckUserToDelete extends AppCompatActivity {
    private ProgressBar progressBar;
    String dni,userToDelete,type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar = findViewById(R.id.progressbar);
        Intent intent=getIntent();
        showLoading();
        userToDelete=intent.getStringExtra("userToDelete");
        type=intent.getStringExtra("type");
        dni=intent.getStringExtra("dni");
        if(!TextUtils.isEmpty(dni) && !TextUtils.isEmpty(userToDelete)){
            if(!checkDni(dni)){//no valid
                Intent intentAdmin=new Intent(CheckUserToDelete.this, DeleteUserActivity.class);
                startActivity(intentAdmin);
                Toast.makeText(CheckUserToDelete.this,"No valid DNI", Toast.LENGTH_LONG).show();
                finish();
            }else{
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    if(userToDelete.equals("client")){
                        showLoading();
                        ManagerClient mngcl=new ManagerClient();
                        ClientDTO clientToFind=new ClientDTO(dni,null,null,null,null,null,null);
                        mngcl.deleteUser(clientToFind, CheckUserToDelete.this, new UserCallback() {
                            @Override
                            public void onUserReceived(ClientDTO user) {//recibo un usuario
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                Toast.makeText(CheckUserToDelete.this,"Client deleted", Toast.LENGTH_LONG).show();
                                Intent intentGoBack=new Intent(CheckUserToDelete.this, AdminActivity.class);
                                startActivity(intentGoBack);
                                hideLoading();
                                finish();
                            }

                            @Override
                            public void onError(VolleyError error) {//error no encotnrado
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                if(error.networkResponse.statusCode==404) {
                                    Toast.makeText(CheckUserToDelete.this,"Not found", Toast.LENGTH_LONG).show();
                                    Intent intentGoBack=new Intent(CheckUserToDelete.this, DeleteUserActivity.class);
                                    startActivity(intentGoBack);
                                    hideLoading();
                                    finish();
                                }else {
                                    Toast.makeText(CheckUserToDelete.this,"An error has ocurred", Toast.LENGTH_LONG).show();
                                    Intent intentGoBack=new Intent(CheckUserToDelete.this, DeleteUserActivity.class);
                                    startActivity(intentGoBack);
                                    hideLoading();
                                    finish();
                                }
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
                    }else{//worker
                        showLoading();
                        ManagerWorker mngwk=new ManagerWorker();
                        WorkerDTO workerToFind=new WorkerDTO(dni,null,null,null,null,null,null);
                        mngwk.deleteUser(workerToFind, CheckUserToDelete.this, new UserCallback() {
                            @Override
                            public void onUserReceived(ClientDTO user) {
                            }

                            @Override
                            public void onError(VolleyError error) {//error no encotnrado
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                Toast.makeText(CheckUserToDelete.this,"Not found", Toast.LENGTH_LONG).show();
                                Intent intentGoBack=new Intent(CheckUserToDelete.this, DeleteUserActivity.class);
                                startActivity(intentGoBack);
                                hideLoading();
                                finish();
                            }

                            @Override
                            public void onWorkerReceived(WorkerDTO user) {//found
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
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
            if(type.equals("client")){
                showLoading();
                ManagerClient mngcl=new ManagerClient();
                ClientDTO clientToFind=new ClientDTO(dni,null,null,null,null,null,null);
                mngcl.deleteUser(clientToFind, CheckUserToDelete.this, new UserCallback() {
                    @Override
                    public void onUserReceived(ClientDTO user) {//recibo un usuario
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(CheckUserToDelete.this,"Client deleted", Toast.LENGTH_LONG).show();
                        Intent intentGoBack=new Intent(CheckUserToDelete.this, AdminActivity.class);
                        startActivity(intentGoBack);
                        hideLoading();
                        finish();
                    }

                    @Override
                    public void onError(VolleyError error) {//it will not happened

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
            }else{//worker
                showLoading();
                ManagerWorker mngwk=new ManagerWorker();
                WorkerDTO workerToFind=new WorkerDTO(dni,null,null,null,null,null,null);
                mngwk.deleteUser(workerToFind, CheckUserToDelete.this, new UserCallback() {
                    @Override
                    public void onUserReceived(ClientDTO user) {
                    }

                    @Override
                    public void onError(VolleyError error) {//error no encotnrado


                    }

                    @Override
                    public void onWorkerReceived(WorkerDTO user) {//found
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
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
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
