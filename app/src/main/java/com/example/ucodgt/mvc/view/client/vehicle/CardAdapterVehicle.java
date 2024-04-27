package com.example.ucodgt.mvc.view.client.vehicle;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import com.example.ucodgt.mvc.controller.client.vehicle.CheckVehicleToFindForClient;
import com.example.ucodgt.mvc.model.business.vehicle.VehicleDTO;
/**
 * Adapter class for populating a RecyclerView with vehicle cards.
 * @author Alfonso de la torre
 */
public class CardAdapterVehicle extends RecyclerView.Adapter<CardAdapterVehicle.CardViewHolder> {

    private List<VehicleDTO> vehicleList;
    private Context context;
    private String dni;
    /**
     * Constructor to initialize CardAdapterVehicle.
     *
     * @param dniC        The client's DNI.
     * @param context     The context.
     * @param vehicleList The list of vehicles.
     */
    public CardAdapterVehicle(String dniC,Context context, List<VehicleDTO> vehicleList) {

        this.context = context;
        if (vehicleList == null) {
            this.vehicleList = new ArrayList<>();
        } else {
            this.vehicleList = vehicleList;
        }
        this.dni=dniC;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout for each card item

        View view = LayoutInflater.from(parent.getContext()).inflate(com.example.ucodgt.R.layout.item_card_vehicle, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        // Bind data to each card item

        VehicleDTO vehicle = vehicleList.get(position);
        holder.textPlate.setText(vehicle.getLicencePlate());
        holder.textType.setText(vehicle.getCarType().toString());
        // Set click listener to navigate to vehicle details

        holder.itemView.setOnClickListener(v -> {

            Intent goShowVehicle = new Intent(context, CheckVehicleToFindForClient.class);
            goShowVehicle.putExtra("licenceplate", vehicle.getLicencePlate());
            goShowVehicle.putExtra("dni",dni);
            context.startActivity(goShowVehicle);
        });
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }
    /**
     * ViewHolder class for a vehicle card.
     */

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView textPlate, textType;

        public CardViewHolder(@NonNull View itemView) {

            super(itemView);
            textPlate = itemView.findViewById(com.example.ucodgt.R.id.textLicenceP);
            textType = itemView.findViewById(com.example.ucodgt.R.id.textCarT);
        }
    }
}

