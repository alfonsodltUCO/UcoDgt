package mvc.view.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ucodgt.R;

import java.util.ArrayList;
import java.util.List;

import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.WorkerDTO;

public class ShowUsers extends AppCompatActivity{
    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private List<ClientDTO> clientList;
    private List<WorkerDTO> workerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_users);
        List<WorkerDTO> workers = (List<WorkerDTO>) getIntent().getSerializableExtra("workers");
        List<ClientDTO> clients = (List<ClientDTO>) getIntent().getSerializableExtra("clients");
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (!clients.isEmpty()) {
            clientList = clients;
        }
        if (!workers.isEmpty()) {
            workerList = workers;
        }
        adapter = new CardAdapter(this,clientList, workerList);
        recyclerView.setAdapter(adapter);
    }
}
