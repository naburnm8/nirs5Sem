package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList.editAdd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.naburnm8.bmstu.android.datamanagementnirapp.R;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Orders;


import java.util.List;

public class OrdersEditAddAdapter extends RecyclerView.Adapter<OrdersEditAddAdapter.OrdersEditAddViewHolder> {

    private List<Orders> ordersList;
    private OnItemClickListener onItemClickListener;

    public OrdersEditAddAdapter(List<Orders> ordersList, OnItemClickListener onItemClickListener) {
        this.ordersList = ordersList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public OrdersEditAddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_edit_add_row, parent, false);
        return new OrdersEditAddViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersEditAddViewHolder holder, int position) {
        Orders order = ordersList.get(position);
        holder.itemName.setText(order.getItem().getItemName());
        holder.qItem.setText(String.valueOf(order.getqItem()));
        holder.itemView.setOnLongClickListener(v -> {
            onItemClickListener.onItemClick(order);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Orders order);
    }

    static class OrdersEditAddViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView qItem;

        public OrdersEditAddViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            qItem = itemView.findViewById(R.id.item_quantity);
        }
    }
}
