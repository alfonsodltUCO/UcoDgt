package mvc.controller.admin;

import static mvc.controller.commonFunctions.ForCheckUser.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import org.mindrot.jbcrypt.BCrypt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.client.ManagerClient;
import mvc.model.business.user.worker.ManagerWorker;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.data.UserCallback;
import mvc.view.admin.user.AddUserActivity;
import mvc.view.admin.AdminActivity;

public class CheckUserToAdd extends AppCompatActivity {
    String name,surname,dni,email,password,age,licencepoints;
    String typeofuserAdded;
    private ProgressBar progressBar;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        showLoading();
        Intent intentReceived=getIntent();
        name=intentReceived.getStringExtra("name");
        surname=intentReceived.getStringExtra("surname");
        email=intentReceived.getStringExtra("email");
        password=intentReceived.getStringExtra("password");
        dni=intentReceived.getStringExtra("dni");
        age=intentReceived.getStringExtra("age");
        licencepoints=intentReceived.getStringExtra("licencepoints");
        typeofuserAdded=intentReceived.getStringExtra("typeofusertoadd");

        if(!TextUtils.isEmpty(dni) && !TextUtils.isEmpty(typeofuserAdded) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(surname) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(licencepoints)){
            if(!checkDni(dni)){//no valid
                Intent intentAdmin=new Intent(CheckUserToAdd.this, AddUserActivity.class);
                startActivity(intentAdmin);
                Toast.makeText(CheckUserToAdd.this,"No valid DNI", Toast.LENGTH_LONG).show();
                finish();
            }else{//valid dni
               if(!checkNameAndSUrname(name,surname)){
                   Intent intentAdmin=new Intent(CheckUserToAdd.this, AddUserActivity.class);
                   startActivity(intentAdmin);
                   Toast.makeText(CheckUserToAdd.this,"No valid input for name/surname", Toast.LENGTH_LONG).show();
                   finish();
               }else{
                   if(!checkLicencePoints(licencepoints)){
                       Intent intentAdmin=new Intent(CheckUserToAdd.this, AddUserActivity.class);
                       startActivity(intentAdmin);//son 8 porque interpretamos que cuando te sacas el carnet te meten en el sistema
                       Toast.makeText(CheckUserToAdd.this,"Number have to be 8 exactly", Toast.LENGTH_LONG).show();
                       finish();
                   }else{
                        checkAdminEmailNotExists(email,CheckUserToAdd.this,new UserCallback(){

                            @Override
                            public void onUserReceived(ClientDTO user) {}
                            @Override
                            public void onError(VolleyError error) {
                                runOnUiThread(() -> checkClientEmailNotExists(email,CheckUserToAdd.this,new UserCallback(){
                                    @Override
                                    public void onUserReceived(ClientDTO user) {
                                        runOnUiThread(() -> {
                                            showLoading();
                                            Intent intentAdmin = new Intent(CheckUserToAdd.this, AddUserActivity.class);
                                            startActivity(intentAdmin);
                                            Toast.makeText(CheckUserToAdd.this, "The email already exists as user", Toast.LENGTH_LONG).show();
                                            hideLoading();
                                            finish();
                                        });
                                    }
                                    @Override
                                    public void onError(VolleyError error1) { runOnUiThread(() -> checkWorkerEmailNotExists(email,CheckUserToAdd.this,new UserCallback(){

                                        @Override
                                        public void onUserReceived(ClientDTO user) {

                                        }

                                        @Override
                                        public void onError(VolleyError error11) {
                                            runOnUiThread(() -> {
                                                if(!checkValidEmail(email)){
                                                    Intent intentAdmin = new Intent(CheckUserToAdd.this, AddUserActivity.class);
                                                    startActivity(intentAdmin);
                                                    Toast.makeText(CheckUserToAdd.this, "The email have incorrect form", Toast.LENGTH_LONG).show();
                                                    finish();
                                                }else{
                                                    if(!checkDateOfBirth(age)){
                                                        Intent intentAdmin = new Intent(CheckUserToAdd.this, AddUserActivity.class);
                                                        startActivity(intentAdmin);
                                                        Toast.makeText(CheckUserToAdd.this, "The user is younger than 18 years old\n"+"or the format is incorrect (yyyy-mm-dd)\n", Toast.LENGTH_LONG).show();
                                                        finish();
                                                    }else{
                                                        if(!checkPassword(password)){
                                                            Intent intentAdmin = new Intent(CheckUserToAdd.this, AddUserActivity.class);
                                                            startActivity(intentAdmin);
                                                            Toast.makeText(CheckUserToAdd.this, "The password must be:\n"+"more that 8 characters\n"+"one capital letter\n"+"one symbol\n"+"one number\n", Toast.LENGTH_LONG).show();
                                                            finish();
                                                        }else{
                                                            if(typeofuserAdded.equals("client")){//client
                                                                ManagerClient mngcl = new ManagerClient();
                                                                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                                                Date dateBirth;
                                                                try {
                                                                    dateBirth = format.parse(age);
                                                                    ClientDTO client = new ClientDTO(dni,BCrypt.hashpw(password,BCrypt.gensalt()),name,surname,dateBirth,email,Integer.parseInt(licencepoints));
                                                                    mngcl.addUser(client,CheckUserToAdd.this,new UserCallback(){

                                                                        @Override
                                                                        public void onUserReceived(ClientDTO user) {
                                                                            runOnUiThread(() -> runOnUiThread(() -> {
                                                                                showLoading();
                                                                                Intent intentAdmin = new Intent(CheckUserToAdd.this, AdminActivity.class);
                                                                                startActivity(intentAdmin);
                                                                                Toast.makeText(CheckUserToAdd.this, "Client added", Toast.LENGTH_LONG).show();
                                                                                hideLoading();
                                                                                finish();
                                                                            }));
                                                                        }

                                                                        @Override
                                                                        public void onError(VolleyError error111) {
                                                                            runOnUiThread(() -> {
                                                                                if(error111.networkResponse.statusCode==500){
                                                                                    showLoading();
                                                                                    Intent intentAdmin = new Intent(CheckUserToAdd.this, AddUserActivity.class);
                                                                                    startActivity(intentAdmin);
                                                                                    Toast.makeText(CheckUserToAdd.this, "The dni already exists", Toast.LENGTH_LONG).show();
                                                                                    hideLoading();
                                                                                    finish();
                                                                                }else{
                                                                                    runOnUiThread(() -> {
                                                                                        showLoading();
                                                                                        Intent intentAdmin = new Intent(CheckUserToAdd.this, AddUserActivity.class);
                                                                                        startActivity(intentAdmin);
                                                                                        Toast.makeText(CheckUserToAdd.this, "An error has happended", Toast.LENGTH_LONG).show();
                                                                                        hideLoading();
                                                                                        finish();
                                                                                    });
                                                                                }
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
                                                                } catch (
                                                                        ParseException e) {
                                                                    throw new RuntimeException(e);
                                                                }

                                                            }else{//worker
                                                                ManagerWorker mngwk = new ManagerWorker();
                                                                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                                                Date dateBirth;
                                                                try {
                                                                    dateBirth = format.parse(age);
                                                                    WorkerDTO worker = new WorkerDTO(dni,BCrypt.hashpw(password,BCrypt.gensalt()),name,surname,dateBirth,email,null);
                                                                    mngwk.addUser(worker,CheckUserToAdd.this,new UserCallback(){

                                                                        @Override
                                                                        public void onUserReceived(ClientDTO user) {

                                                                        }

                                                                        @Override
                                                                        public void onError(VolleyError error111) {
                                                                            runOnUiThread(() -> {
                                                                                if(error111.networkResponse.statusCode==500){
                                                                                    showLoading();
                                                                                    Intent intentAdmin = new Intent(CheckUserToAdd.this, AddUserActivity.class);
                                                                                    startActivity(intentAdmin);
                                                                                    Toast.makeText(CheckUserToAdd.this, "The dni already exists", Toast.LENGTH_LONG).show();
                                                                                    hideLoading();
                                                                                    finish();
                                                                                }else{
                                                                                    runOnUiThread(() -> {
                                                                                        showLoading();
                                                                                        Intent intentAdmin = new Intent(CheckUserToAdd.this, AddUserActivity.class);
                                                                                        startActivity(intentAdmin);
                                                                                        Toast.makeText(CheckUserToAdd.this, "An error has happended", Toast.LENGTH_LONG).show();
                                                                                        hideLoading();
                                                                                        finish();
                                                                                    });
                                                                                }
                                                                            });
                                                                        }

                                                                        @Override
                                                                        public void onWorkerReceived(WorkerDTO user) {
                                                                            runOnUiThread(() -> runOnUiThread(() -> {
                                                                                showLoading();
                                                                                Intent intentAdmin = new Intent(CheckUserToAdd.this, AdminActivity.class);
                                                                                startActivity(intentAdmin);
                                                                                Toast.makeText(CheckUserToAdd.this, "Worker added", Toast.LENGTH_LONG).show();
                                                                                hideLoading();
                                                                                finish();
                                                                            }));
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
                                                                } catch (
                                                                        ParseException e) {
                                                                    throw new RuntimeException(e);
                                                                }

                                                            }
                                                        }
                                                    }
                                                }
                                            });
                                        }

                                        @Override
                                        public void onWorkerReceived(WorkerDTO user) {
                                            runOnUiThread(() -> {
                                                showLoading();
                                                Intent intentAdmin = new Intent(CheckUserToAdd.this, AddUserActivity.class);
                                                startActivity(intentAdmin);
                                                Toast.makeText(CheckUserToAdd.this, "The email already exists as user", Toast.LENGTH_LONG).show();
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
                                    }));}
                                    @Override
                                    public void onWorkerReceived(WorkerDTO user) {}
                                    @Override
                                    public void onAdminReceived(AdminDTO user) {}

                                    @Override
                                    public void onWorkersReceived(List<WorkerDTO> workers) {

                                    }

                                    @Override
                                    public void onClientsReceived(List<ClientDTO> clients) {

                                    }
                                }));
                            }
                            @Override
                            public void onWorkerReceived(WorkerDTO user) {}
                            @Override
                            public void onAdminReceived(AdminDTO user) {
                                runOnUiThread(() -> {
                                    showLoading();
                                    Intent intentAdmin=new Intent(CheckUserToAdd.this, AddUserActivity.class);
                                    startActivity(intentAdmin);
                                    Toast.makeText(CheckUserToAdd.this,"The email already exists as user", Toast.LENGTH_LONG).show();
                                    hideLoading();
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
                   }
               }
            }
        }else{
            Intent intentAdmin=new Intent(CheckUserToAdd.this, AddUserActivity.class);
            startActivity(intentAdmin);
            Toast.makeText(CheckUserToAdd.this,"Please fill all fields", Toast.LENGTH_LONG).show();
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
