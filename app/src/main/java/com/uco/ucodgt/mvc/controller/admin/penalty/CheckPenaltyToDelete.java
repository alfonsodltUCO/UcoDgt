package com.uco.ucodgt.mvc.controller.admin.penalty;

import android.content.Intent;
import android.os.Bundle;
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
/**
 * An activity that after check the data allow Admin to delete a client.
 * @author Alfonso de la torre
 */
public class CheckPenaltyToDelete extends AppCompatActivity {
    private ProgressBar progressBar;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        String id=getIntent().getStringExtra("id");
        setContentView(com.uco.ucodgt.R.layout.loading);
        progressBar=findViewById(com.uco.ucodgt.R.id.progressbar);
        showLoading();
        ManagerPenalty mngP=new ManagerPenalty();
        assert id != null;
        PenaltyDTO penalty=new PenaltyDTO(Integer.parseInt(id),null,null,null,null,null,null,null,null,null,false,null,null);

        mngP.deletePenalty(penalty,CheckPenaltyToDelete.this, new PenaltyCallback() {
            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

            }

            @Override
            public void onError(VolleyError error) {

                Intent goMain = new Intent(CheckPenaltyToDelete.this, AdminActivity.class);
                try {
                    Thread.sleep(2*1000);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                Toast.makeText(CheckPenaltyToDelete.this, "Not found the penalty", Toast.LENGTH_LONG).show();
                startActivity(goMain);
                hideLoading();
                             }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {

                Intent goMain = new Intent(CheckPenaltyToDelete.this, AdminActivity.class);
                try {
                    Thread.sleep(2*1000);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                Toast.makeText(CheckPenaltyToDelete.this, "Deleted done", Toast.LENGTH_LONG).show();
                startActivity(goMain);
                hideLoading();

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
