package com.uco.ucodgt.mvc.view.admin.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.uco.ucodgt.R;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
/**
 * Activity for adding a new penalty by admin.
 * @author Alfonso de la torre
 */
public class AddPenaltyActivity extends AppCompatActivity implements View.OnClickListener {
    Button goAddDescrp,goMain;
    String selectedReason;
    Spinner spinnerReason;
    EditText etDate,etDniClient,etDniWorker,etPlace,etInformedAtTheMoment,etLocality,etLPlate,etQuantity,etPoints;
    /**
     * Initializes the activity with UI components and sets up click listeners.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was no saved state.
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.add_penalty);
        goAddDescrp=findViewById(com.uco.ucodgt.R.id.goNext);
        goMain=findViewById(com.uco.ucodgt.R.id.goMainMenu);

        etDate=findViewById(com.uco.ucodgt.R.id.etDate);
        etDniClient=findViewById(com.uco.ucodgt.R.id.etCDni);
        etDniWorker=findViewById(com.uco.ucodgt.R.id.etWDni);
        etPlace=findViewById(com.uco.ucodgt.R.id.etPlace);
        etInformedAtTheMoment=findViewById(com.uco.ucodgt.R.id.etInformedAtTheMoment);
        etLocality=findViewById(com.uco.ucodgt.R.id.etLocality);
        etLPlate=findViewById(com.uco.ucodgt.R.id.etLicencePlate);
        etQuantity=findViewById(com.uco.ucodgt.R.id.etQuantity);
        etPoints=findViewById(com.uco.ucodgt.R.id.etPoints);


        spinnerReason = findViewById(com.uco.ucodgt.R.id.Reason);
        String[] reasons = getResources().getStringArray(com.uco.ucodgt.R.array.reason);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.reason, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReason.setAdapter(adapter);
        spinnerReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedReason = (String) parentView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        goAddDescrp.setOnClickListener(this);
        goMain.setOnClickListener(this);
    }
    /**
     * Handles click events for the buttons in the activity.
     *
     * @param v The view that was clicked.
     */
    public void onClick(View v) {

        if(v.getId()==com.uco.ucodgt.R.id.goNext){

            Intent goNext = new Intent(AddPenaltyActivity.this, IntroduceDescriptionForPenalty.class);
            goNext.putExtra("date",etDate.getText().toString());
            goNext.putExtra("dniC",etDniClient.getText().toString());
            goNext.putExtra("dniW",etDniWorker.getText().toString());
            goNext.putExtra("state","processed");
            goNext.putExtra("reason",selectedReason);
            goNext.putExtra("place",etPlace.getText().toString());
            goNext.putExtra("informed",etInformedAtTheMoment.getText().toString());
            goNext.putExtra("locality",etLocality.getText().toString());
            goNext.putExtra("licenceplate",etLPlate.getText().toString());
            goNext.putExtra("quantity",etQuantity.getText().toString());
            goNext.putExtra("points",etPoints.getText().toString());
            startActivity(goNext);
            finish();

        } else if (v.getId()==com.uco.ucodgt.R.id.goMainMenu) {

            Intent intentGoMain = new Intent(AddPenaltyActivity.this, AdminActivity.class);
            startActivity(intentGoMain);
            finish();

        }
    }
}
