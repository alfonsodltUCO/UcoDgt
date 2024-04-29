package com.uco.ucodgt.mvc.view.client.penalty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.uco.ucodgt.mvc.controller.client.penalty.CheckPenaltyToPay;
import com.uco.ucodgt.mvc.view.client.ClientActivity;
/**
 * Activity class to introduce payment data for a penalty.
 * @author Alfonso de la torre
 */
public class IntroduceDataForPay extends AppCompatActivity implements View.OnClickListener {
    String dni,id,quantity;
    TextView quant;
    Button goMain,goFinish;
    EditText etNumber,etCvv,etCaducity;
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
        setContentView(com.uco.ucodgt.R.layout.pay_penalty);
        dni=getIntent().getStringExtra("dni");
        id=getIntent().getStringExtra("id");

        quantity=getIntent().getStringExtra("quantity");
        quant=findViewById(com.uco.ucodgt.R.id.tvQuantityToPut);
        quant.setText(quantity+" With discount= "+ Float.parseFloat(quantity)/2);
        goFinish=findViewById(com.uco.ucodgt.R.id.goPay);
        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);

        etCaducity=findViewById(com.uco.ucodgt.R.id.etCaducity);
        etCvv=findViewById(com.uco.ucodgt.R.id.etCvv);
        etNumber=findViewById(com.uco.ucodgt.R.id.etNumberCard);
        goMain.setOnClickListener(this);
        goFinish.setOnClickListener(this);

    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==com.uco.ucodgt.R.id.goMainMenu){
            // Navigate back to main menu

            Intent goMain=new Intent(IntroduceDataForPay.this, ClientActivity.class);
            goMain.putExtra("dni",dni);
            startActivity(goMain);
            finish();

        }else if(v.getId()==com.uco.ucodgt.R.id.goPay){
            // Proceed to pay the penalty

            Intent goPay=new Intent(IntroduceDataForPay.this, CheckPenaltyToPay.class);
            goPay.putExtra("id", id);
            goPay.putExtra("dni",dni);
            goPay.putExtra("number",etNumber.getText().toString());
            goPay.putExtra("cvv",etCvv.getText().toString());
            goPay.putExtra("caducity",etCaducity.getText().toString());
            startActivity(goPay);
            finish();

        }

    }
}
