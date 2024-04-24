package mvc.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.util.List;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.admin.ManagerAdmin;
import mvc.model.business.user.client.ManagerClient;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.ManagerWorker;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;
import mvc.view.MainActivity;
import mvc.view.admin.AdminActivity;
import mvc.view.client.ClientActivity;

/**
 * A class created to handle the login of the different types of user.
 * This class extends AppCompatActivity.
 * @author Alfonso de la Torre
 */
public class CheckLogIn extends AppCompatActivity {

    private ProgressBar progressBar;

    /**
     * Called when the activity is starting. This method creates the activity.
     * It initializes the UI elements and retrieves data from the intent,
     * then performs a login check for the user.
     * @param savedInstanceState A Bundle containing the activity's previously saved state, if there was one.
     */
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

            /**
             * Called when the client is successfully retrieved from the database.
             * Shows a loading indicator, waits for 1.5 seconds, then launches the ClientActivity.
             * @param user The client object retrieved from the database.
             */
            @Override
            public void onUserReceived(ClientDTO user) {

                runOnUiThread(() -> {

                    showLoading();

                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Intent intentClient=new Intent(CheckLogIn.this, ClientActivity.class);
                    intentClient.putExtra("dni",user.getDni().toString());
                    startActivity(intentClient);
                    Toast.makeText(CheckLogIn.this,"Successful client",Toast.LENGTH_LONG).show();
                    hideLoading();
                    finish();
                });
            }

            /**
             * Called when an error occurs during the login check for the client.
             * If the client login fails, it checks for admin login.
             * If admin login also fails, it checks for worker login.
             * @param error The error object describing the error that occurred.
             */
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
                        public void onError(VolleyError error) {

                            runOnUiThread(() -> {

                                ManagerWorker mngwrk=new ManagerWorker();
                                WorkerDTO worker = new WorkerDTO(null,password,null,null,null,email,null);
                                mngwrk.checkLogInWorker(worker, CheckLogIn.this, new UserCallback() {

                                    @Override
                                    public void onUserReceived(ClientDTO user) {

                                    }

                                    @Override
                                    public void onError(VolleyError error) {

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
                                        finish();
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
                                startActivity(intentAdmin);
                                hideLoading();
                                Toast.makeText(CheckLogIn.this,"Successful LogIn",Toast.LENGTH_LONG).show();
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
                });
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
     * Shows the progress bar indicator.
     */
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }
    /**
     * Hides the progress bar indicator.
     */
    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
