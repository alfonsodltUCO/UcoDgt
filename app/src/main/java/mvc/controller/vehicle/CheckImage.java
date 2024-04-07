package mvc.controller.vehicle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import mvc.model.business.vehicle.ManagerVehicle;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.VehicleCallback;

public class CheckImage extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        showLoading();
        Bitmap image = BitmapFactory.decodeByteArray(
                getIntent().getByteArrayExtra("image"),0,getIntent().getByteArrayExtra("image").length);
        ManagerVehicle mngV=new ManagerVehicle();
        mngV.checkVehicle(image,CheckImage.this, new VehicleCallback() {
            @Override
            public void onVehicleReceived(VehicleDTO vehicle) {
                runOnUiThread(()->{
                    Log.d("aa",vehicle.toString());
                    hideLoading();
                });
            }

            @Override
            public void onError(VolleyError error) {
                runOnUiThread(()->{
                    if(error.networkResponse.statusCode==404){//not found
                        Log.d("aa","not found");
                        hideLoading();

                    }else{
                        Log.d("aa","not recog");
                        hideLoading();
                    }
                });
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
