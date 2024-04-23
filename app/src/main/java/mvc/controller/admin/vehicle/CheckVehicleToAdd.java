package mvc.controller.admin.vehicle;


import static mvc.controller.commonFunctions.ForCheckUser.checkDni;

import static mvc.controller.commonFunctions.ForCheckVehicle.checkColor;
import static mvc.controller.commonFunctions.ForCheckVehicle.checkDates;
import static mvc.controller.commonFunctions.ForCheckVehicle.checkPlate;
import static mvc.controller.commonFunctions.ForCheckVehicle.checkType;
import static mvc.controller.commonFunctions.ForCheckVehicle.getTypeOf;
import static mvc.controller.commonFunctions.ForCheckVehicle.getTypeOfColor;

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


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import mvc.model.business.user.admin.AdminDTO;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.client.ManagerClient;
import mvc.model.business.user.worker.WorkerDTO;
import mvc.model.business.vehicle.ManagerVehicle;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.UserCallback;
import mvc.model.data.VehicleCallback;
import mvc.view.admin.AdminActivity;
import mvc.view.admin.vehicle.AddVehicleActivity;

public class CheckVehicleToAdd extends AppCompatActivity {
    String licenceplate,color,type,itvfrom,itvto,dni,insurance;

    private ProgressBar progressBar;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        showLoading();
        Intent intentReceived=getIntent();
        licenceplate=intentReceived.getStringExtra("licenceplate");
        color=intentReceived.getStringExtra("color");
        type=intentReceived.getStringExtra("cartype");
        itvfrom=intentReceived.getStringExtra("itvfrom");
        dni=intentReceived.getStringExtra("dni");
        insurance=intentReceived.getStringExtra("insurance");
        itvto=intentReceived.getStringExtra("itvto");


        if(!TextUtils.isEmpty(dni) && !TextUtils.isEmpty(licenceplate) && !TextUtils.isEmpty(color) && !TextUtils.isEmpty(type) && !TextUtils.isEmpty(itvfrom) && !TextUtils.isEmpty(itvto) && !TextUtils.isEmpty(insurance)){
            if(!checkDni(dni)){//no valid
                Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                startActivity(intentAdmin);
                Toast.makeText(CheckVehicleToAdd.this,"No valid DNI", Toast.LENGTH_LONG).show();
                finish();
            }else{//valid dni
                if(!checkPlate(licenceplate)){
                    Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                    startActivity(intentAdmin);
                    Toast.makeText(CheckVehicleToAdd.this,"No valid licence plate", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    if(!checkDates(itvfrom,itvto)){
                        Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                        startActivity(intentAdmin);
                        Toast.makeText(CheckVehicleToAdd.this,"Dates must be first older than second\n And the format is yyyy-mm-dd", Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        if(!checkColor(color)){
                            Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                            startActivity(intentAdmin);
                            Toast.makeText(CheckVehicleToAdd.this,"please enter a valid color", Toast.LENGTH_LONG).show();
                            finish();
                        }else{
                            if(!checkType(type)){
                                Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                                startActivity(intentAdmin);
                                Toast.makeText(CheckVehicleToAdd.this,"please enter a valid type", Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                ManagerClient mngcl=new ManagerClient();
                                ClientDTO cl=new ClientDTO(dni,null,null,null,null,null,null);
                                mngcl.getUser(cl,CheckVehicleToAdd.this,new UserCallback(){

                                    @Override
                                    public void onUserReceived(ClientDTO user) {//existe cliente
                                        runOnUiThread(()->{
                                            ManagerVehicle mngV=new ManagerVehicle();
                                            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                            try {
                                                VehicleDTO vh=new VehicleDTO(licenceplate,getTypeOf(type),getTypeOfColor(color),format.parse(itvfrom),format.parse(itvto),Integer.parseInt(insurance));
                                                mngV.addVehicle(vh,cl, CheckVehicleToAdd.this, new VehicleCallback() {
                                                    @Override
                                                    public void onVehicleReceived(VehicleDTO vehicle) {
                                                        Toast.makeText(CheckVehicleToAdd.this,"added", Toast.LENGTH_LONG).show();
                                                        Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AdminActivity.class);
                                                        startActivity(intentAdmin);
                                                        finish();
                                                    }

                                                    @Override
                                                    public void onError(VolleyError error) {
                                                        if(error.networkResponse.statusCode==400){
                                                            Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                                                            startActivity(intentAdmin);
                                                            Toast.makeText(CheckVehicleToAdd.this,"the vehicle already exists", Toast.LENGTH_LONG).show();
                                                            finish();
                                                        } else if (error.networkResponse.statusCode==404) {
                                                            Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                                                            startActivity(intentAdmin);
                                                            Toast.makeText(CheckVehicleToAdd.this,"the insurance id doesnt exist", Toast.LENGTH_LONG).show();
                                                            finish();
                                                        }
                                                    }

                                                    @Override
                                                    public void onVehiclesReceived(List<VehicleDTO> vehicles) {

                                                    }
                                                });
                                            } catch (ParseException e) {
                                                throw new RuntimeException(e);
                                            }
                                        });
                                    }
                                    @Override
                                    public void onError(VolleyError error) {
                                        Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
                                        startActivity(intentAdmin);
                                        Toast.makeText(CheckVehicleToAdd.this,"Client doesnt exist", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                    @Override
                                    public void onWorkerReceived(WorkerDTO user) {}
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
                        }
                    }
                }
            }
        }else{
            Intent intentAdmin=new Intent(CheckVehicleToAdd.this, AddVehicleActivity.class);
            startActivity(intentAdmin);
            Toast.makeText(CheckVehicleToAdd.this,"Please fill all fields", Toast.LENGTH_LONG).show();
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
