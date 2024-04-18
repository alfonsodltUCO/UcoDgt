package mvc.view.admin.penalty;
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

import mvc.controller.penalty.CheckPenaltyToFind;
import mvc.controller.vehicle.CheckVehicleToFind;
import mvc.model.business.penalty.PenaltyDTO;
import mvc.model.business.vehicle.VehicleDTO;

public class CardAdapterPenalty extends RecyclerView.Adapter<CardAdapterPenalty.CardViewHolder> {

    private List<PenaltyDTO> penaltyList;
    private Context context;

    public CardAdapterPenalty(Context context, List<PenaltyDTO> penaltyList) {
        this.context = context;
        if (penaltyList == null) {
            this.penaltyList = new ArrayList<>();
        } else {
            this.penaltyList = penaltyList;
        }
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_penalties, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        PenaltyDTO penalty = penaltyList.get(position);
        holder.textPlate.setText(penalty.getLicenceplate());
        holder.textId.setText(penalty.getId().toString().trim());
        holder.itemView.setOnClickListener(v -> {
            Intent goShowPenalty = new Intent(context, CheckPenaltyToFind.class);
            goShowPenalty.putExtra("id", penalty.getId().toString());
            context.startActivity(goShowPenalty);
        });
    }

    @Override
    public int getItemCount() {
        return penaltyList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView textPlate, textId;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            textPlate = itemView.findViewById(R.id.textPenaltyL);
            textId = itemView.findViewById(R.id.textPenaltyId);
        }
    }
}

