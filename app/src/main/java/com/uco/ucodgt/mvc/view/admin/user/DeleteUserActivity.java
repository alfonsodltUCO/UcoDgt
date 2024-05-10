package com.uco.ucodgt.mvc.view.admin.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.uco.ucodgt.mvc.controller.admin.users.CheckUserToDelete;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
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
        setContentView(com.uco.ucodgt.R.layout.delete_user);
        radioGroupTypeOfUser=findViewById(com.uco.ucodgt.R.id.radioGroupTypeUserToDelete);
        dniToDelete=findViewById(com.uco.ucodgt.R.id.editTextDniToDelete);
        deleteUser=findViewById(com.uco.ucodgt.R.id.deleteUser);
        goMenu=findViewById(com.uco.ucodgt.R.id.goMainMenu);

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

        if(v.getId()==com.uco.ucodgt.R.id.deleteUser){
            // Handle delete user action
            showConfirmationDialog();

        }else if(v.getId()==com.uco.ucodgt.R.id.goMainMenu){
            // Handle go to main menu action

            Intent goMenu=new Intent(DeleteUserActivity.this, AdminActivity.class);
            startActivity(goMenu);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
        }
    }

    /**
     * Displays a confirmation dialog to confirm or cancel an action.
     * The dialog contains a confirmation message, a confirm button, and a cancel button.
     * When the confirm button is clicked, a new activity is started to delete a user.
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

            Intent delete=new Intent(DeleteUserActivity.this, CheckUserToDelete.class);
            delete.putExtra("userToDelete",selectedOption);
            delete.putExtra("dni",dniToDelete.getText().toString().trim());
            startActivity(delete);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> {


            dialog.dismiss();

        });
    }
}
