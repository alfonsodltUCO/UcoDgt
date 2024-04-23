package mvc.controller.admin;

import static mvc.controller.commonFunctions.ForCheckUser.checkDni;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import mvc.view.admin.user.FindUserActivity;
import mvc.view.admin.user.ShowUser;

public class CheckUserToFind  extends AppCompatActivity {
    private ProgressBar progressBar;
    String userToFind;
    String dni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar = findViewById(R.id.progressbar);
        showLoading();
        Intent intent=getIntent();
        userToFind=intent.getStringExtra("userToFind");
        dni=intent.getStringExtra("dni");

        if(!TextUtils.isEmpty(dni) && !TextUtils.isEmpty(userToFind)){
            if(!checkDni(dni)){//no valid
                Intent intentAdmin=new Intent(CheckUserToFind.this, FindUserActivity.class);
                startActivity(intentAdmin);
                Toast.makeText(CheckUserToFind.this,"No valid DNI", Toast.LENGTH_LONG).show();
                finish();
            }else{
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    if(userToFind.equals("client")){
                        showLoading();
                        ManagerClient mngcl=new ManagerClient();
                        ClientDTO clientToFind=new ClientDTO(dni,null,null,null,null,null,null);
                        mngcl.getUser(clientToFind, CheckUserToFind.this, new UserCallback() {
                            @Override
                            public void onUserReceived(ClientDTO user) {//recibo un usuario
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                Toast.makeText(CheckUserToFind.this,"Client Found", Toast.LENGTH_LONG).show();
                                Intent intentSeeWorker=new Intent(CheckUserToFind.this, ShowUser.class);
                                intentSeeWorker.putExtra("client", user);
                                intentSeeWorker.putExtra("type","client");
                                startActivity(intentSeeWorker);
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
                    }else{//worker
                        showLoading();
                        ManagerWorker mngwk=new ManagerWorker();
                        WorkerDTO workerToFind=new WorkerDTO(dni,null,null,null,null,null,null);
                        mngwk.getUser(workerToFind, CheckUserToFind.this, new UserCallback() {
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
                                Toast.makeText(CheckUserToFind.this,"Not found", Toast.LENGTH_LONG).show();
                                Intent intentGoBack=new Intent(CheckUserToFind.this, FindUserActivity.class);
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

    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }


}
