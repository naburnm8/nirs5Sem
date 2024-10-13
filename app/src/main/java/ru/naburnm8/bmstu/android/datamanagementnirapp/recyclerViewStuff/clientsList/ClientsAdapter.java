package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.clientsList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import ru.naburnm8.bmstu.android.datamanagementnirapp.R;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;

import java.util.ArrayList;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ClientsViewHolder> {
    private final OnDBandRecyclerListener context;
    private final ArrayList<Clients> clientsArrayList;

    public ClientsAdapter(ArrayList<Clients> clientsArrayList, OnDBandRecyclerListener context) {
        this.clientsArrayList = clientsArrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ClientsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.client_row, viewGroup, false); // Inflate the new layout
        return new ClientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ClientsViewHolder clientsViewHolder, int i) {
        Clients client = clientsArrayList.get(i);
        clientsViewHolder.bind(client);
    }

    @Override
    public int getItemCount() {
        return clientsArrayList.size();
    }

    public class ClientsViewHolder extends RecyclerView.ViewHolder {
        TextView clientId, firstName, lastName, patronymic, phoneNum;
        Button editButton, deleteButton;

        public ClientsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            clientId = itemView.findViewById(R.id.tvClientId);
            firstName = itemView.findViewById(R.id.tvFirstName);
            lastName = itemView.findViewById(R.id.tvLastName);
            patronymic = itemView.findViewById(R.id.tvPatronymic);
            phoneNum = itemView.findViewById(R.id.tvPhoneNum);
            editButton = itemView.findViewById(R.id.editClientButton);
            deleteButton = itemView.findViewById(R.id.deleteClientButton);
        }

        public void bind(final Clients client) {
            clientId.setText(String.valueOf(client.getId()));
            firstName.setText(client.getFirstName());
            lastName.setText(client.getLastName());
            patronymic.setText(client.getPatronymic());
            phoneNum.setText(client.getPhone());

            editButton.setOnClickListener(view -> {
                context.onEditClick(client);
            });

            deleteButton.setOnClickListener(view -> {
                if (context instanceof Context) {
                    Toast.makeText(((Context) context).getApplicationContext(), ((Context) context).getText(R.string.holdDownDelete), Toast.LENGTH_LONG).show();
                }
            });

            deleteButton.setOnLongClickListener(view -> {
                context.onDeleteClick(client);
                return true;
            });
        }
    }
}

