package com.example.ucodgt.mvc.view.admin.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ucodgt.R;

import java.util.List;

import com.example.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.example.ucodgt.mvc.model.business.user.worker.WorkerDTO;
/**
 * Activity for displaying a list of users (clients and workers).
 * @author Alfonso de la torre
 */
public class ShowUsers extends AppCompatActivity{
    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private List<ClientDTO> clientList;
    private List<WorkerDTO> workerList;
    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_users);
        // Retrieve the list of clients and workers from the intent

        List<WorkerDTO> workers = (List<WorkerDTO>) getIntent().getSerializableExtra("workers");
        List<ClientDTO> clients = (List<ClientDTO>) getIntent().getSerializableExtra("clients");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Initialize clientList and workerList

        if (!clients.isEmpty()) {
            clientList = clients;
        }

        if (!workers.isEmpty()) {
            workerList = workers;
        }

        // Initialize and set the adapter for the RecyclerView
        adapter = new CardAdapter(this,clientList, workerList);
        recyclerView.setAdapter(adapter);
    }
}
