package com.uco.ucodgt.mvc.view.admin.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
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

            Intent goDelete = new Intent(DeletePenaltyActivity.this, CheckPenaltyToDelete.class);
            goDelete.putExtra("id",etId.getText().toString());
            startActivity(goDelete);
            finish();

        } else if (v.getId()==com.uco.ucodgt.R.id.goMainMenu) {
            // Navigate back to the main menu

            Intent intentGoMain = new Intent(DeletePenaltyActivity.this, AdminActivity.class);
            startActivity(intentGoMain);
            finish();
        }

    }
}
