package mvc.view.worker.penalty;
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

import mvc.controller.worker.CheckPenaltyToFindForWorker;
import mvc.model.business.penalty.PenaltyDTO;

/**
 * Adapter for displaying PenaltyDTO objects in a RecyclerView as cards.
 * @author Alfonso de la torre
 */
public class CardAdapterPenalty extends RecyclerView.Adapter<CardAdapterPenalty.CardViewHolder> {

    private List<PenaltyDTO> penaltyList;
    String numberWorker;
    private Context context;

    /**
     * Constructs a new CardAdapterPenalty.
     *
     * @param context     The context in which the adapter is being used.
     * @param penaltyList The list of PenaltyDTO objects to display.
     */
    public CardAdapterPenalty(String numberWorker,Context context, List<PenaltyDTO> penaltyList) {

        this.context = context;
        this.numberWorker=numberWorker;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_penalties, parent, false);
        return new CardViewHolder(view);
    }
    /**
     * Binds data to the CardViewHolder.
     *
     * @param holder   The CardViewHolder to bind data to.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        PenaltyDTO penalty = penaltyList.get(position);
        holder.textPlate.setText(penalty.getLicenceplate());
        holder.textId.setText(penalty.getId().toString().trim());

        holder.itemView.setOnClickListener(v -> {

            Intent goShowPenalty = new Intent(context, CheckPenaltyToFindForWorker.class);
            goShowPenalty.putExtra("id", penalty.getId().toString());
            goShowPenalty.putExtra("numberWorker",numberWorker);
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
        /**
         * Constructs a new CardViewHolder.
         *
         * @param itemView The View that will be held by this ViewHolder.
         */
        public CardViewHolder(@NonNull View itemView) {

            super(itemView);
            textPlate = itemView.findViewById(R.id.textPenaltyL);
            textId = itemView.findViewById(R.id.textPenaltyId);
        }
    }
}

