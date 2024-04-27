package com.example.ucodgt.mvc.controller.admin.penalty;

import static com.example.ucodgt.mvc.controller.commonFunctions.ForCheckPenalty.checkNumeric;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.List;

import com.example.ucodgt.mvc.model.business.penalty.ManagerPenalty;
import com.example.ucodgt.mvc.model.business.penalty.PenaltyDTO;
import com.example.ucodgt.mvc.model.data.PenaltyCallback;
import com.example.ucodgt.mvc.view.admin.AdminActivity;
import com.example.ucodgt.mvc.view.admin.penalty.IntroducePenaltyForSearch;
import com.example.ucodgt.mvc.view.admin.penalty.ShowPenalty;
/**
 * An activity to check penalty to find.
 * @author Alfonso de la torre
 */
public class CheckPenaltyToFind extends AppCompatActivity {
    private ProgressBar progressBar;
    String id;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.ucodgt.R.layout.loading);
        progressBar=findViewById(com.example.ucodgt.R.id.progressbar);
        showLoading();
        id=getIntent().getStringExtra("id");

        if(!TextUtils.isEmpty(id)){

            if(!checkNumeric(id)){//Check the identifier is numeric

                Intent intentGoBackAgain=new Intent(CheckPenaltyToFind.this, IntroducePenaltyForSearch.class);
                startActivity(intentGoBackAgain);
                Toast.makeText(CheckPenaltyToFind.this,"Must be a number", Toast.LENGTH_LONG).show();
                finish();

            }else{

                ManagerPenalty mngP=new ManagerPenalty();
                PenaltyDTO penalty=new PenaltyDTO(Integer.parseInt(id),null,null,null,null,null,null,null,null,null,false,null,null);
                mngP.getPenalty(penalty, CheckPenaltyToFind.this, new PenaltyCallback() {


                    @Override
                    public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                    }

                    @Override
                    public void onError(VolleyError error) {
                        Intent goMain=new Intent(CheckPenaltyToFind.this, AdminActivity.class);
                        Toast.makeText(CheckPenaltyToFind.this,"Not found the penalty", Toast.LENGTH_LONG).show();
                        startActivity(goMain);
                        finish();
                        hideLoading();
                    }

                    @Override
                    public void onPenaltyReceived(PenaltyDTO penalty) {
                        Intent goShow=new Intent(CheckPenaltyToFind.this, ShowPenalty.class);
                        goShow.putExtra("penalty",penalty);
                        startActivity(goShow);
                        finish();
                        hideLoading();
                    }


                });

            }

        }else{
            Intent intentAdmin=new Intent(CheckPenaltyToFind.this, AdminActivity.class);
            startActivity(intentAdmin);
            Toast.makeText(CheckPenaltyToFind.this,"Fill the field please", Toast.LENGTH_LONG).show();
            finish();
        }

    }
    /**
     * Show loading progress bar.
     */
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    /**
     * Hide loading progress bar.
     */
    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
