package com.uco.ucodgt.mvc.view.client.vehicle;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import com.uco.ucodgt.mvc.controller.client.vehicle.CheckVehicleToFindForClient;
import com.uco.ucodgt.mvc.model.business.vehicle.VehicleDTO;
/**
 * Adapter class for Client for populating a RecyclerView with vehicle cards.
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

        View view = LayoutInflater.from(parent.getContext()).inflate(com.uco.ucodgt.R.layout.item_card_vehicle, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        // Bind data to each card item

        VehicleDTO vehicle = vehicleList.get(position);
        holder.textPlate.setText(vehicle.getLicencePlate());
        holder.textType.setText(vehicle.getCarType().toString());
        // Set click listener to navigate to vehicle details
        if(vehicleList.get(position).getCarType().toString().equals("fiat")){
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.fiat);
        } else if (vehicleList.get(position).getCarType().toString().equals("ferrari")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.ferrari);

        } else if (vehicleList.get(position).getCarType().toString().equals("porsche")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.porsche);

        } else if (vehicleList.get(position).getCarType().toString().equals("lamborghini")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.lamborghini);

        } else if (vehicleList.get(position).getCarType().toString().equals("citroen")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.citroen);

        } else if (vehicleList.get(position).getCarType().toString().equals("skoda")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.skoda);

        } else if (vehicleList.get(position).getCarType().toString().equals("audi")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.audi);

        } else if (vehicleList.get(position).getCarType().toString().equals("bmw")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.bmw);

        }else if (vehicleList.get(position).getCarType().toString().equals("volkswagen")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.volkswagen);

        } else if (vehicleList.get(position).getCarType().toString().equals("mercedes")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.mercedes);

        } else if (vehicleList.get(position).getCarType().toString().equals("volvo")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.volvo);

        }
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
        ImageView image;


        public CardViewHolder(@NonNull View itemView) {

            super(itemView);
            image=itemView.findViewById((com.uco.ucodgt.R.id.imageView));
            textPlate = itemView.findViewById(com.uco.ucodgt.R.id.textLicenceP);
            textType = itemView.findViewById(com.uco.ucodgt.R.id.textCarT);
        }
    }
}

