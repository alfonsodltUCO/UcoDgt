package mvc.view.admin;
import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import mvc.controller.admin.CheckUserToFind;
import mvc.model.business.user.client.ClientDTO;
import mvc.model.business.user.worker.WorkerDTO;
import com.example.ucodgt.R;
import java.util.List;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder>{

    private List<ClientDTO> clientList;
    private List<WorkerDTO> workerList;

    private Context context;

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

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        if (position < clientList.size()) {
            ClientDTO clientDTO = clientList.get(position);
            holder.textDni.setText(clientDTO.getDni());
            holder.textType.setText("client");
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
            holder.itemView.setOnClickListener(v -> {
                Intent goShowUser=new Intent(context,CheckUserToFind.class);
                goShowUser.putExtra("dni",workerDTO.getDni());
                goShowUser.putExtra("userToFind","worker");
                startActivity(context,goShowUser,null);
            });
        }
    }

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

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView textDni,textType;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            textDni = itemView.findViewById(R.id.text_dni);
            textType = itemView.findViewById(R.id.text_type);
        }
    }

}
