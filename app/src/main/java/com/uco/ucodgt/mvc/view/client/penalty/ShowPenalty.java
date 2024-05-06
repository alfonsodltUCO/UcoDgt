package com.uco.ucodgt.mvc.view.client.penalty;

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

import com.uco.ucodgt.mvc.controller.client.penalty.CheckPenaltyToPay;
import com.uco.ucodgt.mvc.model.business.penalty.PenaltyDTO;
import com.uco.ucodgt.mvc.view.client.ClientActivity;
/**
 * Activity class to display details of a penalty for a client.
 * @author Alfonso de la torre
 */
public class ShowPenalty extends AppCompatActivity implements View.OnClickListener{
    String idtoshow;
    TextView id,description,dniw,dnic,quant,points,date,state,reason,licenceP;
    Button goMain,payPenalty;

    ImageView image;

    PenaltyDTO penalty;
    String dni,workerNum;


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


        penalty=(PenaltyDTO)getIntent().getSerializableExtra("penalty");
        dni=getIntent().getStringExtra("dni");
        workerNum=getIntent().getStringExtra("worker");
        setContentView(com.uco.ucodgt.R.layout.show_penalty_for_client);

        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        payPenalty=findViewById(com.uco.ucodgt.R.id.payPenalty);
        id=findViewById(com.uco.ucodgt.R.id.tvId);
        date=findViewById(com.uco.ucodgt.R.id.tvDate);

        dnic=findViewById(com.uco.ucodgt.R.id.tvDniC);
        dniw=findViewById(com.uco.ucodgt.R.id.tvDniW);
        description=findViewById(com.uco.ucodgt.R.id.tvDescription);
        state=findViewById(com.uco.ucodgt.R.id.tvState);

        reason=findViewById(com.uco.ucodgt.R.id.tvReason);
        licenceP=findViewById(com.uco.ucodgt.R.id.tvLicenceP);
        idtoshow=id.getText().toString();

        quant=findViewById(com.uco.ucodgt.R.id.tvQuantity);
        points=findViewById(com.uco.ucodgt.R.id.tvPoints);
        id.setText("id= "+penalty.getId().toString());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(penalty.getDate());
        date.setText("date= "+strDate);

        dniw.setText("number of Worker= "+workerNum);
        dnic.setText("dni Client= "+penalty.getDniClient());

        description.setText(penalty.getDescription());
        state.setText("state= "+penalty.getState().toString());
        reason.setText("reason= "+penalty.getReason().toString());
        description.setText("description= "+penalty.getDescription());

        points.setText("points= "+penalty.getPoints().toString());
        quant.setText("quantity= "+ penalty.getQuantity().toString());
        licenceP.setText("plate= "+ penalty.getLicenceplate());

        image=findViewById(com.uco.ucodgt.R.id.imageShow);
        String reason = penalty.getReason().toString().toLowerCase();
        int resourceId = getResources().getIdentifier("drawable/" + reason, null, getPackageName());
        image.setImageResource(resourceId);

        goMain.setOnClickListener(this);
        payPenalty.setOnClickListener(this);
    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==com.uco.ucodgt.R.id.goMainMenu){
            // Navigate to main menu

            Intent goMain=new Intent(ShowPenalty.this, ClientActivity.class);
            goMain.putExtra("dni",dni);
            startActivity(goMain);
            finish();

        }else if(v.getId()==com.uco.ucodgt.R.id.payPenalty){
            // Navigate to payment screen for the penalty
            Intent goPay=new Intent(ShowPenalty.this, CheckPenaltyToPay.class);
            goPay.putExtra("id", penalty.getId().toString());
            goPay.putExtra("dni",dni);
            goPay.putExtra("quantity",penalty.getQuantity().toString());
            startActivity(goPay);
            finish();

        }
    }

}
