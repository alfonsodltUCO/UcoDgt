package com.example.ucodgt.mvc.view.admin.vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.R;

import com.example.ucodgt.mvc.controller.admin.vehicle.CheckVehicleToDelete;
import com.example.ucodgt.mvc.view.admin.AdminActivity;
/**
 * Activity class for deleting a vehicle.
 * @author Alfonso de la torre
 */
public class DeleteVehicleActivity extends AppCompatActivity implements View.OnClickListener{

    Button deleteVehicle,goMain;
    EditText licencePlate;
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
        setContentView(R.layout.delete_vehicle);
        licencePlate=findViewById(R.id.editTextPlateToDelete);
        deleteVehicle=findViewById(R.id.deleteVehicle);
        goMain=findViewById(R.id.goMainMenu);

        deleteVehicle.setOnClickListener(this);
        goMain.setOnClickListener(this);
    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.goMainMenu){

            Intent goMain=new Intent(DeleteVehicleActivity.this, AdminActivity.class);
            startActivity(goMain);
            finish();

        }else if(v.getId()==R.id.deleteVehicle){

            Intent goDelete=new Intent(DeleteVehicleActivity.this, CheckVehicleToDelete.class);
            goDelete.putExtra("licencePlate",licencePlate.getText().toString().trim());
            startActivity(goDelete);
            finish();

        }
    }
}