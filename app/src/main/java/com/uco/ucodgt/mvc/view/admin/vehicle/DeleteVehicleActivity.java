package com.uco.ucodgt.mvc.view.admin.vehicle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.uco.ucodgt.R;
 import com.uco.ucodgt.mvc.controller.admin.vehicle.CheckVehicleToDelete;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
import com.uco.ucodgt.mvc.view.admin.penalty.DeletePenaltyActivity;

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
        setContentView(com.uco.ucodgt.R.layout.delete_vehicle);
        licencePlate=findViewById(com.uco.ucodgt.R.id.editTextPlateToDelete);
        deleteVehicle=findViewById(com.uco.ucodgt.R.id.deleteVehicle);
        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);

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

        if(v.getId()==com.uco.ucodgt.R.id.goMainMenu){

          showConfirmationDialog();

        }else if(v.getId()==com.uco.ucodgt.R.id.deleteVehicle){

            Intent goDelete=new Intent(DeleteVehicleActivity.this, CheckVehicleToDelete.class);
            goDelete.putExtra("licencePlate",licencePlate.getText().toString().trim());
            startActivity(goDelete);
            finish();

        }
    }

    /**
     * Displays a confirmation dialog to confirm or cancel an action.
     * The dialog contains a confirmation message, a confirm button, and a cancel button.
     * When the confirm button is clicked, a new activity is started to delete a penalty.
     * User information to delete is passed via an Intent.
     * When the cancel button is clicked, the dialog is dismissed.
     */
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.confirm_activity, null);
        builder.setView(dialogView);

        TextView textConfirmation = dialogView.findViewById(com.uco.ucodgt.R.id.text_confirmation);
        Button btnConfirm = dialogView.findViewById(com.uco.ucodgt.R.id.btn_confirm);
        Button btnCancel = dialogView.findViewById(com.uco.ucodgt.R.id.btn_cancel);

        final AlertDialog dialog = builder.create();
        dialog.show();

        btnConfirm.setOnClickListener(v -> {

            Intent goMain=new Intent(DeleteVehicleActivity.this, AdminActivity.class);
            startActivity(goMain);
            finish();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> {


            dialog.dismiss();

        });
    }
}
