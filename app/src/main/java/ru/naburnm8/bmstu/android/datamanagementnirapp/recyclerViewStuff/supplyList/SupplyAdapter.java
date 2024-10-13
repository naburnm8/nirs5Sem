package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.supplyList;

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
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Supply;

import java.util.ArrayList;

public class SupplyAdapter extends RecyclerView.Adapter<SupplyAdapter.SupplyViewHolder> {
    private final OnDBandRecyclerListener context;
    private final ArrayList<Supply> supplyArrayList;

    public SupplyAdapter(ArrayList<Supply> supplyArrayList, OnDBandRecyclerListener context) {
        this.supplyArrayList = supplyArrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public SupplyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.supply_row, viewGroup, false);
        return new SupplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SupplyViewHolder supplyViewHolder, int i) {
        Supply supply = supplyArrayList.get(i);
        supplyViewHolder.bind(supply);
    }

    @Override
    public int getItemCount() {
        return supplyArrayList.size();
    }

    public class SupplyViewHolder extends RecyclerView.ViewHolder {
        TextView supplyId, dateOfArrival, itemId, quantity;
        Button editButton, deleteButton;

        public SupplyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            supplyId = itemView.findViewById(R.id.tvSupplyId);
            dateOfArrival = itemView.findViewById(R.id.tvDateOfArrival);
            itemId = itemView.findViewById(R.id.tvItemId);
            quantity = itemView.findViewById(R.id.tvQuantity);
            editButton = itemView.findViewById(R.id.editSupplyButton);
            deleteButton = itemView.findViewById(R.id.deleteSupplyButton);
        }

        public void bind(final Supply supply) {
            dateOfArrival.setText(supply.getDateOfArrival());
            itemId.setText(supply.getItem().getItemName());
            quantity.setText(String.valueOf(supply.getQuantity()));

            editButton.setOnClickListener(view -> {
                context.onEditClick(supply);
            });

            deleteButton.setOnClickListener(view -> {
                Toast.makeText(itemView.getContext(), R.string.holdDownDelete, Toast.LENGTH_LONG).show();
            });

            deleteButton.setOnLongClickListener(view -> {
                context.onDeleteClick(supply);
                return true;
            });
        }
    }
}
