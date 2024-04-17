package mvc.view.vehicle;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ucodgt.R;

import java.util.ArrayList;
import java.util.List;

import mvc.controller.vehicle.CheckVehicleToFind;
import mvc.model.business.vehicle.VehicleDTO;

public class CardAdapterVehicle extends RecyclerView.Adapter<CardAdapterVehicle.CardViewHolder> {

    private List<VehicleDTO> vehicleList;
    private Context context;

    public CardAdapterVehicle(Context context, List<VehicleDTO> vehicleList) {
        this.context = context;
        if (vehicleList == null) {
            this.vehicleList = new ArrayList<>();
        } else {
            this.vehicleList = vehicleList;
        }
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_vehicle, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        VehicleDTO vehicle = vehicleList.get(position);
        holder.textPlate.setText(vehicle.getLicencePlate());
        holder.textType.setText(vehicle.getCarType().ordinal());
        holder.itemView.setOnClickListener(v -> {
            Intent goShowVehicle = new Intent(context, CheckVehicleToFind.class);
            goShowVehicle.putExtra("licenceplate", vehicle.getLicencePlate());
            context.startActivity(goShowVehicle);
        });
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView textPlate, textType;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            textPlate = itemView.findViewById(R.id.textLicenceP);
            textType = itemView.findViewById(R.id.textCarT);
        }
    }
}

