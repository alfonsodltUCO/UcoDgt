package com.example.ucodgt.mvc.view.admin.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ucodgt.mvc.controller.admin.users.CheckUserToAdd;
import com.example.ucodgt.mvc.view.admin.AdminActivity;
/**
 * This activity allows administrators to add users into the system.
 * @author Alfonso de la torre
 */
public class AddUserActivity extends AppCompatActivity implements View.OnClickListener {
    Button checkAddUser,goMenu;
    String typeofuser;
    String selectedOption;
    EditText editTextName,editTextDni,editTextSurname,editTextAge,editTextPassword,editTextLicencePoints,editTextEmail;

    RadioGroup radiogrouptypeuser;
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

        setContentView(com.example.ucodgt.R.layout.add_user);
        editTextAge=findViewById(com.example.ucodgt.R.id.editTextDateBirth);
        editTextEmail=findViewById(com.example.ucodgt.R.id.editTextEmail);
        editTextDni=findViewById(com.example.ucodgt.R.id.editTextDNI);
        editTextName=findViewById(com.example.ucodgt.R.id.editTextName);
        editTextSurname=findViewById(com.example.ucodgt.R.id.editTextSurname);
        checkAddUser=findViewById(com.example.ucodgt.R.id.checkAdd);
        goMenu=findViewById(com.example.ucodgt.R.id.goMainMenu);
        radiogrouptypeuser=findViewById(com.example.ucodgt.R.id.radioGroupTypeUserToAdd);
        editTextLicencePoints=findViewById(com.example.ucodgt.R.id.editTextLicencePoints);
        editTextPassword=findViewById(com.example.ucodgt.R.id.editTextPassword);

        radiogrouptypeuser.setOnCheckedChangeListener((group, checkedId) -> {

            RadioButton radioButton = findViewById(checkedId);
            if (radioButton != null) {
                selectedOption = radioButton.getText().toString().trim();
            }
        });
        goMenu.setOnClickListener(this);
        checkAddUser.setOnClickListener(this);
    }

    /**
     * Handles click events for buttons.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==com.example.ucodgt.R.id.checkAdd){
            //Handle activity to check the addition
           Intent checkUserToAdd=new Intent(AddUserActivity.this, CheckUserToAdd.class);
           checkUserToAdd.putExtra("name",editTextName.getText().toString().trim());
           checkUserToAdd.putExtra("surname",editTextSurname.getText().toString().trim());
           checkUserToAdd.putExtra("dni",editTextDni.getText().toString().trim());
           checkUserToAdd.putExtra("email",editTextEmail.getText().toString().trim());
           checkUserToAdd.putExtra("password",editTextPassword.getText().toString().trim());
           checkUserToAdd.putExtra("age",editTextAge.getText().toString().trim());
           checkUserToAdd.putExtra("licencepoints",editTextLicencePoints.getText().toString().trim());
           checkUserToAdd.putExtra("type",typeofuser);
           checkUserToAdd.putExtra("typeofusertoadd",selectedOption);

           startActivity(checkUserToAdd);

       }else if(v.getId()==com.example.ucodgt.R.id.goMainMenu){
            //Handle the option to go back
            Intent goMenu=new Intent(AddUserActivity.this, AdminActivity.class);
            startActivity(goMenu);

        }
    }
}
