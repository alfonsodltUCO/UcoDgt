package mvc.controller.vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.io.Serializable;
import java.util.List;

import mvc.controller.admin.CheckUsersToList;
import mvc.model.business.vehicle.ManagerVehicle;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.VehicleCallback;
import mvc.view.admin.AdminActivity;
import mvc.view.admin.ShowUsers;
import mvc.view.vehicle.ShowVehicles;

public class CheckVehiclesToList extends AppCompatActivity {
    ProgressBar progressBar;
    List<VehicleDTO> vehiclelist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        showLoading();
        ManagerVehicle mngV=new ManagerVehicle();
        mngV.getVehicles(CheckVehiclesToList.this, new VehicleCallback() {
            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {

            }

            @Override
            public void onError(VolleyError error) {
                Intent emptyLists = new Intent(CheckVehiclesToList.this, AdminActivity.class);
                startActivity(emptyLists);
                Toast.makeText(CheckVehiclesToList.this,"Not found any vehicle",Toast.LENGTH_LONG).show();
                hideLoading();
                finish();
            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehicles) {
                vehiclelist=vehicles;

                Intent notEmptyLists = new Intent(CheckVehiclesToList.this, ShowVehicles.class);
                notEmptyLists.putExtra("vehicles", (Serializable) vehiclelist);
                Toast.makeText(CheckVehiclesToList.this,"found vehicles",Toast.LENGTH_LONG).show();
                startActivity(notEmptyLists);
                hideLoading();
                finish();
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
