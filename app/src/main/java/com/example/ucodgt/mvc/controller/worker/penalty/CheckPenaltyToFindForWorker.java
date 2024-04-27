package com.example.ucodgt.mvc.controller.worker.penalty;

import static com.example.ucodgt.mvc.controller.commonFunctions.ForCheckPenalty.checkNumeric;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.util.List;

import com.example.ucodgt.mvc.model.business.penalty.ManagerPenalty;
import com.example.ucodgt.mvc.model.business.penalty.PenaltyDTO;
import com.example.ucodgt.mvc.model.data.PenaltyCallback;
import com.example.ucodgt.mvc.view.worker.WorkerActivity;
import com.example.ucodgt.mvc.view.worker.penalty.ShowPenalty;

/**
 * An activity to check penalty to find.
 * @author Alfonso de la torre
 */
public class CheckPenaltyToFindForWorker extends AppCompatActivity {
    private ProgressBar progressBar;
    String id,numberWorker;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
        showLoading();
        id=getIntent().getStringExtra("id");
        numberWorker=getIntent().getStringExtra("numberWorker");

        if(!TextUtils.isEmpty(id)){

            if(!checkNumeric(id)){//Check identifier is valid

                Intent intentGoBackAgain=new Intent(CheckPenaltyToFindForWorker.this, WorkerActivity.class);
                intentGoBackAgain.putExtra("numberWorker",numberWorker);
                startActivity(intentGoBackAgain);
                Toast.makeText(CheckPenaltyToFindForWorker.this,"Must be a number", Toast.LENGTH_LONG).show();
                finish();

            }else{

                ManagerPenalty mngP=new ManagerPenalty();
                PenaltyDTO penalty=new PenaltyDTO(Integer.parseInt(id),null,null,null,null,null,null,null,null,null,false,null,null);
                mngP.getPenalty(penalty, CheckPenaltyToFindForWorker.this, new PenaltyCallback() {


                    @Override
                    public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                    }

                    @Override
                    public void onError(VolleyError error) {
                        Intent goMain=new Intent(CheckPenaltyToFindForWorker.this, WorkerActivity.class);
                        goMain.putExtra("numberWorker",numberWorker);
                        Toast.makeText(CheckPenaltyToFindForWorker.this,"Not found the penalty", Toast.LENGTH_LONG).show();
                        startActivity(goMain);
                        finish();
                        hideLoading();
                    }

                    @Override
                    public void onPenaltyReceived(PenaltyDTO penalty) {
                        Intent goShow=new Intent(CheckPenaltyToFindForWorker.this, ShowPenalty.class);
                        goShow.putExtra("penalty",penalty);
                        goShow.putExtra("numberWorker",numberWorker);
                        startActivity(goShow);
                        finish();
                        hideLoading();
                    }


                });

            }

        }else{
            Intent intent=new Intent(CheckPenaltyToFindForWorker.this, WorkerActivity.class);
            intent.putExtra("numberWorker",numberWorker);
            startActivity(intent);
            Toast.makeText(CheckPenaltyToFindForWorker.this,"Fill the field please", Toast.LENGTH_LONG).show();
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
