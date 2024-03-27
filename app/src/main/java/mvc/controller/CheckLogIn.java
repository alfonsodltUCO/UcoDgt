package mvc.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.admin.ManagerAdmin;
import mvc.model.business.user.client.ManagerClient;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.ManagerWorker;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;
import mvc.view.AdminActivity;

public class CheckLogIn extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String email=intent.getStringExtra("email");
        String password=intent.getStringExtra("password");
        ManagerClient mngusr=new ManagerClient();
        ClientDTO client = new ClientDTO(null,password,null,null,null,email,null);
        mngusr.checkLogInClient(client, CheckLogIn.this, new UserCallback() {

            @Override
            public void onUserReceived(ClientDTO user) {//client

                if(user.getEmail()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CheckLogIn.this, "Success Client", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{//admin
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ManagerAdmin mngadm=new ManagerAdmin();
                            AdminDTO admin = new AdminDTO(null,password,null,null,null,email);
                            mngadm.checkLogInAdmin(admin, CheckLogIn.this, new UserCallback() {
                                @Override
                                public void onUserReceived(ClientDTO user) {

                                }

                                @Override
                                public void onError(VolleyError error) {

                                }

                                @Override
                                public void onWorkerReceived(WorkerDTO user) {

                                }

                                @Override
                                public void onAdminReceived(AdminDTO user) {

                                    if(user.getEmail()!=null){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(CheckLogIn.this, "Success", Toast.LENGTH_SHORT).show();
                                                Intent intentAdmin=new Intent(CheckLogIn.this,AdminActivity.class);
                                                intentAdmin.putExtra("type","admin");
                                                startActivity(intentAdmin);
                                            }
                                        });
                                    }else{//worker
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ManagerWorker mngwrk=new ManagerWorker();
                                                WorkerDTO worker = new WorkerDTO(null,password,null,null,null,email,null);
                                                mngwrk.checkLogInWorker(worker, CheckLogIn.this, new UserCallback() {
                                                    @Override
                                                    public void onUserReceived(ClientDTO user) {

                                                    }

                                                    @Override
                                                    public void onError(VolleyError error) {

                                                    }

                                                    @Override
                                                    public void onWorkerReceived(WorkerDTO user) {
                                                        if(user.getEmail()!=null){
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Toast.makeText(CheckLogIn.this, "Success worker", Toast.LENGTH_SHORT).show();

                                                                }
                                                            });
                                                        }else{
                                                            Toast.makeText(CheckLogIn.this, "Fail dddddddd", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }

                                                    @Override
                                                    public void onAdminReceived(AdminDTO user) {

                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                }
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
        });


    }
}