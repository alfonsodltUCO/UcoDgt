package com.uco.ucodgt.mvc.view.worker.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uco.ucodgt.mvc.controller.worker.user.CheckWorkerPassword;
import com.uco.ucodgt.mvc.view.worker.WorkerActivity;


public class IntroduceActualPassword extends AppCompatActivity implements View.OnClickListener {

    Button goMain,goCheck;

    String numberWorker;
    EditText pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.introduce_actual_pasword);

        numberWorker=getIntent().getStringExtra("numberWorker");

        pass=findViewById(com.uco.ucodgt.R.id.editTextPassword);
        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        goCheck=findViewById(com.uco.ucodgt.R.id.goCheck);

        goCheck.setOnClickListener(this);
        goMain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==com.uco.ucodgt.R.id.goCheck){
            // Proceed to check the actual pas is correct
            Intent goChange = new Intent(IntroduceActualPassword.this, CheckWorkerPassword.class);
            goChange.putExtra("numberWorker",numberWorker);
            goChange.putExtra("password",pass.getText().toString());
            startActivity(goChange);
            finish();

        } else if (v.getId()==com.uco.ucodgt.R.id.goMainMenu) {
            // Navigate back to the main menu

            Intent intentGoMain = new Intent(IntroduceActualPassword.this, WorkerActivity.class);
            intentGoMain.putExtra("numberWorker",numberWorker);
            startActivity(intentGoMain);
            finish();
        }
    }
}
