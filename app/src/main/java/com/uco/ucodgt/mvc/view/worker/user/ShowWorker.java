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

import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
import com.uco.ucodgt.mvc.view.worker.WorkerActivity;

/**
 * Activity  for Worker for see the info of his/her own.
 * He will be able to change if he want the data.
 * @author Alfonso de la torre
 */
public class ShowWorker extends AppCompatActivity implements View.OnClickListener {
    TextView name,surname,numberofworker,birth,dni,email;
    String strDate;
    String dniNoText;
    String numberWorker;
    Button goMenu;
    ImageView image;


    Button changeData;

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
        setContentView(com.uco.ucodgt.R.layout.show_worker_for_worker);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        name=findViewById(com.uco.ucodgt.R.id.textViewFoundName);
        birth=findViewById(com.uco.ucodgt.R.id.textViewFoundDateBirth);
        surname=findViewById(com.uco.ucodgt.R.id.textViewFoundSurname);
        email=findViewById(com.uco.ucodgt.R.id.textViewFoundEmail);
        dni=findViewById(com.uco.ucodgt.R.id.textViewFoundDni);
        goMenu=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        numberofworker=findViewById(com.uco.ucodgt.R.id.tvNumberWorker);
        changeData=findViewById(com.uco.ucodgt.R.id.changeData);
        image=findViewById(com.uco.ucodgt.R.id.imageShow);
        image.setImageResource(com.uco.ucodgt.R.drawable.trabajador);

        changeData.setOnClickListener(this);
        goMenu.setOnClickListener(this);

        numberWorker=getIntent().getStringExtra("numberWorker");
        WorkerDTO worker = (WorkerDTO) getIntent().getSerializableExtra("worker");

        assert worker != null;
        name.setText("name= \n"+worker.getName());
        surname.setText("surname= \n"+worker.getSurname());
        email.setText("email= \n"+worker.getEmail());
        numberofworker.setText("number of worker= \n"+worker.getNumberOfWorker().toString());

        dni.setText("dni= \n"+worker.getDni());
        dniNoText=worker.getDni();
        strDate= formatter.format(worker.getAge());
        birth.setText("birth= \n"+strDate);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    public void onClick(View v){

        if(v.getId()==com.uco.ucodgt.R.id.goMainMenu){

            Intent showUser=new Intent(ShowWorker.this, WorkerActivity.class);
            showUser.putExtra("numberWorker",numberWorker);
            startActivity(showUser);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);


        }else if(v.getId()==com.uco.ucodgt.R.id.changeData){

            Intent intent=new Intent(ShowWorker.this, IntroduceActualPassword.class);
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);

        }
    }
}
