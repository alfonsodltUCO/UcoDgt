package com.uco.ucodgt.mvc.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uco.ucodgt.mvc.controller.CheckDniImage;
import com.uco.ucodgt.mvc.controller.CheckLogIn;
/**
 * The main activity class for user login.
 * If the credentials are not valid the system will not allow the user to enter into the system
 * Depending on credentials user will enter as client, worker or administrator
 * @author Alfonso de la torre
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginButton,registerButton;
    EditText editTextEmail,editTextPassword;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        setTheme(com.uco.ucodgt.R.style.Theme_UcoDgt);
        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.activity_main);
        loginButton=findViewById(com.uco.ucodgt.R.id.checkLogIn);
        editTextEmail=findViewById(com.uco.ucodgt.R.id.editTextEmail);
        editTextPassword=findViewById(com.uco.ucodgt.R.id.editTextPassword);
        registerButton=findViewById(com.uco.ucodgt.R.id.register);

        registerButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id == com.uco.ucodgt.R.id.checkLogIn) {
            goCheckLogIn();
        }else if(id==com.uco.ucodgt.R.id.register){
            Intent intent = new Intent(MainActivity.this, CheckDniImage.class);
            startActivity(intent);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
            Toast.makeText(this,"Put the DNI visible for scanning please",Toast.LENGTH_LONG).show();
        }
    }
    /**
     * Redirects to the check login activity.
     */
    private void goCheckLogIn(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        Intent intent = new Intent(MainActivity.this, CheckLogIn.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
        overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
    }
}