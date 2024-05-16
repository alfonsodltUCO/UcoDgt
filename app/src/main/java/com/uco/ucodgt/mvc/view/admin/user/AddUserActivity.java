package com.uco.ucodgt.mvc.view.admin.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uco.ucodgt.mvc.controller.admin.users.CheckUserToAdd;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
/**
 * This activity allows administrators to add users into the system.
 * @author Alfonso de la torre
 */
public class AddUserActivity extends AppCompatActivity implements View.OnClickListener {
    Button checkAddUser,goMenu;
    String typeofuser;
    String initialPoints="8";
    String selectedOption;
    EditText editTextName,editTextDni,editTextSurname,editTextAge,editTextPassword,editTextEmail;

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

        setContentView(com.uco.ucodgt.R.layout.add_user);
        editTextAge=findViewById(com.uco.ucodgt.R.id.editTextDateBirth);
        editTextEmail=findViewById(com.uco.ucodgt.R.id.editTextEmail);
        editTextDni=findViewById(com.uco.ucodgt.R.id.editTextDNI);
        editTextName=findViewById(com.uco.ucodgt.R.id.editTextName);
        editTextSurname=findViewById(com.uco.ucodgt.R.id.editTextSurname);
        checkAddUser=findViewById(com.uco.ucodgt.R.id.checkAdd);
        goMenu=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        radiogrouptypeuser=findViewById(com.uco.ucodgt.R.id.radioGroupTypeUserToAdd);
        editTextPassword=findViewById(com.uco.ucodgt.R.id.editTextPassword);

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

        if(v.getId()==com.uco.ucodgt.R.id.checkAdd){
            //Handle activity to check the addition
           Intent checkUserToAdd=new Intent(AddUserActivity.this, CheckUserToAdd.class);
           checkUserToAdd.putExtra("name",editTextName.getText().toString().trim());
           checkUserToAdd.putExtra("surname",editTextSurname.getText().toString().trim());
           checkUserToAdd.putExtra("dni",editTextDni.getText().toString().trim());
           checkUserToAdd.putExtra("email",editTextEmail.getText().toString().trim());
           checkUserToAdd.putExtra("password",editTextPassword.getText().toString().trim());
           checkUserToAdd.putExtra("age",editTextAge.getText().toString().trim());
           checkUserToAdd.putExtra("licencepoints",initialPoints);
           checkUserToAdd.putExtra("type",typeofuser);
           checkUserToAdd.putExtra("typeofusertoadd",selectedOption);

           startActivity(checkUserToAdd);
           overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);

       }else if(v.getId()==com.uco.ucodgt.R.id.goMainMenu){
            //Handle the option to go back
            Intent goMenu=new Intent(AddUserActivity.this, AdminActivity.class);
            startActivity(goMenu);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);

        }
    }
}
