package com.uco.ucodgt.mvc.view.worker.user;

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

import com.uco.ucodgt.mvc.controller.worker.penalty.CheckPenaltiesToListForWorker;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.view.worker.WorkerActivity;

/**
 * Activity  for Worker for displaying user details and providing options to perform actions related to the user.
 * @author Alfonso d la torre
 */
public class ShowUser extends AppCompatActivity implements View.OnClickListener {
    TextView name,surname,email,numberofworker_licencepoints,birth,dni,obtaining;
    String strDate,strDate2;
    String dniNoText;
    ImageView image;


    String numberWorker;
    Button goMenu,listPenalties;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.show_user_for_worker);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        obtaining=findViewById(com.uco.ucodgt.R.id.textViewDateObtaining);
        name=findViewById(com.uco.ucodgt.R.id.textViewFoundName);
        birth=findViewById(com.uco.ucodgt.R.id.textViewFoundDateBirth);
        surname=findViewById(com.uco.ucodgt.R.id.textViewFoundSurname);
        email=findViewById(com.uco.ucodgt.R.id.textViewFoundEmail);
        dni=findViewById(com.uco.ucodgt.R.id.textViewFoundDni);
        goMenu=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        listPenalties=findViewById(com.uco.ucodgt.R.id.listPenalties);
        numberofworker_licencepoints=findViewById(com.uco.ucodgt.R.id.textViewFoundLicencePoints_numberworker);
        image=findViewById(com.uco.ucodgt.R.id.imageShow);
        image.setImageResource(com.uco.ucodgt.R.drawable.userformenu);

        goMenu.setOnClickListener(this);
        listPenalties.setOnClickListener(this);

        numberWorker=getIntent().getStringExtra("numberWorker");
        ClientDTO client = (ClientDTO) getIntent().getSerializableExtra("client");

        assert client != null;
        name.setText("name= "+client.getName());
        surname.setText("surname= "+client.getSurname());
        email.setText("email= "+client.getEmail());
        numberofworker_licencepoints.setText("licence points= "+client.getLicencepoints().toString());

        dni.setText("dni= "+client.getDni());
        dniNoText=client.getDni();
        strDate= formatter.format(client.getAge());
        strDate2= formatter.format(client.getDateLicenceObtaining());
        birth.setText("birth= "+strDate);
        obtaining.setText("Date obtainig licence= "+strDate2);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    public void onClick(View v){

        if(v.getId()==com.uco.ucodgt.R.id.goMainMenu){

            Intent showUser=new Intent(ShowUser.this, WorkerActivity.class);
            showUser.putExtra("numberWorker",numberWorker);
            startActivity(showUser);


        } else if (v.getId()==com.uco.ucodgt.R.id.listPenalties) {

            Intent goList=new Intent(ShowUser.this, CheckPenaltiesToListForWorker.class);
            goList.putExtra("numberWorker",numberWorker);
            goList.putExtra("dni",dniNoText);
            startActivity(goList);


        }
    }
}
