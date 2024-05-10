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

import com.uco.ucodgt.mvc.controller.admin.users.CheckUserToFind;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
/**
 * Activity for Admin, for finding a user based on criteria.
 * @author Alfonso de la torre
 */
public class FindUserActivity extends AppCompatActivity implements View.OnClickListener {
    RadioGroup radiogrouptypeuser;
    String selectedOption;
    EditText dniToSearch;
    Button search,goMenu;
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
        setContentView(com.uco.ucodgt.R.layout.find_user);
        search=findViewById(com.uco.ucodgt.R.id.findUser);
        radiogrouptypeuser = findViewById(com.uco.ucodgt.R.id.radioGroupTypeUserToFind);
        dniToSearch = findViewById(com.uco.ucodgt.R.id.editTextDniToFind);
        goMenu=findViewById(com.uco.ucodgt.R.id.goMainMenu);

        radiogrouptypeuser.setOnCheckedChangeListener((group, checkedId) -> {

            RadioButton radioButton = findViewById(checkedId);

            if (radioButton != null) {
                selectedOption = radioButton.getText().toString().trim();
            }
        });
        search.setOnClickListener(this);
        goMenu.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==com.uco.ucodgt.R.id.findUser){
            // Start the CheckUserToFind activity to find the user

            Intent goFind=new Intent(FindUserActivity.this, CheckUserToFind.class);
            goFind.putExtra("userToFind",selectedOption);
            goFind.putExtra("dni",dniToSearch.getText().toString().trim());
            startActivity(goFind);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);


        }else if(v.getId()==com.uco.ucodgt.R.id.goMainMenu){
            // Navigate back to the AdminActivity

            Intent goMenu=new Intent(FindUserActivity.this, AdminActivity.class);
            startActivity(goMenu);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);

        }
    }
}
