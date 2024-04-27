package com.example.ucodgt.mvc.controller.worker.penalty;

import android.content.Intent;
import android.os.Bundle;
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


/**
 * This activity is responsible for canceling a penalty associated with a client giving back points and money.
 * It communicates with the backend to cancel the penalty and then redirects the user back to the WorkerActivity.
 * @author Alfonso de la torre
 */
public class CheckPenaltyToCancel extends AppCompatActivity {

    String id,numberWorker,points,dniClient;

    private ProgressBar progressBar;


    /**
     * Called when the activity is starting. Responsible for initializing the activity,
     * retrieving necessary data from the intent, canceling the penalty, and handling the response.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        progressBar = findViewById(R.id.progressbar);
        showLoading();

        id=getIntent().getStringExtra("id");
        numberWorker=getIntent().getStringExtra("numberWorker");
        points=getIntent().getStringExtra("points");
        dniClient=getIntent().getStringExtra("dni");

        ManagerPenalty mngP=new ManagerPenalty();

        PenaltyDTO penaltyToFind=new PenaltyDTO(Integer.parseInt(id),null,null,null,null,null,null,null,null,null,false,null,null);

        mngP.getPenalty(penaltyToFind, CheckPenaltyToCancel.this, new PenaltyCallback() {
            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

            }

            @Override
            public void onError(VolleyError error) {

                Intent intent=new Intent(CheckPenaltyToCancel.this, WorkerActivity.class);
                intent.putExtra("numberWorker",numberWorker);
                startActivity(intent);
                Toast.makeText(CheckPenaltyToCancel.this,"An error has happened, contact the administrator please", Toast.LENGTH_LONG).show();
                hideLoading();
                finish();
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {
                if(penalty.getState().toString().equals("cancelled")){

                    Intent intent=new Intent(CheckPenaltyToCancel.this, WorkerActivity.class);
                    intent.putExtra("numberWorker",numberWorker);
                    startActivity(intent);
                    Toast.makeText(CheckPenaltyToCancel.this,"The penalty is already cancelled", Toast.LENGTH_LONG).show();
                    hideLoading();
                    finish();
                }else{

                    PenaltyDTO penaltyToSend=new PenaltyDTO(Integer.parseInt(id),Integer.parseInt(points),null,Float.parseFloat("0"),null,null,dniClient,null,null,null,false,null,null);

                    mngP.cancelPenalty(penaltyToSend,CheckPenaltyToCancel.this, new PenaltyCallback() {
                        @Override
                        public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

                            Intent intent=new Intent(CheckPenaltyToCancel.this, WorkerActivity.class);
                            intent.putExtra("numberWorker",numberWorker);
                            startActivity(intent);
                            Toast.makeText(CheckPenaltyToCancel.this,"Penalty was cancelled", Toast.LENGTH_LONG).show();
                            hideLoading();
                            finish();
                        }

                        @Override
                        public void onError(VolleyError error) {

                            Intent intent=new Intent(CheckPenaltyToCancel.this, WorkerActivity.class);
                            intent.putExtra("numberWorker",numberWorker);
                            startActivity(intent);
                            Toast.makeText(CheckPenaltyToCancel.this,"An error has happened, contact the administrator please", Toast.LENGTH_LONG).show();
                            hideLoading();
                            finish();
                        }

                        @Override
                        public void onPenaltyReceived(PenaltyDTO penalty) {

                        }
                    });
                }
            }
        });
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
