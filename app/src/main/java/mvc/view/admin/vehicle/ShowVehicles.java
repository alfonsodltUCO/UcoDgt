package mvc.view.admin.vehicle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ucodgt.R;

import java.util.List;

import mvc.model.business.vehicle.VehicleDTO;

public class ShowVehicles extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardAdapterVehicle adapter;
    private List<VehicleDTO> vehicleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_vehicles);
        List<VehicleDTO> vehicles = (List<VehicleDTO>) getIntent().getSerializableExtra("vehicles");
        recyclerView = findViewById(R.id.recycler_view_vehicles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (!vehicles.isEmpty()) {
            vehicleList = vehicles;
        }

        adapter = new CardAdapterVehicle(this,vehicleList);
        recyclerView.setAdapter(adapter);
    }
}
