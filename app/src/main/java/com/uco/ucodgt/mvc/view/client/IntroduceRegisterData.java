package com.uco.ucodgt.mvc.view.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uco.ucodgt.mvc.view.MainActivity;
import com.uco.ucodgt.mvc.view.worker.WorkerActivity;
import com.uco.ucodgt.mvc.view.worker.vehicle.ShowVehicle;

public class IntroduceRegisterData extends AppCompatActivity implements View.OnClickListener{

    Button goMain,finish;

    EditText name,surname,birth,email,password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.register_data);

        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        finish=findViewById(com.uco.ucodgt.R.id.finish);
        name=findViewById(com.uco.ucodgt.R.id.editTextName);
        surname=findViewById(com.uco.ucodgt.R.id.editTextSurname);
        birth=findViewById(com.uco.ucodgt.R.id.editTextDateBirth);
        email=findViewById(com.uco.ucodgt.R.id.editTextEmail);
        password=findViewById(com.uco.ucodgt.R.id.editTextPassword);

        goMain.setOnClickListener(this);
        finish.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==com.uco.ucodgt.R.id.goMainMenu){

            Intent goMain=new Intent(IntroduceRegisterData.this, MainActivity.class);
            startActivity(goMain);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);

        }else if(v.getId()==com.uco.ucodgt.R.id.finish){

        }
    }
}
