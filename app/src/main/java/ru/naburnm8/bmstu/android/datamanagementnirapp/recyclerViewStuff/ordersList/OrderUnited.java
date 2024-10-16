package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList;

import org.jetbrains.annotations.NotNull;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Orders;

import java.util.ArrayList;

public class OrderUnited {
    private String date_of_transaction;
    private Clients client;
    private ArrayList<Orders> orders;
    public OrderUnited(ArrayList<Orders> orders) {
        this.date_of_transaction = orders.get(0).getDateOfTransaction();
        this.client = orders.get(0).getClient();
        this.orders = new ArrayList<>(orders);
        for (Orders order : orders) {
            if (!order.getDateOfTransaction().equals(date_of_transaction) || !order.getClient().equals(client)) {
                throw new RuntimeException("Dataset exception: Orders key pair mismatch");
            }
        }

    }
    public String getDate_of_transaction() {
        return date_of_transaction;
    }

    public void setDate_of_transaction(String date_of_transaction) {
        this.date_of_transaction = date_of_transaction;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public ArrayList<Orders> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Orders> orders) {
        this.orders = orders;
    }

    @NotNull
    @Override
    public String toString() {
        return "Client: " + client.getLastName() + " date: " + date_of_transaction + " listLen: " + orders.size();
    }
}
