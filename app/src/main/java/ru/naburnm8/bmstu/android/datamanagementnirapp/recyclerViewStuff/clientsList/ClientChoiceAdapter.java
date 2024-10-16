package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.clientsList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.naburnm8.bmstu.android.datamanagementnirapp.R;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;

import java.util.ArrayList;

public class ClientChoiceAdapter extends RecyclerView.Adapter<ClientChoiceAdapter.ClientViewHolder> {
    private final ArrayList<Clients> clientList;
    private final OnDBandRecyclerListener listener;

    public ClientChoiceAdapter(ArrayList<Clients> clientList, OnDBandRecyclerListener listener) {
        this.clientList = clientList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_choice_row, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        Clients client = clientList.get(position);
        String fullName = client.getLastName() + " " + client.getFirstName() + " " + client.getPatronymic();
        holder.clientName.setText(fullName);
        holder.clientPhone.setText(client.getPhone());

        holder.chooseButton.setOnClickListener(v -> listener.onDeleteClick(client));
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public static class ClientViewHolder extends RecyclerView.ViewHolder {
        TextView clientName;
        TextView clientPhone;
        Button chooseButton;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            clientName = itemView.findViewById(R.id.clientName);
            clientPhone = itemView.findViewById(R.id.clientPhone);
            chooseButton = itemView.findViewById(R.id.deleteClientButton);
        }
    }
}

