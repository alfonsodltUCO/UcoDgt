package com.example.ucodgt.mvc.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.ucodgt.R;
import com.example.ucodgt.mvc.controller.CheckLogIn;
/**
 * The main activity class for user login.
 * @author Alfonso de la torre
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginButton;
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

        setTheme(R.style.Theme_UcoDgt);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton=findViewById(R.id.checkLogIn);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);
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
        if (id == R.id.checkLogIn) {
            goCheckLogIn();
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
    }
}