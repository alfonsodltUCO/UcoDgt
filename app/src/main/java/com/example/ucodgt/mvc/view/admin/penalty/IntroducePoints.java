package com.example.ucodgt.mvc.view.admin.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.mvc.controller.admin.users.CheckPointsToUpdate;
import com.example.ucodgt.mvc.view.admin.AdminActivity;


/**
 * Activity class for introducing points for a user.
 * @author Alfonso de la torre
 */
public class IntroducePoints extends AppCompatActivity implements View.OnClickListener {

    Button goMain,goUpdate;

    EditText etNumber;

    String dni;


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
        setContentView(com.example.ucodgt.R.layout.introduce_points);

        goMain=findViewById(com.example.ucodgt.R.id.goMainMenu);
        goUpdate=findViewById(com.example.ucodgt.R.id.goUpdate);

        etNumber=findViewById(com.example.ucodgt.R.id.etNumber);

        dni=getIntent().getStringExtra("dni");

        goUpdate.setOnClickListener(this);
        goMain.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==com.example.ucodgt.R.id.goMainMenu){
            // Navigate to the main admin activity

            Intent goMain=new Intent(IntroducePoints.this, AdminActivity.class);
            startActivity(goMain);
            finish();

        }else if(v.getId()==com.example.ucodgt.R.id.goUpdate){
            // Navigate to the CheckPointsToUpdate activity

            Intent goUpdate=new Intent(IntroducePoints.this, CheckPointsToUpdate.class);
            goUpdate.putExtra("dni",dni);
            goUpdate.putExtra("points",etNumber.getText().toString());
            startActivity(goUpdate);
            finish();

        }
    }
}
