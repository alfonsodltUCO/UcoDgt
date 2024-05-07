package com.uco.ucodgt.mvc.view.client.vehicle;

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

import com.uco.ucodgt.mvc.controller.client.penalty.CheckPenaltiesToListForClient;
import com.uco.ucodgt.mvc.model.business.vehicle.VehicleDTO;
import com.uco.ucodgt.mvc.view.client.ClientActivity;
/**
 * Activity class to display details of a single vehicle for a client.
 */
public class ShowVehicle extends AppCompatActivity implements View.OnClickListener {
    String licplate;
    TextView lplate,itv1,itv2,idIns,color,type;
    ImageView image;

    String dni;
    Button goMain,listPenalties;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     * @see AppCompatActivity
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        VehicleDTO vehicle= (VehicleDTO) getIntent().getSerializableExtra("vehicle");
        dni=getIntent().getStringExtra("dni");
        setContentView(com.uco.ucodgt.R.layout.show_vehicle_for_client);

        // Initialize views

        lplate=findViewById(com.uco.ucodgt.R.id.textViewFoundLicencePlate);
        color=findViewById(com.uco.ucodgt.R.id.textViewFoundColor);
        type=findViewById(com.uco.ucodgt.R.id.textViewCarType);

        idIns=findViewById(com.uco.ucodgt.R.id.textViewFoundIdInsurance);
        itv1=findViewById(com.uco.ucodgt.R.id.textViewFoundValidItvFrom);
        itv2=findViewById(com.uco.ucodgt.R.id.textViewFoundValidItvTo);

        image=findViewById(com.uco.ucodgt.R.id.imageShow);
        String carType = vehicle.getCarType().toString().toLowerCase();
        int resourceId = getResources().getIdentifier("drawable/" + carType, null, getPackageName());
        image.setImageResource(resourceId);

        assert vehicle != null;
        lplate.setText("Licence plate= "+vehicle.getLicencePlate());
        color.setText("Color= "+vehicle.getColor().toString());
        type.setText("Type= "+vehicle.getCarType().toString());
        idIns.setText("Id insurance= "+vehicle.getIdInsurance());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(vehicle.getValidItvFrom());
        itv1.setText("Itv valid from= "+strDate);

        strDate= formatter.format(vehicle.getValidItvTo());
        itv2.setText("Itv valid to= "+strDate);
        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        goMain.setOnClickListener(this);
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

            Intent goMain=new Intent(ShowVehicle.this, ClientActivity.class);
            goMain.putExtra("dni",dni);
            startActivity(goMain);
            finish();

        } else if (v.getId()==com.uco.ucodgt.R.id.listPenalties) {

            Intent goList=new Intent(ShowVehicle.this, CheckPenaltiesToListForClient.class);
            goList.putExtra("licencePlate",licplate);
            goList.putExtra("dni",dni);
            startActivity(goList);
            finish();

        }
    }

}
