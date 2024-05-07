package com.uco.ucodgt.mvc.view.admin.penalty;

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

import com.uco.ucodgt.mvc.controller.admin.penalty.CheckPenaltyToDelete;
 import com.uco.ucodgt.mvc.view.admin.AdminActivity;

/**
 * Activity to allow administrators to delete a penalty by providing its ID.
 * @author Alfonso de la torre
 */
public class DeletePenaltyActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etId;
    Button goDelete,goMain;
    /**
     * Initializes the activity with UI components and sets up click listeners.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was no saved state.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.delete_penalty);
        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        goDelete=findViewById(com.uco.ucodgt.R.id.deletePenalty);
        etId=findViewById(com.uco.ucodgt.R.id.editTextId);

        goMain.setOnClickListener(this);
        goDelete.setOnClickListener(this);
    }
    /**
     * Handles click events for UI components.
     *
     * @param v The View that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==com.uco.ucodgt.R.id.deletePenalty){
            // Proceed to check and delete the penalty with the provided ID
            showConfirmationDialog();


        } else if (v.getId()==com.uco.ucodgt.R.id.goMainMenu) {
            // Navigate back to the main menu

            Intent intentGoMain = new Intent(DeletePenaltyActivity.this, AdminActivity.class);
            startActivity(intentGoMain);
            finish();
        }

    }

    /**
     * Displays a confirmation dialog to confirm or cancel an action.
     * The dialog contains a confirmation message, a confirm button, and a cancel button.
     * When the confirm button is clicked, a new activity is started to delete a penalty.
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

            Intent goDelete = new Intent(DeletePenaltyActivity.this, CheckPenaltyToDelete.class);
            goDelete.putExtra("id",etId.getText().toString());
            startActivity(goDelete);
            finish();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> {


            dialog.dismiss();

        });
    }
}
