package com.example.ucodgt.mvc.view.admin.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.ucodgt.mvc.controller.admin.users.CheckUserToDelete;
import com.example.ucodgt.mvc.view.admin.AdminActivity;
/**
 * This activity allows administrators to delete users from the system.
 * @author Alfonso de la torre
 */
public class DeleteUserActivity extends AppCompatActivity implements View.OnClickListener{
    RadioGroup radioGroupTypeOfUser;
    Button deleteUser,goMenu;
    String selectedOption;

    TextView dniToDelete;
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
        setContentView(com.example.ucodgt.R.layout.delete_user);
        radioGroupTypeOfUser=findViewById(com.example.ucodgt.R.id.radioGroupTypeUserToDelete);
        dniToDelete=findViewById(com.example.ucodgt.R.id.editTextDniToDelete);
        deleteUser=findViewById(com.example.ucodgt.R.id.deleteUser);
        goMenu=findViewById(com.example.ucodgt.R.id.goMainMenu);

        radioGroupTypeOfUser.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            if (radioButton != null) {
                selectedOption = radioButton.getText().toString().trim();
            }
        });
        goMenu.setOnClickListener(this);
        deleteUser.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==com.example.ucodgt.R.id.deleteUser){
            // Handle delete user action

            Intent delete=new Intent(DeleteUserActivity.this, CheckUserToDelete.class);
            delete.putExtra("userToDelete",selectedOption);
            delete.putExtra("dni",dniToDelete.getText().toString().trim());
            startActivity(delete);

        }else if(v.getId()==com.example.ucodgt.R.id.goMainMenu){
            // Handle go to main menu action

            Intent goMenu=new Intent(DeleteUserActivity.this, AdminActivity.class);
            startActivity(goMenu);
        }
    }
}
