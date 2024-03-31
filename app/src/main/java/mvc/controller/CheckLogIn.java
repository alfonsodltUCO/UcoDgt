package mvc.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.admin.ManagerAdmin;
import mvc.model.business.user.client.ManagerClient;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.ManagerWorker;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;
import mvc.view.MainActivity;
import mvc.view.admin.AdminActivity;

public class CheckLogIn extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar = findViewById(R.id.progressbar);
        Intent intent=getIntent();
        String email=intent.getStringExtra("email");
        String password=intent.getStringExtra("password");
        ManagerClient mngusr=new ManagerClient();
        ClientDTO client = new ClientDTO(null,password,null,null,null,email,null);
        mngusr.checkLogInClient(client, CheckLogIn.this, new UserCallback() {

            @Override
            public void onUserReceived(ClientDTO user) {//client
                runOnUiThread(() -> {
                    showLoading();
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Toast.makeText(CheckLogIn.this, "Success Client", Toast.LENGTH_SHORT).show();
                    hideLoading();
                });
            }

            @Override
            public void onError(VolleyError error) {
                runOnUiThread(() -> {

                    ManagerAdmin mngadm=new ManagerAdmin();
                    AdminDTO admin = new AdminDTO(null,password,null,null,null,email);
                    mngadm.checkLogInAdmin(admin, CheckLogIn.this, new UserCallback() {
                        @Override
                        public void onUserReceived(ClientDTO user) {

                        }

                        @Override
                        public void onError(VolleyError error1) {
                            runOnUiThread(() -> {
                                ManagerWorker mngwrk=new ManagerWorker();
                                WorkerDTO worker = new WorkerDTO(null,password,null,null,null,email,null);
                                mngwrk.checkLogInWorker(worker, CheckLogIn.this, new UserCallback() {
                                    @Override
                                    public void onUserReceived(ClientDTO user) {

                                    }

                                    @Override
                                    public void onError(VolleyError error11) {
                                        showLoading();
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                        Toast.makeText(CheckLogIn.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                                        Intent intentGoMain=new Intent(CheckLogIn.this, MainActivity.class);
                                        startActivity(intentGoMain);
                                        hideLoading();
                                    }

                                    @Override
                                    public void onWorkerReceived(WorkerDTO user) {
                                        runOnUiThread(() -> {
                                            showLoading();
                                            try {
                                                Thread.sleep(1500);
                                            } catch (InterruptedException e) {
                                                throw new RuntimeException(e);
                                            }
                                            Toast.makeText(CheckLogIn.this, "Success worker", Toast.LENGTH_SHORT).show();
                                            hideLoading();

                                        });
                                    }

                                    @Override
                                    public void onAdminReceived(AdminDTO user) {

                                    }
                                });
                            });
                        }
                        @Override
                        public void onWorkerReceived(WorkerDTO user) {

                        }

                        @Override
                        public void onAdminReceived(AdminDTO user) {
                            runOnUiThread(() -> {
                                showLoading();
                                try {
                                    Thread.sleep(1500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                Intent intentAdmin=new Intent(CheckLogIn.this,AdminActivity.class);
                                intentAdmin.putExtra("type","admin");
                                startActivity(intentAdmin);
                                hideLoading();
                                Toast.makeText(CheckLogIn.this,"Successful LogIn",Toast.LENGTH_LONG).show();

                            });
                        }
                    });
                });
            }

            @Override
            public void onWorkerReceived(WorkerDTO user) {

            }

            @Override
            public void onAdminReceived(AdminDTO user) {

            }
        });


    }
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}