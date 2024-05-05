package com.uco.ucodgt.mvc.view.admin.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.uco.ucodgt.mvc.controller.admin.penalty.CheckPenaltiesToList;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
/**
 * Activity to allow administrators to search for penalties by specifying start and end dates.
 * @author Alfonso de la torre
 */
public class SearchByDatesPenalties extends AppCompatActivity implements View.OnClickListener {
    EditText etDate1,etDate2;
    Button goSearch,goMain;
    /**
     * Initializes the activity with UI components and sets up click listeners.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was no saved state.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.introduce_penalty_dates);
        etDate1=findViewById(com.uco.ucodgt.R.id.etDateStart);
        etDate2=findViewById(com.uco.ucodgt.R.id.etDateEnd);
        goSearch=findViewById(com.uco.ucodgt.R.id.findPenalty);
        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);

        goMain.setOnClickListener(this);
        goSearch.setOnClickListener(this);
    }
    /**
     * Handles click events for UI components.
     *
     * @param v The View that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==com.uco.ucodgt.R.id.findPenalty){
            // Initiates a search for penalties based on the specified dates

            Intent intentFind = new Intent(SearchByDatesPenalties.this, CheckPenaltiesToList.class);
            intentFind.putExtra("date1",etDate1.getText().toString());
            intentFind.putExtra("date2",etDate2.getText().toString());
            startActivity(intentFind);
            finish();

        } else if (v.getId()==com.uco.ucodgt.R.id.goMainMenu) {
            // Navigates back to the main menu

            Intent intentGoMain = new Intent(SearchByDatesPenalties.this, AdminActivity.class);
            startActivity(intentGoMain);
            finish();
        }
    }
}
