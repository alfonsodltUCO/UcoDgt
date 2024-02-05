package mvc.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import mvc.model.business.user.admin_and_client.ManagerUser;
import mvc.model.business.user.admin_and_client.UserDTO;
import mvc.model.business.user.typeof;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.admin_and_client.UserCallback;
import mvc.view.AdminActivity;
import mvc.view.MainActivity;

public class CheckLogIn extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String email=intent.getStringExtra("email");
        String password=intent.getStringExtra("password");
        ManagerUser mngusr=new ManagerUser();
        mngusr.checkLogInClient(email, password, CheckLogIn.this, new UserCallback() {
            @Override
            public void onUserReceived(UserDTO user) {//client
                if(user.getEmail()!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CheckLogIn.this, "Success client", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{//admin
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mngusr.checkLogInAdmin(email, password, CheckLogIn.this, new UserCallback() {
                                @Override
                                public void onUserReceived(UserDTO user) {
                                    if(user.getEmail()!=null){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(CheckLogIn.this, "Success admin", Toast.LENGTH_SHORT).show();
                                                Intent intentAdmin=new Intent(CheckLogIn.this,AdminActivity.class);
                                                startActivity(intentAdmin);
                                            }
                                        });
                                    }else{//worker
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mngusr.checkLogInWorker(email, password, CheckLogIn.this, new UserCallback() {
                                                    @Override
                                                    public void onUserReceived(UserDTO user) {

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
