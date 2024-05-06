package com.uco.ucodgt.mvc.view.admin.vehicle;
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

import com.uco.ucodgt.mvc.controller.admin.vehicle.CheckVehicleToFind;
import com.uco.ucodgt.mvc.model.business.vehicle.VehicleDTO;
/**
 * Adapter class for displaying vehicle information in a RecyclerView.
 * @author Alfonso de la torre
 */
public class CardAdapterVehicle extends RecyclerView.Adapter<CardAdapterVehicle.CardViewHolder> {

    private List<VehicleDTO> vehicleList;
    private Context context;
    /**
     * Constructor to initialize the adapter with a list of vehicles and a context.
     *
     * @param context     The context in which the adapter is being used.
     * @param vehicleList The list of vehicles to be displayed.
     */
    public CardAdapterVehicle(Context context, List<VehicleDTO> vehicleList) {

        this.context = context;

        if (vehicleList == null) {
            this.vehicleList = new ArrayList<>();
        } else {
            this.vehicleList = vehicleList;
        }
    }
    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(com.uco.ucodgt.R.layout.item_card_vehicle, parent, false);
        return new CardViewHolder(view);

    }
    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        VehicleDTO vehicle = vehicleList.get(position);
        holder.textPlate.setText(vehicle.getLicencePlate());
        holder.textType.setText(vehicle.getCarType().toString());

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

            Intent goShowVehicle = new Intent(context, CheckVehicleToFind.class);
            goShowVehicle.putExtra("licenceplate", vehicle.getLicencePlate());
            context.startActivity(goShowVehicle);
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return vehicleList.size();
    }
    /**
     * ViewHolder class to hold the views for each item in the RecyclerView.
     */
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView textPlate, textType;
        ImageView image;

        /**
         * Constructor to initialize the views inside the ViewHolder.
         *
         * @param itemView The view representing each item in the RecyclerView.
         */
        public CardViewHolder(@NonNull View itemView) {

            super(itemView);
            image=itemView.findViewById((com.uco.ucodgt.R.id.imageView));
            textPlate = itemView.findViewById(com.uco.ucodgt.R.id.textLicenceP);
            textType = itemView.findViewById(com.uco.ucodgt.R.id.textCarT);
        }
    }
}

