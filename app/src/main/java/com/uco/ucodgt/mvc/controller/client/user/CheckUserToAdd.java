package com.uco.ucodgt.mvc.controller.client.user;

import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkAdminEmailNotExists;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkClientEmailNotExists;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkDateOfBirth;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkDni;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkLicencePoints;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkNameAndSUrname;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkPassword;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkValidEmail;
import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckUser.checkWorkerEmailNotExists;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.uco.ucodgt.mvc.model.business.user.admin.AdminDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.user.client.ManagerClient;
import com.uco.ucodgt.mvc.model.business.user.worker.ManagerWorker;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.model.data.UserCallback;
import com.uco.ucodgt.mvc.view.MainActivity;
import com.uco.ucodgt.mvc.view.client.ClientActivity;
import com.uco.ucodgt.mvc.view.client.IntroduceRegisterData;

import org.mindrot.jbcrypt.BCrypt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * An activity that after check the data allow Admin to register a user into the system.
 * @author Alfonso de la torre
 */
public class CheckUserToAdd extends AppCompatActivity {
    String name,surname,dni,email,password,age,licencepoints;
    private ProgressBar progressBar;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.loading);
        progressBar=findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();
        Intent intentReceived=getIntent();

        name=intentReceived.getStringExtra("name");
        surname=intentReceived.getStringExtra("surname");
        email=intentReceived.getStringExtra("email");
        password=intentReceived.getStringExtra("password");
        dni=intentReceived.getStringExtra("dni");
        age=intentReceived.getStringExtra("age");
        licencepoints=intentReceived.getStringExtra("licencepoints");

        if(!TextUtils.isEmpty(dni) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(surname) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(licencepoints)){

            if(!checkDni(dni)){//Check DNI format

                Intent intent=new Intent(CheckUserToAdd.this, IntroduceRegisterData.class);
                intent.putExtra("dni",dni);
                try {
                    Thread.sleep(2*1000);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                startActivity(intent);
                overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                finish();
                Log.d("e",dni.toString());
                Toast.makeText(CheckUserToAdd.this,"No valid DNI", Toast.LENGTH_LONG).show();


            }else{

               if(!checkNameAndSUrname(name,surname)){//Check format of name and surname

                   Intent intent=new Intent(CheckUserToAdd.this, IntroduceRegisterData.class);
                   intent.putExtra("dni",dni);
                   try {
                       Thread.sleep(2*1000);
                   }
                   catch (Exception e) {
                       System.out.println(e);
                   }
                   startActivity(intent);
                   overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                   finish();
                   Toast.makeText(CheckUserToAdd.this,"No valid input for name/surname", Toast.LENGTH_LONG).show();


               }else{

                   if(!checkLicencePoints(licencepoints)){//Check licence points

                       Intent intent=new Intent(CheckUserToAdd.this, IntroduceRegisterData.class);
                       intent.putExtra("dni",dni);
                       try {
                           Thread.sleep(2*1000);
                       }
                       catch (Exception e) {
                           System.out.println(e);
                       }
                       startActivity(intent);
                       overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                       finish();
                       Toast.makeText(CheckUserToAdd.this,"Number have to be 8 exactly", Toast.LENGTH_LONG).show();


                   }else{

                        checkAdminEmailNotExists(email,CheckUserToAdd.this,new UserCallback(){//Check if user exists

                            @Override
                            public void onUserReceived(ClientDTO user) {}
                            @Override
                            public void onError(VolleyError error) {
                                runOnUiThread(() -> checkClientEmailNotExists(email,CheckUserToAdd.this,new UserCallback(){
                                    @Override
                                    public void onUserReceived(ClientDTO user) {

                                        runOnUiThread(() -> {

                                            Intent intent=new Intent(CheckUserToAdd.this, IntroduceRegisterData.class);
                                            intent.putExtra("dni",dni);
                                            try {
                                                Thread.sleep(2*1000);
                                            }
                                            catch (Exception e) {
                                                System.out.println(e);
                                            }
                                            startActivity(intent);
                                            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                            finish();
                                            Toast.makeText(CheckUserToAdd.this, "The email already exists as user", Toast.LENGTH_LONG).show();
                                            hideLoading();


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

                                                if(!checkValidEmail(email)){//Chem email format

                                                    Intent intent=new Intent(CheckUserToAdd.this, IntroduceRegisterData.class);
                                                    intent.putExtra("dni",dni);
                                                    try {
                                                        Thread.sleep(2*1000);
                                                    }
                                                    catch (Exception e) {
                                                        System.out.println(e);
                                                    }
                                                    startActivity(intent);
                                                    overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                    finish();
                                                    Toast.makeText(CheckUserToAdd.this, "The email have incorrect form", Toast.LENGTH_LONG).show();


                                                }else{

                                                    if(!checkDateOfBirth(age)){//Check date format and if is older than 18

                                                        Intent intent=new Intent(CheckUserToAdd.this, IntroduceRegisterData.class);
                                                        intent.putExtra("dni",dni);
                                                        try {
                                                            Thread.sleep(2*1000);
                                                        }
                                                        catch (Exception e) {
                                                            System.out.println(e);
                                                        }
                                                        startActivity(intent);
                                                        overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                        finish();
                                                        Toast.makeText(CheckUserToAdd.this, "The user is younger than 18 years old\n"+"or the format is incorrect (yyyy-mm-dd)\n", Toast.LENGTH_LONG).show();


                                                    }else{

                                                        if(!checkPassword(password)){

                                                            Intent intent=new Intent(CheckUserToAdd.this, IntroduceRegisterData.class);
                                                            intent.putExtra("dni",dni);
                                                            try {
                                                                Thread.sleep(2*1000);
                                                            }
                                                            catch (Exception e) {
                                                                System.out.println(e);
                                                            }
                                                            startActivity(intent);
                                                            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                            finish();
                                                            Toast.makeText(CheckUserToAdd.this, "The password must be:\n"+"more that 8 characters\n"+"one capital letter\n"+"one symbol\n"+"one number\n", Toast.LENGTH_LONG).show();


                                                        }else{

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

                                                                            Intent intent=new Intent(CheckUserToAdd.this, ClientActivity.class);
                                                                            intent.putExtra("dni",dni);
                                                                            try {
                                                                                Thread.sleep(2*1000);
                                                                            }
                                                                            catch (Exception e) {
                                                                                System.out.println(e);
                                                                            }
                                                                            startActivity(intent);
                                                                            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                                            finish();
                                                                            Toast.makeText(CheckUserToAdd.this, "Client added", Toast.LENGTH_LONG).show();
                                                                            hideLoading();

                                                                        }));

                                                                    }

                                                                    @Override
                                                                    public void onError(VolleyError error111) {
                                                                        runOnUiThread(() -> {

                                                                            if(error111.networkResponse.statusCode==500){

                                                                                Intent intent=new Intent(CheckUserToAdd.this, IntroduceRegisterData.class);
                                                                                intent.putExtra("dni",dni);
                                                                                try {
                                                                                    Thread.sleep(2*1000);
                                                                                }
                                                                                catch (Exception e) {
                                                                                    System.out.println(e);
                                                                                }
                                                                                startActivity(intent);
                                                                                overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                                                finish();
                                                                                Toast.makeText(CheckUserToAdd.this, "The dni already exists", Toast.LENGTH_LONG).show();
                                                                                hideLoading();


                                                                            }else{

                                                                                runOnUiThread(() -> {

                                                                                    Intent intent=new Intent(CheckUserToAdd.this, IntroduceRegisterData.class);
                                                                                    intent.putExtra("dni",dni);
                                                                                    try {
                                                                                        Thread.sleep(2*1000);
                                                                                    }
                                                                                    catch (Exception e) {
                                                                                        System.out.println(e);
                                                                                    }
                                                                                    startActivity(intent);
                                                                                    overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                                                    finish();
                                                                                    Toast.makeText(CheckUserToAdd.this, "An error has happended", Toast.LENGTH_LONG).show();
                                                                                    hideLoading();

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

                                                        }
                                                    }
                                                }
                                            });
                                        }

                                        @Override
                                        public void onWorkerReceived(WorkerDTO user) {

                                            runOnUiThread(() -> {

                                                Intent intent=new Intent(CheckUserToAdd.this, IntroduceRegisterData.class);
                                                intent.putExtra("dni",dni);
                                                try {
                                                    Thread.sleep(2*1000);
                                                }
                                                catch (Exception e) {
                                                    System.out.println(e);
                                                }
                                                startActivity(intent);
                                                overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                                finish();
                                                Toast.makeText(CheckUserToAdd.this, "The email already exists as user", Toast.LENGTH_LONG).show();
                                                hideLoading();

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
                                    Intent intent=new Intent(CheckUserToAdd.this, IntroduceRegisterData.class);
                                    intent.putExtra("dni",dni);
                                    try {
                                        Thread.sleep(2*1000);
                                    }
                                    catch (Exception e) {
                                        System.out.println(e);
                                    }
                                    startActivity(intent);
                                    overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
                                    finish();
                                    Toast.makeText(CheckUserToAdd.this,"The email already exists as user", Toast.LENGTH_LONG).show();
                                    hideLoading();


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

            Intent intent=new Intent(CheckUserToAdd.this, IntroduceRegisterData.class);
            intent.putExtra("dni",dni);
            try {
                Thread.sleep(2*1000);
            }
            catch (Exception e) {
                System.out.println(e);
            }
            startActivity(intent);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
            finish();
            Toast.makeText(CheckUserToAdd.this,"Please fill all fields", Toast.LENGTH_LONG).show();

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
