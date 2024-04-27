package com.example.ucodgt.mvc.view.worker.vehicle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.text.SimpleDateFormat;

import com.example.ucodgt.mvc.controller.worker.penalty.CheckPenaltiesToListForWorker;
import com.example.ucodgt.mvc.controller.worker.user.CheckUserToSee;
import com.example.ucodgt.mvc.model.business.vehicle.VehicleDTO;
import com.example.ucodgt.mvc.view.worker.WorkerActivity;
import com.example.ucodgt.mvc.view.worker.penalty.AddPenaltyActivity;

/**
 * Activity class to display details of a vehicle for workers.
 * @author Alfonso de la torre
 */
public class ShowVehicle extends AppCompatActivity implements View.OnClickListener {
    String licplate;
    TextView lplate,itv1,itv2,idIns,color,type;
    String numberWorker;
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

        setContentView(com.example.ucodgt.R.layout.show_vehicle_for_worker);
        lplate=findViewById(com.example.ucodgt.R.id.textViewFoundLicencePlate);
        color=findViewById(com.example.ucodgt.R.id.textViewFoundColor);

        type=findViewById(com.example.ucodgt.R.id.textViewCarType);
        idIns=findViewById(com.example.ucodgt.R.id.textViewFoundIdInsurance);
        itv1=findViewById(com.example.ucodgt.R.id.textViewFoundValidItvFrom);
        itv2=findViewById(com.example.ucodgt.R.id.textViewFoundValidItvTo);
        addPenalty=findViewById(com.example.ucodgt.R.id.addPenalty);

        lplate.setText("Licence plate= "+vehicle.getLicencePlate());
        color.setText("Color= "+vehicle.getColor().toString());
        type.setText("Type= "+vehicle.getCarType().toString());
        idIns.setText("Id insurance= "+vehicle.getIdInsurance());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(vehicle.getValidItvFrom());
        itv1.setText("Itv valid from= "+strDate);
        strDate= formatter.format(vehicle.getValidItvTo());

        itv2.setText("Itv valid to= "+strDate);
        goMain=findViewById(com.example.ucodgt.R.id.goMainMenu);
        seeUser=findViewById(com.example.ucodgt.R.id.seeUser);
        addPenalty=findViewById(com.example.ucodgt.R.id.addPenalty);
        goMain.setOnClickListener(this);
        seeUser.setOnClickListener(this);
        addPenalty.setOnClickListener(this);
        licplate=vehicle.getLicencePlate();
        listPenalties=findViewById(com.example.ucodgt.R.id.listPenalties);
        listPenalties.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==com.example.ucodgt.R.id.goMainMenu){

            Intent goMain=new Intent(ShowVehicle.this, WorkerActivity.class);
            goMain.putExtra("numberWorker",numberWorker);
            startActivity(goMain);
            finish();

        }else if(v.getId()==com.example.ucodgt.R.id.seeUser){

            Intent goSee=new Intent(ShowVehicle.this, CheckUserToSee.class);
            goSee.putExtra("licencePlate",licplate);
            goSee.putExtra("numberWorker",numberWorker);
            startActivity(goSee);
            finish();

        } else if (v.getId()==com.example.ucodgt.R.id.listPenalties) {

            Intent goList=new Intent(ShowVehicle.this, CheckPenaltiesToListForWorker.class);
            goList.putExtra("licencePlate",licplate);
            goList.putExtra("numberWorker",numberWorker);
            startActivity(goList);
            finish();

        } else if (v.getId()==com.example.ucodgt.R.id.addPenalty) {

            Intent goAdd=new Intent(ShowVehicle.this, AddPenaltyActivity.class);
            goAdd.putExtra("licencePlate",licplate);
            goAdd.putExtra("numberWorker",numberWorker);
            startActivity(goAdd);
            finish();

        }
    }

}
