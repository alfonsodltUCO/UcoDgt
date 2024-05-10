package com.uco.ucodgt.mvc.view.worker.user;

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

import com.uco.ucodgt.mvc.controller.worker.user.CheckDataForUpdate;
import com.uco.ucodgt.mvc.view.worker.WorkerActivity;

/**
 * This class represents an activity in the worker application where workers can
 * introduce new data, such as email and password, for updating their profile.
 * @author Alfonso de la torre
 */
public class IntroduceNewData extends AppCompatActivity implements View.OnClickListener{

    String numberWorker,dni;

    Button goChange,goMain;

    EditText email,password;

    /**
     * Called when the activity is starting. This method initializes the activity,
     * sets the layout, and initializes UI components.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down, this Bundle contains
     *                           the data it most recently supplied in
     *                           onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numberWorker=getIntent().getStringExtra("numberWorker");
        dni=getIntent().getStringExtra("dni");
        setContentView(com.uco.ucodgt.R.layout.introduce_new_data);

        goChange=findViewById(com.uco.ucodgt.R.id.goConfirm);
        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        email=findViewById(com.uco.ucodgt.R.id.editTextEmail);
        password=findViewById(com.uco.ucodgt.R.id.editTextPassword);

        goMain.setOnClickListener(this);
        goChange.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked. This method handles button clicks.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v.getId()==com.uco.ucodgt.R.id.goConfirm){
            // Proceed to change the data
            showConfirmationDialog();


        } else if (v.getId()==com.uco.ucodgt.R.id.goMainMenu) {
            // Navigate back to the main menu

            Intent intentGoMain = new Intent(IntroduceNewData.this, WorkerActivity.class);
            intentGoMain.putExtra("numberWorker",numberWorker);
            startActivity(intentGoMain);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);

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
            goChange.putExtra("numberWorker",numberWorker);
            goChange.putExtra("email",email.getText().toString());
            goChange.putExtra("password",password.getText().toString());
            goChange.putExtra("dni",dni);
            startActivity(goChange);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);

            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> {


            dialog.dismiss();

        });
    }
}
