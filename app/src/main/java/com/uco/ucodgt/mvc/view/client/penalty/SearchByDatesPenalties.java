package com.uco.ucodgt.mvc.view.client.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.uco.ucodgt.mvc.controller.client.penalty.CheckPenaltiesToListForClient;
import com.uco.ucodgt.mvc.view.client.ClientActivity;

/**
 * Activity class for Client to search for penalties by dates.
 * @author Alfonso de la torre
 */
public class SearchByDatesPenalties extends AppCompatActivity implements View.OnClickListener {
    EditText etDate1,etDate2;
    Button goSearch,goMain;
    String dni;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     * @see AppCompatActivity
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.introduce_penalty_dates);
        dni=getIntent().getStringExtra("dni");
        etDate1=findViewById(com.uco.ucodgt.R.id.etDateStart);

        etDate2=findViewById(com.uco.ucodgt.R.id.etDateEnd);
        goSearch=findViewById(com.uco.ucodgt.R.id.findPenalty);
        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        goMain.setOnClickListener(this);
        goSearch.setOnClickListener(this);
    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==com.uco.ucodgt.R.id.findPenalty){
            // Search for penalties within specified dates

            Intent intentFind = new Intent(SearchByDatesPenalties.this, CheckPenaltiesToListForClient.class);
            intentFind.putExtra("date1",etDate1.getText().toString());
            intentFind.putExtra("date2",etDate2.getText().toString());
            intentFind.putExtra("dni",dni);
            startActivity(intentFind);


        } else if (v.getId()==com.uco.ucodgt.R.id.goMainMenu) {
            // Navigate back to main menu

            Intent intentGoMain = new Intent(SearchByDatesPenalties.this, ClientActivity.class);
            intentGoMain.putExtra("dni",dni);
            startActivity(intentGoMain);


        }
    }
}
