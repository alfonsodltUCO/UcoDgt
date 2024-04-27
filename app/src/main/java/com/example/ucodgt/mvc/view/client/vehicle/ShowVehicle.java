package com.example.ucodgt.mvc.view.client.vehicle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import java.text.SimpleDateFormat;

import com.example.ucodgt.mvc.controller.client.penalty.CheckPenaltiesToListForClient;
import com.example.ucodgt.mvc.model.business.vehicle.VehicleDTO;
import com.example.ucodgt.mvc.view.client.ClientActivity;
/**
 * Activity class to display details of a single vehicle for a client.
 */
public class ShowVehicle extends AppCompatActivity implements View.OnClickListener {
    String licplate;
    TextView lplate,itv1,itv2,idIns,color,type;
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
        setContentView(R.layout.show_vehicle_for_client);

        // Initialize views

        lplate=findViewById(R.id.textViewFoundLicencePlate);
        color=findViewById(R.id.textViewFoundColor);
        type=findViewById(R.id.textViewCarType);

        idIns=findViewById(R.id.textViewFoundIdInsurance);
        itv1=findViewById(R.id.textViewFoundValidItvFrom);
        itv2=findViewById(R.id.textViewFoundValidItvTo);

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
        goMain=findViewById(R.id.goMainMenu);
        goMain.setOnClickListener(this);
        licplate=vehicle.getLicencePlate();

        listPenalties=findViewById(R.id.listPenalties);
        listPenalties.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.goMainMenu){

            Intent goMain=new Intent(ShowVehicle.this, ClientActivity.class);
            goMain.putExtra("dni",dni);
            startActivity(goMain);
            finish();

        } else if (v.getId()==R.id.listPenalties) {

            Intent goList=new Intent(ShowVehicle.this, CheckPenaltiesToListForClient.class);
            goList.putExtra("licencePlate",licplate);
            goList.putExtra("dni",dni);
            startActivity(goList);
            finish();

        }
    }

}
