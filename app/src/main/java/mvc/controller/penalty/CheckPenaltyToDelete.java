package mvc.controller.penalty;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.ucodgt.R;

import java.util.List;

import mvc.model.business.penalty.ManagerPenalty;
import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.data.PenaltyCallback;

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
        PenaltyDTO penalty=new PenaltyDTO(Integer.parseInt(id),null,null,null,null,null,null,null,null,null,false,null,null);
        mngP.deletePenalty(penalty,CheckPenaltyToDelete.this, new PenaltyCallback() {
            @Override
            public void onPenaltiesReceived(List<PenaltyDTO> penalties) {

            }

            @Override
            public void onError(VolleyError error) {
                //no existe
            }

            @Override
            public void onPenaltyReceived(PenaltyDTO penalty) {
                //existe y correcto
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
