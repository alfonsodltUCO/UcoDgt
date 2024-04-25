package mvc.controller.worker;

import static mvc.controller.commonFunctions.ForCheckVehicle.checkPlate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import mvc.view.worker.WorkerActivity;
import mvc.view.worker.vehicle.IntroduceManual;
import mvc.view.worker.vehicle.ShowVehicle;

/**
 * An activity to check the validity of a vehicle's license plate before attempting to find it in the database.
 * @author Alfosno de la torre
 */
public class CheckVehicleToFindForWorker extends AppCompatActivity {
    private ProgressBar progressBar;
    String licenceplate;
    String numberWorker;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        showLoading();
        numberWorker=getIntent().getStringExtra("numberWorker");
        licenceplate=getIntent().getStringExtra("licenceplate");

        if(!TextUtils.isEmpty(licenceplate)){

            if(!checkPlate(licenceplate)){

                Intent intent=new Intent(CheckVehicleToFindForWorker.this, IntroduceManual.class);
                intent.putExtra("numberWorker",numberWorker);
                startActivity(intent);
                Toast.makeText(CheckVehicleToFindForWorker.this,"No valid licence plate", Toast.LENGTH_LONG).show();
                finish();

            }else{

                ManagerVehicle mngV=new ManagerVehicle();
                VehicleDTO vehicle=new VehicleDTO(licenceplate,null,null,null,null,0);

                mngV.getVehicle(vehicle, CheckVehicleToFindForWorker.this, new VehicleCallback() {
                    @Override
                    public void onVehicleReceived(VehicleDTO vehicle) {

                        Intent goShow=new Intent(CheckVehicleToFindForWorker.this, ShowVehicle.class);
                        goShow.putExtra("vehicle",vehicle);
                        goShow.putExtra("numberWorker",numberWorker);
                        startActivity(goShow);
                        finish();
                        hideLoading();

                    }

                    @Override
                    public void onError(VolleyError error) {

                        Intent goMain=new Intent(CheckVehicleToFindForWorker.this, WorkerActivity.class);
                        goMain.putExtra("numberWorker",numberWorker);
                        Toast.makeText(CheckVehicleToFindForWorker.this,"Not found the vehicle", Toast.LENGTH_LONG).show();
                        startActivity(goMain);
                        finish();
                        hideLoading();

                    }

                    @Override
                    public void onVehiclesReceived(List<VehicleDTO> vehicles) {

                    }
                });
            }
        }else{

            Intent intent=new Intent(CheckVehicleToFindForWorker.this, IntroduceManual.class);
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            Toast.makeText(CheckVehicleToFindForWorker.this,"Fill the field please", Toast.LENGTH_LONG).show();
            finish();
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
