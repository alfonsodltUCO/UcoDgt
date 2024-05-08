package com.uco.ucodgt.mvc.controller.admin.penalty;

import static com.uco.ucodgt.mvc.controller.commonFunctions.ForCheckPenalty.checkNumeric;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import java.util.List;

import com.uco.ucodgt.mvc.model.business.penalty.ManagerPenalty;
import com.uco.ucodgt.mvc.model.business.penalty.PenaltyDTO;
import com.uco.ucodgt.mvc.model.data.PenaltyCallback;
import com.uco.ucodgt.mvc.view.admin.AdminActivity;
import com.uco.ucodgt.mvc.view.admin.penalty.IntroducePenaltyForSearch;
import com.uco.ucodgt.mvc.view.admin.penalty.ShowPenalty;
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
        setContentView(com.uco.ucodgt.R.layout.loading);
        progressBar=findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();
        id=getIntent().getStringExtra("id");

        if(!TextUtils.isEmpty(id)){

            if(!checkNumeric(id)){//Check the identifier is numeric

                Intent intentGoBackAgain=new Intent(CheckPenaltyToFind.this, IntroducePenaltyForSearch.class);
                try {
                    Thread.sleep(2*1000);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
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
                        try {
                            Thread.sleep(2*1000);
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        }
                        Toast.makeText(CheckPenaltyToFind.this,"Not found the penalty", Toast.LENGTH_LONG).show();
                        startActivity(goMain);
                        finish();
                        hideLoading();
                    }

                    @Override
                    public void onPenaltyReceived(PenaltyDTO penalty) {
                        Intent goShow=new Intent(CheckPenaltyToFind.this, ShowPenalty.class);
                        try {
                            Thread.sleep(2*1000);
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        }
                        goShow.putExtra("penalty",penalty);
                        startActivity(goShow);
                        finish();
                        hideLoading();
                    }


                });

            }

        }else{
            Intent intentAdmin=new Intent(CheckPenaltyToFind.this, AdminActivity.class);
            try {
                Thread.sleep(2*1000);
            }
            catch (Exception e) {
                System.out.println(e);
            }
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
