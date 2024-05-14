package com.uco.ucodgt.mvc.view.admin.penalty;
import android.annotation.SuppressLint;
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

import com.uco.ucodgt.mvc.controller.admin.penalty.CheckPenaltyToFind;
import com.uco.ucodgt.mvc.model.business.penalty.PenaltyDTO;

/**
 * Admin Adapter for displaying PenaltyDTO objects in a RecyclerView as cards.
 * @author Alfonso de la torre
 */
public class CardAdapterPenalty extends RecyclerView.Adapter<CardAdapterPenalty.CardViewHolder> {

    private List<PenaltyDTO> penaltyList;
    private Context context;

    /**
     * Constructs a new CardAdapterPenalty.
     *
     * @param context     The context in which the adapter is being used.
     * @param penaltyList The list of PenaltyDTO objects to display.
     */
    public CardAdapterPenalty(Context context, List<PenaltyDTO> penaltyList) {

        this.context = context;

        if (penaltyList == null) {
            this.penaltyList = new ArrayList<>();
        } else {
            this.penaltyList = penaltyList;
        }
    }
    /**
     * Creates a new CardViewHolder for holding card views.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The type of the new View.
     * @return A new CardViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.uco.ucodgt.R.layout.item_card_penalties, parent, false);
        return new CardViewHolder(view);
    }
    /**
     * Binds data to the CardViewHolder.
     *
     * @param holder   The CardViewHolder to bind data to.
     * @param position The position of the item within the adapter's data set.
     */
    @SuppressLint("SetTextI18n")
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

            Intent goShowPenalty = new Intent(context, CheckPenaltyToFind.class);
            goShowPenalty.putExtra("id", penalty.getId().toString());
            context.startActivity(goShowPenalty);
        });
    }
    /**
     * Gets the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return penaltyList.size();
    }
    /**
     * ViewHolder for holding card views.
     */
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView textPlate, textId;
        ImageView image;

        /**
         * Constructs a new CardViewHolder.
         *
         * @param itemView The View that will be held by this ViewHolder.
         */
        public CardViewHolder(@NonNull View itemView) {

            super(itemView);
            image = itemView.findViewById(com.uco.ucodgt.R.id.imageView);
            textPlate = itemView.findViewById(com.uco.ucodgt.R.id.textPenaltyL);
            textId = itemView.findViewById(com.uco.ucodgt.R.id.textPenaltyId);
        }
    }
}

