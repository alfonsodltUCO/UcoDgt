package com.uco.ucodgt.mvc.view.client.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uco.ucodgt.mvc.controller.client.user.CheckClientPassword;
 import com.uco.ucodgt.mvc.view.client.ClientActivity;


/**
 * This activity allows the Client to input their actual password for change his/he own data.
 * It provides options to proceed to check if the actual password is correct or to navigate back to the main menu.
 * @author Alfonso de la torre
 */
public class IntroduceActualPassword extends AppCompatActivity implements View.OnClickListener {

    Button goMain,goCheck;

    String dniRec;
    EditText pass;


    /**
     * Initializes the activity and sets up the UI elements.
     * Retrieves the user's identification number from the intent extras.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, if any.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.introduce_actual_pasword);

        dniRec=getIntent().getStringExtra("dni");

        pass=findViewById(com.uco.ucodgt.R.id.editTextPassword);
        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        goCheck=findViewById(com.uco.ucodgt.R.id.goCheck);

        goCheck.setOnClickListener(this);
        goMain.setOnClickListener(this);
    }

    /**
     * Handles click events for the buttons.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.getId()==com.uco.ucodgt.R.id.goCheck){
            // Proceed to check the actual pas is correct
            Intent goChange = new Intent(IntroduceActualPassword.this, CheckClientPassword.class);
            goChange.putExtra("dni",dniRec);
            goChange.putExtra("password",pass.getText().toString());
            startActivity(goChange);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);


        } else if (v.getId()==com.uco.ucodgt.R.id.goMainMenu) {
            // Navigate back to the main menu

            Intent intentGoMain = new Intent(IntroduceActualPassword.this, ClientActivity.class);
            intentGoMain.putExtra("dni",dniRec);
            startActivity(intentGoMain);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);

        }
    }
}
