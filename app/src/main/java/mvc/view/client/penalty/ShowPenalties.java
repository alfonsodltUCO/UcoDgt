package mvc.view.client.penalty;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ucodgt.R;

import java.util.List;

import mvc.model.business.penalty.PenaltyDTO;

public class ShowPenalties extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardAdapterPenalty adapter;
    private List<PenaltyDTO> penaltyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_penalties);
        String dni=getIntent().getStringExtra("dni");
        List<PenaltyDTO> penalties = (List<PenaltyDTO>) getIntent().getSerializableExtra("penalties");
        recyclerView = findViewById(R.id.recycler_view_penalties);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (!penalties.isEmpty()) {
            penaltyList = penalties;
        }

        adapter = new CardAdapterPenalty(dni,this,penaltyList);
        recyclerView.setAdapter(adapter);
    }
}
