package com.example.ucodgt.mvc.view.client.penalty;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ucodgt.R;

import java.util.List;

import com.example.ucodgt.mvc.model.business.penalty.PenaltyDTO;
/**
 * Activity class to display a list of penalties for a client.
 * @author Alfonsi de la torre
 */
public class ShowPenalties extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardAdapterPenalty adapter;
    private List<PenaltyDTO> penaltyList;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     * @see AppCompatActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.show_penalties);
        String dni=getIntent().getStringExtra("dni");

        List<PenaltyDTO> penalties = (List<PenaltyDTO>) getIntent().getSerializableExtra("penalties");
        recyclerView = findViewById(R.id.recycler_view_penalties);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Check if the list of penalties is not empty

        if (!penalties.isEmpty()) {
            penaltyList = penalties;
        }
        // Create and set adapter for the recycler view

        adapter = new CardAdapterPenalty(dni,this,penaltyList);
        recyclerView.setAdapter(adapter);
    }
}
