package com.uco.ucodgt.mvc.view.admin.vehicle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import java.text.SimpleDateFormat;

import com.uco.ucodgt.mvc.controller.admin.penalty.CheckPenaltiesToList;
import com.uco.ucodgt.mvc.controller.admin.vehicle.CheckExtendItv;
import com.uco.ucodgt.mvc.controller.admin.vehicle.CheckVehicleToDelete;
import com.uco.ucodgt.mvc.controller.client.user.CheckDataForUpdate;
import com.uco.ucodgt.mvc.model.business.vehicle.VehicleDTO;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
import com.uco.ucodgt.mvc.view.client.user.IntroduceNewData;

/**
 * Activity class to display details of a vehicle for administrators.
 * @author Alfonso de la torre
 */
public class ShowVehicle extends AppCompatActivity implements View.OnClickListener {
    String licplate;
    ImageView image;
    TextView lplate,itv1,itv2,idIns,color,type;
    Button goMain,deleteVehicle,listPenalties,extendItv;
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
        setContentView(com.uco.ucodgt.R.layout.show_vehicle);
        lplate=findViewById(com.uco.ucodgt.R.id.textViewFoundLicencePlate);
        color=findViewById(com.uco.ucodgt.R.id.textViewFoundColor);
        extendItv=findViewById(com.uco.ucodgt.R.id.extendItv);

        type=findViewById(com.uco.ucodgt.R.id.textViewCarType);
        idIns=findViewById(com.uco.ucodgt.R.id.textViewFoundIdInsurance);
        itv1=findViewById(com.uco.ucodgt.R.id.textViewFoundValidItvFrom);
        itv2=findViewById(com.uco.ucodgt.R.id.textViewFoundValidItvTo);

        lplate.setText("Licence plate= "+vehicle.getLicencePlate());
        color.setText("Color= "+vehicle.getColor().toString());
        type.setText("Type= "+vehicle.getCarType().toString());
        idIns.setText("Id insurance= "+vehicle.getIdInsurance());

        image=findViewById(com.uco.ucodgt.R.id.imageShow);
        String carType = vehicle.getCarType().toString().toLowerCase();
        int resourceId = getResources().getIdentifier("drawable/" + carType, null, getPackageName());
        image.setImageResource(resourceId);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(vehicle.getValidItvFrom());
        itv1.setText("Itv valid from= "+strDate);
        strDate= formatter.format(vehicle.getValidItvTo());

        itv2.setText("Itv valid to= "+strDate);
        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        deleteVehicle=findViewById(com.uco.ucodgt.R.id.deleteVehicle);
        goMain.setOnClickListener(this);
        deleteVehicle.setOnClickListener(this);

        licplate=vehicle.getLicencePlate();
        listPenalties=findViewById(com.uco.ucodgt.R.id.listPenalties);
        listPenalties.setOnClickListener(this);
        extendItv.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==com.uco.ucodgt.R.id.goMainMenu){

            Intent goMain=new Intent(ShowVehicle.this, AdminActivity.class);
            startActivity(goMain);
            finish();

        }else if(v.getId()==com.uco.ucodgt.R.id.deleteVehicle){
            showConfirmationDialog();


        } else if (v.getId()==com.uco.ucodgt.R.id.listPenalties) {

            Intent goList=new Intent(ShowVehicle.this, CheckPenaltiesToList.class);
            goList.putExtra("licencePlate",licplate);
            startActivity(goList);
            finish();

        } else if (v.getId()==com.uco.ucodgt.R.id.extendItv) {

            Intent goExtend=new Intent(ShowVehicle.this, CheckExtendItv.class);
            goExtend.putExtra("licencePlate",licplate);
            startActivity(goExtend);
            finish();

        }
    }
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(com.uco.ucodgt.R.layout.confirm_activity, null);
        builder.setView(dialogView);

        TextView textConfirmation = dialogView.findViewById(com.uco.ucodgt.R.id.text_confirmation);
        Button btnConfirm = dialogView.findViewById(com.uco.ucodgt.R.id.btn_confirm);
        Button btnCancel = dialogView.findViewById(com.uco.ucodgt.R.id.btn_cancel);

        final AlertDialog dialog = builder.create();
        dialog.show();

        btnConfirm.setOnClickListener(v -> {

            Intent goDelete=new Intent(ShowVehicle.this, CheckVehicleToDelete.class);
            goDelete.putExtra("licencePlate",licplate);
            startActivity(goDelete);
            finish();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> {


            dialog.dismiss();

        });
    }
}
