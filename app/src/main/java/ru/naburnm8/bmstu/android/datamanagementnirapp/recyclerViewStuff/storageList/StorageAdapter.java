package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.storageList;

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
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Storage;

import java.util.ArrayList;

public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.StorageViewHolder> {
    private final OnDBandRecyclerListener context;
    private final ArrayList<Storage> storageArrayList;

    public StorageAdapter(ArrayList<Storage> storageArrayList, OnDBandRecyclerListener context) {
        this.storageArrayList = storageArrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public StorageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.storage_row, viewGroup, false);
        return new StorageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StorageViewHolder storageViewHolder, int i) {
        Storage storage = storageArrayList.get(i);
        storageViewHolder.bind(storage);
    }

    @Override
    public int getItemCount() {
        return storageArrayList.size();
    }

    public class StorageViewHolder extends RecyclerView.ViewHolder {
        TextView storageId, itemId, storageQuantity;
        Button editButton, deleteButton;

        public StorageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            storageId = itemView.findViewById(R.id.tvStorageId);
            itemId = itemView.findViewById(R.id.tvItemId);
            storageQuantity = itemView.findViewById(R.id.tvStorageQuantity);
            editButton = itemView.findViewById(R.id.editStorageButton);
            deleteButton = itemView.findViewById(R.id.deleteStorageButton);
        }

        public void bind(final Storage storage) {
            storageId.setText(String.valueOf(storage.getIdItem()));
            itemId.setText(String.valueOf(storage.getItem().getId()));
            storageQuantity.setText(String.valueOf(storage.getQuantity()));

            editButton.setOnClickListener(view -> {
                context.onEditClick(storage);
            });

            deleteButton.setOnClickListener(view -> {
                Toast.makeText(itemView.getContext(), R.string.holdDownDelete, Toast.LENGTH_LONG).show();
            });

            deleteButton.setOnLongClickListener(view -> {
                context.onDeleteClick(storage);
                return true;
            });
        }
    }
}
