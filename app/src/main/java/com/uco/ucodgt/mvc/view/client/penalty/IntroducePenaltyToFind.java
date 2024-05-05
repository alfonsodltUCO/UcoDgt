package com.uco.ucodgt.mvc.view.client.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.uco.ucodgt.mvc.controller.client.penalty.CheckPenaltiesToListForClient;
import com.uco.ucodgt.mvc.controller.client.penalty.CheckPenaltyToFindForClient;
import com.uco.ucodgt.mvc.view.client.ClientActivity;
/**
 * Activity class to introduce a penalty to find by ID or state.
 * @author Alfonso de la torre
 */
public class IntroducePenaltyToFind extends AppCompatActivity implements View.OnClickListener {

    EditText etId;
    Button goSearch,goMain,searchByDates;
    RadioGroup rgroup;
    String selectedOption;
    String dni;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     * @see AppCompatActivity
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.introduce_penalty_tofind_for_client);
        etId=findViewById(com.uco.ucodgt.R.id.editTextId);
        goSearch=findViewById(com.uco.ucodgt.R.id.findPenalty);

        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);
        searchByDates=findViewById(com.uco.ucodgt.R.id.searchForDates);
        searchByDates.setOnClickListener(this);
        goMain.setOnClickListener(this);
        rgroup=findViewById(com.uco.ucodgt.R.id.rgState);
        goSearch.setOnClickListener(this);

        rgroup.setOnCheckedChangeListener((group, checkedId) -> {

            RadioButton radioButton = findViewById(checkedId);

            if (radioButton != null) {
                selectedOption = radioButton.getText().toString().trim();
            }
        });
    }
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        dni=getIntent().getStringExtra("dni");
        if(v.getId()==com.uco.ucodgt.R.id.findPenalty){

            if(selectedOption!=null){
                // Search for penalties by state

                Intent intentFind = new Intent(IntroducePenaltyToFind.this, CheckPenaltiesToListForClient.class);
                intentFind.putExtra("state",selectedOption);
                intentFind.putExtra("dni",dni);
                startActivity(intentFind);
                finish();

            }else{
                // Search for penalty by ID

                Intent intentFind = new Intent(IntroducePenaltyToFind.this, CheckPenaltyToFindForClient.class);
                intentFind.putExtra("id",etId.getText().toString());
                intentFind.putExtra("dni",dni);
                startActivity(intentFind);
                finish();
            }

        } else if (v.getId()==com.uco.ucodgt.R.id.goMainMenu) {
            // Navigate back to main menu

            Intent intentGoMain = new Intent(IntroducePenaltyToFind.this, ClientActivity.class);
            intentGoMain.putExtra("dni",dni);
            startActivity(intentGoMain);
            finish();

        }else if(v.getId()==com.uco.ucodgt.R.id.searchForDates){
            // Navigate to search by dates activity

            Intent searchForDates=new Intent(IntroducePenaltyToFind.this, SearchByDatesPenalties.class);
            searchForDates.putExtra("dni",dni);
            startActivity(searchForDates);
            finish();
        }
    }
}
