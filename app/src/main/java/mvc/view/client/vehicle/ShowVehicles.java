package mvc.view.client.vehicle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ucodgt.R;

import java.util.List;

import mvc.model.business.vehicle.VehicleDTO;
/**
 * Activity class to display a list of vehicles.
 * @author Alfonso de la torre
 */
public class ShowVehicles extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardAdapterVehicle adapter;
    private List<VehicleDTO> vehicleList;
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
        String dni=getIntent().getStringExtra("dni");
        setContentView(R.layout.show_vehicles);

        // Retrieve vehicle list from intent
        List<VehicleDTO> vehicles = (List<VehicleDTO>) getIntent().getSerializableExtra("vehicles");

        recyclerView = findViewById(R.id.recycler_view_vehicles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (!vehicles.isEmpty()) {
            vehicleList = vehicles;
        }

        // Create and set adapter for the recycler view

        adapter = new CardAdapterVehicle(dni,this,vehicleList);
        recyclerView.setAdapter(adapter);
    }
}
