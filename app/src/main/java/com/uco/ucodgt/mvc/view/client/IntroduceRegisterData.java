package com.uco.ucodgt.mvc.view.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uco.ucodgt.mvc.controller.client.user.CheckUserToAdd;
import com.uco.ucodgt.mvc.view.MainActivity;

/**
 * IntroduceRegisterData is an activity class that allows users to input registration data.
 * This class extends AppCompatActivity and implements View.OnClickListener to handle button clicks.
 */
public class IntroduceRegisterData extends AppCompatActivity implements View.OnClickListener{

    Button goMain,finish;
    String dni;

    EditText name,surname,birth,email,password;

    /**
     * Called when the activity is starting. This is where most initialization should go.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.register_data);
        dni=getIntent().getStringExtra("dni");
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


    /**
     * Called when a view has been clicked.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==com.uco.ucodgt.R.id.goMainMenu){

            Intent goMain=new Intent(IntroduceRegisterData.this, MainActivity.class);
            startActivity(goMain);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);

        }else if(v.getId()==com.uco.ucodgt.R.id.finish){
            Intent goFinish=new Intent(IntroduceRegisterData.this, CheckUserToAdd.class);
            goFinish.putExtra("dni",dni);
            goFinish.putExtra("name",name.getText().toString());
            goFinish.putExtra("surname",surname.getText().toString());
            goFinish.putExtra("email",email.getText().toString());
            goFinish.putExtra("password",password.getText().toString());
            goFinish.putExtra("licencepoints",String.valueOf(8));
            goFinish.putExtra("age",birth.getText().toString());
            startActivity(goFinish);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
        }
    }
}
