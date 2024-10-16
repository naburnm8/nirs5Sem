package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList;

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
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Orders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {
    private final OnDBandRecyclerListener context;
    private final ArrayList<Orders> ordersArrayList;
    private final ArrayList<OrderUnited> orderUnitedArrayList;

    public OrdersAdapter(ArrayList<Orders> ordersArrayList, OnDBandRecyclerListener context) {
        this.ordersArrayList = ordersArrayList;
        this.context = context;
        this.orderUnitedArrayList = new ArrayList<>();
        fillUnitedOrders(getUniqueKeyPairs());
    }

    @NonNull
    @NotNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orders_row, viewGroup, false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OrdersViewHolder ordersViewHolder, int i) {
        Orders order = ordersArrayList.get(i);
        ordersViewHolder.bind(order);
    }

    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }

    private Set<KeyPair> getUniqueKeyPairs(){
        Set<KeyPair> uniqueKeyPairs = new HashSet<>();
        for(Orders order : ordersArrayList){
            uniqueKeyPairs.add(new KeyPair(order));
        }
        return uniqueKeyPairs;
    }

    private void fillUnitedOrders(Set<KeyPair> uniqueKeyPairs){
        ArrayList<Orders> orders = new ArrayList<>();
        for(KeyPair keyPair : uniqueKeyPairs){
            for(Orders order : ordersArrayList){
                String date = order.getDateOfTransaction();
                Clients client = order.getClient();
                if(date.equals(keyPair.getDateOfTransaction()) && client.equals(keyPair.getClient())){
                    orders.add(order);
                }
            }
            orderUnitedArrayList.add(new OrderUnited(orders));
            orders.clear();
        }
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, nameItem, quantityItem, dateOfTransaction, clientId;
        Button editButton, deleteButton;

        public OrdersViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.tvOrderId);
            nameItem = itemView.findViewById(R.id.tvNameItem);
            quantityItem = itemView.findViewById(R.id.tvQuantityItem);
            dateOfTransaction = itemView.findViewById(R.id.tvDateOfTransaction);
            clientId = itemView.findViewById(R.id.tvClientId);
            editButton = itemView.findViewById(R.id.editOrderButton);
            deleteButton = itemView.findViewById(R.id.deleteOrderButton);
        }

        public void bind(final Orders order) {
            orderId.setText(String.valueOf(order.getId()));
            nameItem.setText(order.getItem().getItemName());
            quantityItem.setText(String.valueOf(order.getqItem()));
            dateOfTransaction.setText(order.getDateOfTransaction());
            String name = order.getClient().getFirstName() + " " + order.getClient().getLastName();
            clientId.setText(name);


            editButton.setOnClickListener(view -> {
                context.onEditClick(order);
            });

            deleteButton.setOnClickListener(view -> {
                Toast.makeText(itemView.getContext(), R.string.holdDownDelete, Toast.LENGTH_LONG).show();
            });

            deleteButton.setOnLongClickListener(view -> {
                context.onDeleteClick(order);
                return true;
            });
        }
    }
}

