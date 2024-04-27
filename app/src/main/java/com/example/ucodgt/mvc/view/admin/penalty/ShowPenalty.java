package com.example.ucodgt.mvc.view.admin.penalty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;

import com.example.ucodgt.mvc.controller.admin.penalty.CheckPenaltyToDelete;
import com.example.ucodgt.mvc.model.business.penalty.PenaltyDTO;
import com.example.ucodgt.mvc.view.admin.AdminActivity;
/**
 * Activity to display details of a penalty and provide options to delete it or return to the main menu.
 * @author Alfosno de la torre
 */
public class ShowPenalty extends AppCompatActivity implements View.OnClickListener{
    String idtodelete;
    TextView id,description,dniw,dnic,quant,points,date,state,reason,licenceP;
    Button goMain,deletePenalty;
    PenaltyDTO penalty;

    /**
     * Initializes the activity with penalty details and sets up UI components.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was no saved state.
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        penalty=(PenaltyDTO)getIntent().getSerializableExtra("penalty");
        setContentView(com.example.ucodgt.R.layout.show_penalty);
        goMain=findViewById(com.example.ucodgt.R.id.goMainMenu);
        deletePenalty=findViewById(com.example.ucodgt.R.id.deletePenalty);
        id=findViewById(com.example.ucodgt.R.id.tvId);
        date=findViewById(com.example.ucodgt.R.id.tvDate);
        dnic=findViewById(com.example.ucodgt.R.id.tvDniC);
        dniw=findViewById(com.example.ucodgt.R.id.tvDniW);
        description=findViewById(com.example.ucodgt.R.id.tvDescription);
        state=findViewById(com.example.ucodgt.R.id.tvState);
        reason=findViewById(com.example.ucodgt.R.id.tvReason);
        licenceP=findViewById(com.example.ucodgt.R.id.tvLicenceP);
        idtodelete=id.getText().toString();
        quant=findViewById(com.example.ucodgt.R.id.tvQuantity);
        points=findViewById(com.example.ucodgt.R.id.tvPoints);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(penalty.getDate());
        date.setText("date= "+strDate);
        id.setText("id= "+penalty.getId().toString());
        dniw.setText("dni Worker= "+penalty.getDniWorker());
        dnic.setText("dni Client= "+penalty.getDniClient());
        description.setText(penalty.getDescription());
        state.setText("state= "+penalty.getState().toString());
        reason.setText("reason= "+penalty.getReason().toString());
        points.setText("points= "+penalty.getPoints().toString());
        quant.setText("quantity= "+ penalty.getQuantity().toString());
        licenceP.setText("plate= "+penalty.getLicenceplate());
        goMain.setOnClickListener(this);
        deletePenalty.setOnClickListener(this);
    }
    /**
     * Handles click events for UI components.
     *
     * @param v The View that was clicked.
     */
    @Override
    public void onClick(View v) {

        if(v.getId()==com.example.ucodgt.R.id.goMainMenu){
            // Navigates to the main menu

            Intent goMain=new Intent(ShowPenalty.this, AdminActivity.class);
            startActivity(goMain);
            finish();

        }else if(v.getId()==com.example.ucodgt.R.id.deletePenalty){
            // Initiates deletion process for the current penalty

            Intent goDelete=new Intent(ShowPenalty.this, CheckPenaltyToDelete.class);
            goDelete.putExtra("id", penalty.getId().toString());
            startActivity(goDelete);
            finish();

        }
    }
}
