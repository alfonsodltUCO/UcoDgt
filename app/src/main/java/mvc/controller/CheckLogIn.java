package mvc.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import mvc.model.business.user.client.ManagerUserLogIn;
import mvc.model.business.user.client.ClientDTO;
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
        ManagerUserLogIn mngusr=new ManagerUserLogIn();
        mngusr.checkLogInClient(email, password, CheckLogIn.this, new UserCallback() {
            @Override
            public void onUserReceived(ClientDTO user) {//client
                if(user.getEmail()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CheckLogIn.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{//admin
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mngusr.checkLogInAdmin(email, password, CheckLogIn.this, new UserCallback() {
                                @Override
                                public void onUserReceived(ClientDTO user) {
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
                                                mngusr.checkLogInWorker(email, password, CheckLogIn.this, new UserCallback() {
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
                                                                    Toast.makeText(CheckLogIn.this, "Success", Toast.LENGTH_SHORT).show();

                                                                }
                                                            });
                                                        }else{
                                                            Toast.makeText(CheckLogIn.this, "Fail", Toast.LENGTH_SHORT).show();

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
        });


    }
}
