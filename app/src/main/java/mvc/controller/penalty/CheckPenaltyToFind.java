package mvc.controller.penalty;

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

import mvc.controller.vehicle.CheckVehicleToFind;
import mvc.model.business.penalty.ManagerPenalty;
import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.vehicle.ManagerVehicle;
import mvc.model.business.vehicle.VehicleDTO;
import mvc.model.data.PenaltyCallback;
import mvc.model.data.VehicleCallback;
import mvc.view.admin.AdminActivity;
import mvc.view.admin.penalty.ShowPenalty;
import mvc.view.admin.vehicle.IntroduceManual;
import mvc.view.admin.vehicle.ShowVehicle;

public class CheckPenaltyToFind extends AppCompatActivity {
    private ProgressBar progressBar;
    String id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        showLoading();
        id=getIntent().getStringExtra("id");
        if(!TextUtils.isEmpty(id)){
            ManagerPenalty mngP=new ManagerPenalty();
            PenaltyDTO penalty=new PenaltyDTO(Integer.parseInt(id),null,null,null,null,null,null,null,null,null,false,null,null);
            mngP.getPenalty(penalty, CheckPenaltyToFind.this, new PenaltyCallback() {


                @Override
                public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                }

                @Override
                public void onError(VolleyError error) {
                    Intent goMain=new Intent(CheckPenaltyToFind.this, AdminActivity.class);
                    Toast.makeText(CheckPenaltyToFind.this,"Not found the penalty", Toast.LENGTH_LONG).show();
                    startActivity(goMain);
                    finish();
                    hideLoading();
                }

                @Override
                public void onPenaltyReceived(PenaltyDTO penalty) {
                    Intent goShow=new Intent(CheckPenaltyToFind.this, ShowPenalty.class);
                    goShow.putExtra("penalty",penalty);
                    startActivity(goShow);
                    finish();
                    hideLoading();
                }


            });

        }else{
            Intent intentAdmin=new Intent(CheckPenaltyToFind.this, IntroduceManual.class);
            startActivity(intentAdmin);
            Toast.makeText(CheckPenaltyToFind.this,"Fill the field please", Toast.LENGTH_LONG).show();
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
