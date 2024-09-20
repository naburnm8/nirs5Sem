package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.catalogueList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import ru.naburnm8.bmstu.android.datamanagementnirapp.R;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Catalogue;

import java.util.ArrayList;


public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.CatalogueViewHolder> {
    private final OnDBandRecyclerListener context;
    private final ArrayList<Catalogue> catalogueArrayList;

    public CatalogueAdapter(ArrayList<Catalogue> catalogueArrayList, OnDBandRecyclerListener context) {
        this.catalogueArrayList = catalogueArrayList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public CatalogueViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.catalogue_row, viewGroup, false);
        return new CatalogueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CatalogueViewHolder catalogueViewHolder, int i) {
        Catalogue catalogue = catalogueArrayList.get(i);
        catalogueViewHolder.bind(catalogue);
    }

    @Override
    public int getItemCount() {
        return catalogueArrayList.size();
    }


    public class CatalogueViewHolder extends RecyclerView.ViewHolder{
        TextView catalogueName, cataloguePrice, catalogueID;
        Button editButton, deleteButton;

        public CatalogueViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            catalogueName = itemView.findViewById(R.id.catalogue_name);
            cataloguePrice = itemView.findViewById(R.id.catalogue_price);
            editButton = itemView.findViewById(R.id.editCatalogueButton);
            deleteButton = itemView.findViewById(R.id.deleteCatalogueButton);
            catalogueID = itemView.findViewById(R.id.id_catalogue);
        }
        public void bind(final Catalogue catalogue){
            catalogueName.setText(catalogue.getItemName());
            String price = String.valueOf(catalogue.getItemPrice());
            cataloguePrice.setText(price);
            String id = String.valueOf(catalogue.getId());
            catalogueID.setText(id);

            editButton.setOnClickListener(view -> {
                context.onEditClick(catalogue);
            });

            deleteButton.setOnClickListener(view -> {
                context.onDeleteClick(catalogue);
            });
        }

    }
}
