package com.uco.ucodgt.mvc.view.admin.vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.uco.ucodgt.mvc.controller.admin.vehicle.CheckVehicleToAdd;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
/**
 * Activity for Admin for adding a new vehicle.
 * @author Alfonso de la torre
 */
public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener{
    Button check,goMenu;
    Spinner spinnerType,spinnerColor;
    String selectedType,selectedColor;
    EditText etLicencePlate,etItvFrom,etItvTo,etDni,etInsurance;
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
        etItvFrom=findViewById(com.uco.ucodgt.R.id.editTextValidItvFrom);
        etItvTo=findViewById(com.uco.ucodgt.R.id.editTextValidItvTo);
        check=findViewById(com.uco.ucodgt.R.id.checkAddVehicle);
        goMenu=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        etDni=findViewById(com.uco.ucodgt.R.id.editTextDniVehicleToAdd);
        etInsurance=findViewById(com.uco.ucodgt.R.id.editTextIdInsurance);

        spinnerType = findViewById(com.uco.ucodgt.R.id.carType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, com.uco.ucodgt.R.array.type, com.uco.ucodgt.R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedType = (String) parentView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerColor = findViewById(com.uco.ucodgt.R.id.colorType);
        ArrayAdapter<CharSequence> adapterColor = ArrayAdapter.createFromResource(this, com.uco.ucodgt.R.array.color, com.uco.ucodgt.R.layout.spinner_item);
        adapterColor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColor.setAdapter(adapterColor);
        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedColor = (String) parentView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        goMenu.setOnClickListener(this);
        check.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if(v.getId()==com.uco.ucodgt.R.id.checkAddVehicle){
            // Prepare data to be sent to the controller

            Intent checkVehicleToAdd=new Intent(AddVehicleActivity.this, CheckVehicleToAdd.class);
            checkVehicleToAdd.putExtra("licenceplate",etLicencePlate.getText().toString().trim());
            checkVehicleToAdd.putExtra("cartype",selectedType);
            checkVehicleToAdd.putExtra("color",selectedColor);
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
