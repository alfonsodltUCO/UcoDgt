package com.uco.ucodgt.mvc.view.admin.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.uco.ucodgt.mvc.controller.admin.penalty.CheckPenaltiesToList;
import com.uco.ucodgt.mvc.controller.admin.penalty.CheckPenaltyToFind;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
/**
 * Activity to allow administrators to introduce penalty details for searching penalties.
 * @author Alfonso de la torre
 */
public class IntroducePenaltyForSearch extends AppCompatActivity implements View.OnClickListener {
    EditText etId;
    Button goSearch,goMain,searchByDates;
    RadioGroup rgroup;
    String selectedOption;
    /**
     * Initializes the activity with UI components and sets up click listeners.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was no saved state.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.introduce_penalty_id);
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
     * Handles click events for UI components.
     *
     * @param v The View that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==com.uco.ucodgt.R.id.findPenalty){

            if(selectedOption!=null){
                // Search penalties based on selected state

                Intent intentFind = new Intent(IntroducePenaltyForSearch.this, CheckPenaltiesToList.class);
                intentFind.putExtra("state",selectedOption);
                startActivity(intentFind);
                overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);


            }else{
                // Search penalties by specific ID

                Intent intentFind = new Intent(IntroducePenaltyForSearch.this, CheckPenaltyToFind.class);
                intentFind.putExtra("id",etId.getText().toString());
                startActivity(intentFind);
                overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);


            }
        } else if (v.getId()==com.uco.ucodgt.R.id.goMainMenu) {
            // Navigate back to the main menu

            Intent intentGoMain = new Intent(IntroducePenaltyForSearch.this, AdminActivity.class);
            startActivity(intentGoMain);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);


        }else if(v.getId()==com.uco.ucodgt.R.id.searchForDates){
            // Navigate to search penalties by dates activity

            Intent searchForDates=new Intent(IntroducePenaltyForSearch.this,SearchByDatesPenalties.class);
            startActivity(searchForDates);
            overridePendingTransition(com.uco.ucodgt.R.anim.fadein, com.uco.ucodgt.R.anim.fadeout);

        }
    }
}
