package com.example.ucodgt.mvc.view.client.penalty;
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

import com.example.ucodgt.mvc.controller.client.penalty.CheckPenaltyToFindForClient;
import com.example.ucodgt.mvc.model.business.penalty.PenaltyDTO;
/**
 * Adapter class for displaying penalties in a RecyclerView.
 * @author Alfonso de la torre
 */
public class CardAdapterPenalty extends RecyclerView.Adapter<CardAdapterPenalty.CardViewHolder> {

    private List<PenaltyDTO> penaltyList;
    private Context context;
    String dni;
    /**
     * Constructor for CardAdapterPenalty.
     *
     * @param dni         The client's DNI.
     * @param context     The context.
     * @param penaltyList The list of penalties.
     */
    public CardAdapterPenalty(String dni,Context context, List<PenaltyDTO> penaltyList) {

        this.context = context;
        if (penaltyList == null) {
            this.penaltyList = new ArrayList<>();
        } else {
            this.penaltyList = penaltyList;
        }
        this.dni=dni;
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

        View view = LayoutInflater.from(parent.getContext()).inflate(com.example.ucodgt.R.layout.item_card_penalties, parent, false);
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

        PenaltyDTO penalty = penaltyList.get(position);
        holder.textPlate.setText(penalty.getLicenceplate());
        holder.textId.setText(penalty.getId().toString().trim());

        holder.itemView.setOnClickListener(v -> {

            Intent goShowPenalty = new Intent(context, CheckPenaltyToFindForClient.class);
            goShowPenalty.putExtra("id", penalty.getId().toString());
            goShowPenalty.putExtra("dni",dni);
            context.startActivity(goShowPenalty);

        });
    }
    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return penaltyList.size();
    }
    /**
     * ViewHolder class for a penalty item.
     */
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView textPlate, textId;
        /**
         * Constructor for CardViewHolder.
         *
         * @param itemView The view for a penalty item.
         */
        public CardViewHolder(@NonNull View itemView) {

            super(itemView);
            textPlate = itemView.findViewById(com.example.ucodgt.R.id.textPenaltyL);
            textId = itemView.findViewById(com.example.ucodgt.R.id.textPenaltyId);
        }
    }
}

