package com.uco.ucodgt.mvc.view.admin.vehicle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Activity for Admin for adding a new vehicle.
 * @author Alfonso de la torre
 */
public class AddVehicleActivity extends AppCompatActivity implements View.OnClickListener{
    Button check,goMenu;
    Spinner spinnerType,spinnerColor;
    String selectedType,selectedColor;
    EditText etLicencePlate,etDni;
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
        check=findViewById(com.uco.ucodgt.R.id.checkAddVehicle);
        goMenu=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        etDni=findViewById(com.uco.ucodgt.R.id.editTextDniVehicleToAdd);

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

    /**
     * Handles click events on UI elements.
     *
     * This method is implemented to handle click events on various views in the activity.
     * Depending on the ID of the clicked view, it performs different actions:
     * - If the view clicked is the "Add Vehicle" button, it prepares data and starts the CheckVehicleToAdd activity.
     * - If the view clicked is the "Go to Main Menu" button, it navigates back to the AdminActivity.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==com.uco.ucodgt.R.id.checkAddVehicle){
            // Prepare data to be sent to the controller
            Date now = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String formatedDate = format.format(now);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.YEAR, 4);
            String nowFourYearsLater = format.format(calendar.getTime());
            Intent checkVehicleToAdd=new Intent(AddVehicleActivity.this, CheckVehicleToAdd.class);
            checkVehicleToAdd.putExtra("licenceplate",etLicencePlate.getText().toString().trim());
            checkVehicleToAdd.putExtra("cartype",selectedType);
            checkVehicleToAdd.putExtra("color",selectedColor);
            checkVehicleToAdd.putExtra("itvfrom",formatedDate);
            checkVehicleToAdd.putExtra("itvto",nowFourYearsLater);
            checkVehicleToAdd.putExtra("dni",etDni.getText().toString().trim());
            // Start the CheckVehicleToAdd activity

            startActivity(checkVehicleToAdd);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);

        }else if(v.getId()==com.uco.ucodgt.R.id.goMainMenu){
            // Navigate back to the AdminActivity

            Intent goMenu=new Intent(AddVehicleActivity.this, AdminActivity.class);
            startActivity(goMenu);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
        }
    }
}
