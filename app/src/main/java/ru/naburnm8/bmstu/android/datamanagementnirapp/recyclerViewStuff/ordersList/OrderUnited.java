package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList;

import org.jetbrains.annotations.NotNull;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Orders;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OrderUnited implements Serializable {
    private String dateOfTransaction;
    private Clients client;
    private ArrayList<Orders> orders;
    public OrderUnited(ArrayList<Orders> orders) {
        this.dateOfTransaction = orders.get(0).getDateOfTransaction();
        this.client = orders.get(0).getClient();
        this.orders = new ArrayList<>(orders);
        for (Orders order : orders) {
            if (!order.getDateOfTransaction().equals(dateOfTransaction) || !order.getClient().equals(client)) {
                throw new RuntimeException("Dataset exception: Orders key pair mismatch");
            }
        }

    }
    public OrderUnited() {
        this.dateOfTransaction = getTodayDate();
        this.client = null;
        this.orders = new ArrayList<>();
    }
    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(String date_of_transaction) {
        this.dateOfTransaction = date_of_transaction;
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
        return "Client: " + client.getLastName() + " date: " + dateOfTransaction + " listLen: " + orders.size();
    }
    public static String getTodayDate() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return formatter.format(today);
        }
        return "";
    }
}
