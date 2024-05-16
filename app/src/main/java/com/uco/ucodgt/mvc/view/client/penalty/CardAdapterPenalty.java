package com.uco.ucodgt.mvc.view.client.penalty;
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

import com.uco.ucodgt.mvc.controller.client.penalty.CheckPenaltyToFindForClient;
import com.uco.ucodgt.mvc.model.business.penalty.PenaltyDTO;
/**
 * Adapter class for Client for displaying penalties in a RecyclerView.
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

        View view = LayoutInflater.from(parent.getContext()).inflate(com.uco.ucodgt.R.layout.item_card_penalties, parent, false);
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

        if(penaltyList.get(position).getReason().toString().equals("drugs")){
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.drugs);
        } else if (penaltyList.get(position).getReason().toString().equals("blue_zone")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.blue_zone);

        } else if (penaltyList.get(position).getReason().toString().equals("no_seatbelt")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.no_seatbelt);

        } else if (penaltyList.get(position).getReason().toString().equals("velocity")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.velocity);

        } else if (penaltyList.get(position).getReason().toString().equals("incorrect_parking")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.incorrect_parking);

        } else if (penaltyList.get(position).getReason().toString().equals("alcohol")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.alcohol);

        } else if (penaltyList.get(position).getReason().toString().equals("no_itv")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.no_itv);

        } else if (penaltyList.get(position).getReason().toString().equals("no_insurance")) {
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.no_insurance);

        }
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
        ImageView image;
        /**
         * Constructor for CardViewHolder.
         *
         * @param itemView The view for a penalty item.
         */
        public CardViewHolder(@NonNull View itemView) {

            super(itemView);
            image=itemView.findViewById((com.uco.ucodgt.R.id.imageView));
            textPlate = itemView.findViewById(com.uco.ucodgt.R.id.textPenaltyL);
            textId = itemView.findViewById(com.uco.ucodgt.R.id.textPenaltyId);
        }
    }
}

