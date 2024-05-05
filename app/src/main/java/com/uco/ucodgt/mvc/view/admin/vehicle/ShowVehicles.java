package com.uco.ucodgt.mvc.view.admin.vehicle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import com.uco.ucodgt.mvc.model.business.vehicle.VehicleDTO;
/**
 * Activity class to display a list of vehicles for administrators.
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
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.uco.ucodgt.R.layout.show_vehicles);
        // Retrieve the list of vehicles from the intent

        List<VehicleDTO> vehicles = (List<VehicleDTO>) getIntent().getSerializableExtra("vehicles");
        recyclerView = findViewById(com.uco.ucodgt.R.id.recycler_view_vehicles);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Check if the list of vehicles is not empty

        if (!vehicles.isEmpty()) {
            vehicleList = vehicles;
        }
        // Create and set up the adapter with the list of vehicles

        adapter = new CardAdapterVehicle(this,vehicleList);
        recyclerView.setAdapter(adapter);
    }
}
