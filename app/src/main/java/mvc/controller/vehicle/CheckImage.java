package mvc.controller.vehicle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.text.SimpleDateFormat;

import mvc.model.business.vehicle.ManagerVehicle;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.VehicleCallback;
import mvc.view.admin.AdminActivity;
import mvc.view.vehicle.GetVehiclePlate;

public class CheckImage extends AppCompatActivity{
    ProgressBar progressBar;
    TextView lplate,itv1,itv2,idIns,color,type;
    Button goMain,deleteVehicle;
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
                    hideLoading();
                    setContentView(R.layout.show_vehicle);
                    lplate=findViewById(R.id.textViewFoundLicencePlate);
                    color=findViewById(R.id.textViewFoundColor);
                    type=findViewById(R.id.textViewCarType);
                    idIns=findViewById(R.id.textViewFoundIdInsurance);
                    itv1=findViewById(R.id.textViewFoundValidItvFrom);
                    itv2=findViewById(R.id.textViewFoundValidItvTo);
                    lplate.setText("Licence plate= "+vehicle.getLicencePlate());
                    color.setText("Colo= "+vehicle.getColor().toString());
                    type.setText("Type= "+vehicle.getCarType().toString());
                    idIns.setText("Id insurance= "+vehicle.getIdInsurance());
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String strDate= formatter.format(vehicle.getValidItvFrom());
                    itv1.setText("Itv valid from= "+strDate);
                    strDate= formatter.format(vehicle.getValidItvTo());
                    itv2.setText("Itv valid to= "+strDate);
                    goMain=findViewById(R.id.goMainMenu);
                    deleteVehicle=findViewById(R.id.deleteVehicle);
                    goMain.setOnClickListener(v -> {
                        Intent goMain=new Intent(CheckImage.this,AdminActivity.class);
                        startActivity(goMain);
                        finish();
                    });

                    deleteVehicle.setOnClickListener(v ->{
                        Intent goDelete=new Intent(CheckImage.this,CheckVehicleToDelete.class);
                        goDelete.putExtra("licencePlate",vehicle.getLicencePlate());
                        startActivity(goDelete);
                        finish();
                    });
                });
            }

            @Override
            public void onError(VolleyError error) {
                runOnUiThread(()->{
                    if(error.networkResponse.statusCode==404){//not found addddd
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
        });

    }


    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
