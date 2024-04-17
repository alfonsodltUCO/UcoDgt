package mvc.controller.vehicle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.util.List;

import mvc.model.business.vehicle.ManagerVehicle;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.VehicleCallback;
import mvc.view.admin.AdminActivity;
import mvc.view.admin.vehicle.ShowVehicle;

public class CheckImage extends AppCompatActivity{
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        showLoading();
        Bitmap image = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("image"),0,getIntent().getByteArrayExtra("image").length);
        ManagerVehicle mngV=new ManagerVehicle();
        mngV.checkVehicle(image,CheckImage.this, new VehicleCallback() {
            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {
                runOnUiThread(()->{
                    hideLoading();
                    Intent goShow=new Intent(CheckImage.this, ShowVehicle.class);
                    goShow.putExtra("vehicle",vehicle);
                    startActivity(goShow);
                    finish();
                });
            }

            @Override
            public void onError(VolleyError error) {
                runOnUiThread(()->{
                    if(error.networkResponse.statusCode==404){
                        Intent goAdd=new Intent(CheckImage.this, AdminActivity.class);
                        Toast.makeText(CheckImage.this,"Not found this vehicle on Data Base", Toast.LENGTH_LONG).show();
                        startActivity(goAdd);
                        hideLoading();
                        finish();
                    }else{
                        Intent goMain=new Intent(CheckImage.this, AdminActivity.class);
                        Toast.makeText(CheckImage.this,"Not licence plate recognized", Toast.LENGTH_LONG).show();
                        startActivity(goMain);
                        hideLoading();
                        finish();
                    }
                });
            }

            @Override
            public void onVehiclesReceived(List<VehicleDTO> vehicles) {

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
