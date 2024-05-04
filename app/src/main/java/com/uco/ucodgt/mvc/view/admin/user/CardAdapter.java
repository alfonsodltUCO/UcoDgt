package com.uco.ucodgt.mvc.view.admin.user;
import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import com.uco.ucodgt.mvc.controller.admin.users.CheckUserToFind;
import com.uco.ucodgt.mvc.model.business.user.client.ClientDTO;
import com.uco.ucodgt.mvc.model.business.user.worker.WorkerDTO;
 import java.util.List;

/**
 * Adapter class for displaying users in a RecyclerView.
 * @author Alfonso de la torre
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder>{

    private List<ClientDTO> clientList;
    private List<WorkerDTO> workerList;

    private Context context;
    /**
     * Constructs a CardAdapter.
     *
     * @param context     The context in which the adapter is being used.
     * @param clientList  The list of client users.
     * @param workerList  The list of worker users.
     */
    public CardAdapter(Context context,List<ClientDTO> clientList, List<WorkerDTO> workerList) {
        if (clientList == null) {
            this.clientList = new ArrayList<>();
        } else {
            this.clientList = clientList;
            this.context=context;
        }

        if (workerList == null) {
            this.workerList = new ArrayList<>();
        } else {
            this.workerList = workerList;
            this.context=context;
        }
    }
    /**
     * Creates a new CardViewHolder by inflating a layout.
     *
     * @param parent   The parent ViewGroup.
     * @param viewType The view type of the new View.
     * @return A new CardViewHolder.
     */
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.uco.ucodgt.R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }
    /**
     * Binds data to a CardViewHolder.
     *
     * @param holder   The CardViewHolder to bind data to.
     * @param position The position of the item within the adapter's data set.
     */
    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        if (position < clientList.size()) {

            ClientDTO clientDTO = clientList.get(position);
            holder.textDni.setText(clientDTO.getDni());
            holder.textType.setText("client");
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.cliente);

            holder.itemView.setOnClickListener(v -> {

                Intent goShowUser=new Intent(context,CheckUserToFind.class);
                goShowUser.putExtra("dni",clientDTO.getDni());
                goShowUser.putExtra("userToFind","client");
                startActivity(context,goShowUser,null);
            });

        } else {

            WorkerDTO workerDTO = workerList.get(position - clientList.size());
            holder.textDni.setText(workerDTO.getDni());
            holder.textType.setText("worker");
            holder.image.setImageResource(com.uco.ucodgt.R.drawable.trabajador);

            holder.itemView.setOnClickListener(v -> {

                Intent goShowUser=new Intent(context,CheckUserToFind.class);
                goShowUser.putExtra("dni",workerDTO.getDni());
                goShowUser.putExtra("userToFind","worker");
                startActivity(context,goShowUser,null);
            });
        }
    }
    /**
     * Gets the total number of items in the data set held by the adapter.
     *
     * @return The total number of items.
     */
    @Override
    public int getItemCount() {
        if(!clientList.isEmpty() && !workerList.isEmpty()){
            return clientList.size() + workerList.size();
        } else if (!clientList.isEmpty()) {
            return clientList.size();
        } else if (!workerList.isEmpty()) {
            return workerList.size();
        }else{
            return 0;
        }
    }
    /**
     * ViewHolder for the CardAdapter.
     */
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView textDni,textType;
        ImageView image;
        /**
         * Constructs a CardViewHolder.
         *
         * @param itemView The view of the item.
         */
        public CardViewHolder(@NonNull View itemView) {

            super(itemView);
            image = itemView.findViewById(com.uco.ucodgt.R.id.imageView);
            textDni = itemView.findViewById(com.uco.ucodgt.R.id.text_dni);
            textType = itemView.findViewById(com.uco.ucodgt.R.id.text_type);
        }
    }
}
