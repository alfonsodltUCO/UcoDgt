package com.uco.ucodgt.mvc.view.client.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.uco.ucodgt.mvc.controller.client.user.CheckDataForUpdate;
import com.uco.ucodgt.mvc.view.client.ClientActivity;


public class IntroduceNewData extends AppCompatActivity implements View.OnClickListener{

    String dniRec;

    Button goChange,goMain;

    EditText email,password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dniRec=getIntent().getStringExtra("dni");
        setContentView(com.uco.ucodgt.R.layout.introduce_new_data);

        goChange=findViewById(com.uco.ucodgt.R.id.goConfirm);
        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        email=findViewById(com.uco.ucodgt.R.id.editTextEmail);
        password=findViewById(com.uco.ucodgt.R.id.editTextPassword);

        goMain.setOnClickListener(this);
        goChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==com.uco.ucodgt.R.id.goConfirm){
            // Proceed to change the data
            showConfirmationDialog();


        } else if (v.getId()==com.uco.ucodgt.R.id.goMainMenu) {
            // Navigate back to the main menu

            Intent intentGoMain = new Intent(IntroduceNewData.this, ClientActivity.class);
            intentGoMain.putExtra("dni",dniRec);
            startActivity(intentGoMain);
            finish();
        }
    }

    /**
     * Displays a confirmation dialog to confirm or cancel an action.
     * The dialog contains a confirmation message, a confirm button, and a cancel button.
     * When the confirm button is clicked, a new activity is started to change the user data.
     * User information to delete is passed via an Intent.
     * When the cancel button is clicked, the dialog is dismissed.
     */
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(com.uco.ucodgt.R.layout.confirm_activity, null);
        builder.setView(dialogView);

        TextView textConfirmation = dialogView.findViewById(com.uco.ucodgt.R.id.text_confirmation);
        Button btnConfirm = dialogView.findViewById(com.uco.ucodgt.R.id.btn_confirm);
        Button btnCancel = dialogView.findViewById(com.uco.ucodgt.R.id.btn_cancel);

        final AlertDialog dialog = builder.create();
        dialog.show();

        btnConfirm.setOnClickListener(v -> {

            Intent goChange = new Intent(IntroduceNewData.this, CheckDataForUpdate.class);
            goChange.putExtra("dni",dniRec);
            goChange.putExtra("email",email.getText().toString());
            goChange.putExtra("password",password.getText().toString());
            startActivity(goChange);
            finish();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> {


            dialog.dismiss();

        });
    }
}