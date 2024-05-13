package com.uco.ucodgt.mvc.view.worker.vehicle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.text.SimpleDateFormat;

import com.uco.ucodgt.mvc.controller.worker.penalty.CheckPenaltiesToListForWorker;
import com.uco.ucodgt.mvc.controller.worker.user.CheckUserToSee;
import com.uco.ucodgt.mvc.model.business.vehicle.VehicleDTO;
import com.uco.ucodgt.mvc.view.worker.WorkerActivity;
import com.uco.ucodgt.mvc.view.worker.penalty.AddPenaltyActivity;

/**
 * Activity class to display details of a vehicle for workers.
 * The worker will be able to do an action with de vehicle that is shown
 * @author Alfonso de la torre
 */
public class ShowVehicle extends AppCompatActivity implements View.OnClickListener {
    String licplate;
    TextView lplate,itv1,itv2,idIns,color,type;
    String numberWorker;
    ImageView image;

    Button goMain,addPenalty,listPenalties,seeUser;

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        VehicleDTO vehicle= (VehicleDTO) getIntent().getSerializableExtra("vehicle");
        numberWorker=getIntent().getStringExtra("numberWorker");

        setContentView(com.uco.ucodgt.R.layout.show_vehicle_for_worker);
        lplate=findViewById(com.uco.ucodgt.R.id.textViewFoundLicencePlate);
        color=findViewById(com.uco.ucodgt.R.id.textViewFoundColor);

        type=findViewById(com.uco.ucodgt.R.id.textViewCarType);
        idIns=findViewById(com.uco.ucodgt.R.id.textViewFoundIdInsurance);
        itv1=findViewById(com.uco.ucodgt.R.id.textViewFoundValidItvFrom);
        itv2=findViewById(com.uco.ucodgt.R.id.textViewFoundValidItvTo);
        addPenalty=findViewById(com.uco.ucodgt.R.id.addPenalty);

        lplate.setText("Licence plate= "+vehicle.getLicencePlate());
        color.setText("Color= "+vehicle.getColor().toString());
        type.setText("Type= "+vehicle.getCarType().toString());
        idIns.setText("Id insurance= "+vehicle.getIdInsurance());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(vehicle.getValidItvFrom());
        itv1.setText("Itv valid from= "+strDate);
        strDate= formatter.format(vehicle.getValidItvTo());
        itv2.setText("Itv valid to= "+strDate);

        image=findViewById(com.uco.ucodgt.R.id.imageShow);
        String carType = vehicle.getCarType().toString().toLowerCase();
        int resourceId = getResources().getIdentifier("drawable/" + carType, null, getPackageName());
        image.setImageResource(resourceId);

        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        seeUser=findViewById(com.uco.ucodgt.R.id.seeUser);
        addPenalty=findViewById(com.uco.ucodgt.R.id.addPenalty);
        goMain.setOnClickListener(this);
        seeUser.setOnClickListener(this);
        addPenalty.setOnClickListener(this);
        licplate=vehicle.getLicencePlate();
        listPenalties=findViewById(com.uco.ucodgt.R.id.listPenalties);
        listPenalties.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==com.uco.ucodgt.R.id.goMainMenu){

            Intent goMain=new Intent(ShowVehicle.this, WorkerActivity.class);
            goMain.putExtra("numberWorker",numberWorker);
            startActivity(goMain);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);


        }else if(v.getId()==com.uco.ucodgt.R.id.seeUser){

            Intent goSee=new Intent(ShowVehicle.this, CheckUserToSee.class);
            goSee.putExtra("licencePlate",licplate);
            goSee.putExtra("numberWorker",numberWorker);
            startActivity(goSee);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);


        } else if (v.getId()==com.uco.ucodgt.R.id.listPenalties) {

            Intent goList=new Intent(ShowVehicle.this, CheckPenaltiesToListForWorker.class);
            goList.putExtra("licencePlate",licplate);
            goList.putExtra("numberWorker",numberWorker);
            startActivity(goList);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);


        } else if (v.getId()==com.uco.ucodgt.R.id.addPenalty) {

            Intent goAdd=new Intent(ShowVehicle.this, AddPenaltyActivity.class);
            goAdd.putExtra("licencePlate",licplate);
            goAdd.putExtra("numberWorker",numberWorker);
            startActivity(goAdd);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);


        }
    }

}
