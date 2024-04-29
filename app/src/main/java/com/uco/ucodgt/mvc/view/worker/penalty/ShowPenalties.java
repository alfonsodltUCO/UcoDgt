package com.uco.ucodgt.mvc.view.worker.penalty;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import com.uco.ucodgt.mvc.model.business.penalty.PenaltyDTO;

/**
* Activity to display a list of penalties using a RecyclerView.
* @author Alfonso de la torre
*/
public class ShowPenalties extends AppCompatActivity {
   private RecyclerView recyclerView;
   private CardAdapterPenalty adapter;
   private List<PenaltyDTO> penaltyList;

   String numberWorker;

   /**
    * Initializes the activity with a list of penalties and sets up the RecyclerView.
    *
    * @param savedInstanceState A Bundle containing the activity's previously saved state, or null if there was no saved state.
    */
   @Override
   protected void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);

       setContentView(com.uco.ucodgt.R.layout.show_penalties);

       numberWorker=getIntent().getStringExtra("numberWorker");
       // Retrieve the list of penalties from the intent

       List<PenaltyDTO> penalties = (List<PenaltyDTO>) getIntent().getSerializableExtra("penalties");

       recyclerView = findViewById(com.uco.ucodgt.R.id.recycler_view_penalties);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // If the list of penalties is not empty, assign it to the local variable

       if (!penalties.isEmpty()) {

           penaltyList = penalties;
       }
       // Create and set up the adapter with the list of penalties

       adapter = new CardAdapterPenalty(numberWorker,this,penaltyList);
       recyclerView.setAdapter(adapter);
   }
}
