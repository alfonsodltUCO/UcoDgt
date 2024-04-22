package mvc.controller.penalty;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.util.List;

import mvc.model.business.penalty.ManagerPenalty;
import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.data.PenaltyCallback;
import mvc.view.admin.AdminActivity;

public class CheckPenaltyToDelete extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String id=getIntent().getStringExtra("id");
        setContentView(R.layout.loading);
        progressBar=findViewById(R.id.progressbar);
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
                Toast.makeText(CheckPenaltyToDelete.this, "Not found the penalty", Toast.LENGTH_LONG).show();
                startActivity(goMain);
                hideLoading();
                finish();            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {
                Intent goMain = new Intent(CheckPenaltyToDelete.this, AdminActivity.class);
                Toast.makeText(CheckPenaltyToDelete.this, "Deleted done", Toast.LENGTH_LONG).show();
                startActivity(goMain);
                hideLoading();
                finish();
            }
        });
    }
    private void showLoading() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
