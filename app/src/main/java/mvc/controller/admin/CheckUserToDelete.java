package mvc.controller.admin;

import static mvc.controller.commonFunctions.ForCheckUser.checkDni;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.client.ManagerClient;
import mvc.model.business.user.worker.ManagerWorker;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;
import mvc.view.admin.DeleteUserActivity;
import mvc.view.admin.FindUserActivity;

public class CheckUserToDelete extends AppCompatActivity {
    private ProgressBar progressBar;
    String dni,userToDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar = findViewById(R.id.progressbar);
        showLoading();
        Intent intent=getIntent();
        userToDelete=intent.getStringExtra("userToDelete");
        dni=intent.getStringExtra("dni");
        if(!TextUtils.isEmpty(dni) && !TextUtils.isEmpty(userToDelete)){
            if(!checkDni(dni)){//no valid
                Intent intentAdmin=new Intent(CheckUserToDelete.this, DeleteUserActivity.class);
                startActivity(intentAdmin);
                Toast.makeText(CheckUserToDelete.this,"No valid DNI", Toast.LENGTH_LONG).show();
            }else{
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
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
                                    hideLoading();
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
                                        hideLoading();
                                    }else {
                                        Toast.makeText(CheckUserToDelete.this,"An error has ocurred", Toast.LENGTH_LONG).show();
                                        hideLoading();
                                    }
                                }

                                @Override
                                public void onWorkerReceived(WorkerDTO user) {
                                }

                                @Override
                                public void onAdminReceived(AdminDTO user) {

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
                                    hideLoading();


                                }

                                @Override
                                public void onWorkerReceived(WorkerDTO user) {//found
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                    Toast.makeText(CheckUserToDelete.this,"Worker deleted", Toast.LENGTH_LONG).show();
                                    hideLoading();
                                }

                                @Override
                                public void onAdminReceived(AdminDTO user) {

                                }
                            });
                        }
                    }
                });
            }

        }else{
            Intent intentAdmin=new Intent(CheckUserToDelete.this, DeleteUserActivity.class);
            startActivity(intentAdmin);
            Toast.makeText(CheckUserToDelete.this,"Please fill all fields", Toast.LENGTH_LONG).show();
        }


    }
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
