package com.uco.ucodgt.mvc.view.admin.vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.uco.ucodgt.mvc.controller.admin.vehicle.CheckVehicleToAdd;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
/**
 * Activity for adding a new vehicle.
 * @author Alfonso de la torre
 */
public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener{
    Button check,goMenu;
    EditText etLicencePlate,etCarType,etColor,etItvFrom,etItvTo,etDni,etInsurance;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.add_vehicle);

        etLicencePlate=findViewById(com.uco.ucodgt.R.id.editTextLicencePlate);
        etCarType=findViewById(com.uco.ucodgt.R.id.editTextCarType);
        etColor=findViewById(com.uco.ucodgt.R.id.editTextColor);
        etItvFrom=findViewById(com.uco.ucodgt.R.id.editTextValidItvFrom);
        etItvTo=findViewById(com.uco.ucodgt.R.id.editTextValidItvTo);
        check=findViewById(com.uco.ucodgt.R.id.checkAddVehicle);
        goMenu=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        etDni=findViewById(com.uco.ucodgt.R.id.editTextDniVehicleToAdd);
        etInsurance=findViewById(com.uco.ucodgt.R.id.editTextIdInsurance);
        goMenu.setOnClickListener(this);
        check.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if(v.getId()==com.uco.ucodgt.R.id.checkAddVehicle){
            // Prepare data to be sent to the controller

            Intent checkVehicleToAdd=new Intent(AddVehicleActivity.this, CheckVehicleToAdd.class);
            checkVehicleToAdd.putExtra("licenceplate",etLicencePlate.getText().toString().trim());
            checkVehicleToAdd.putExtra("cartype",etCarType.getText().toString().trim());
            checkVehicleToAdd.putExtra("color",etColor.getText().toString().trim());
            checkVehicleToAdd.putExtra("itvfrom",etItvFrom.getText().toString().trim());
            checkVehicleToAdd.putExtra("itvto",etItvTo.getText().toString().trim());
            checkVehicleToAdd.putExtra("dni",etDni.getText().toString().trim());
            checkVehicleToAdd.putExtra("insurance",etInsurance.getText().toString().trim());
            // Start the CheckVehicleToAdd activity

            startActivity(checkVehicleToAdd);

        }else if(v.getId()==com.uco.ucodgt.R.id.goMainMenu){
            // Navigate back to the AdminActivity

            Intent goMenu=new Intent(AddVehicleActivity.this, AdminActivity.class);
            startActivity(goMenu);
        }
    }
}
