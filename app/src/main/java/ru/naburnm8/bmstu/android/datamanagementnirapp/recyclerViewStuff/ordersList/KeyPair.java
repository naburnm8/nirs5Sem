package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList;

import org.jetbrains.annotations.NotNull;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Orders;

public class KeyPair {
    private Clients client;
    private String dateOfTransaction;
    public KeyPair(Orders order) {
        this.client = order.getClient();
        this.dateOfTransaction = order.getDateOfTransaction();
    }
    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(String dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    @NotNull
    @Override
    public String toString() {
        return dateOfTransaction + ", " + client.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof KeyPair) {
            KeyPair keyPair = (KeyPair) o;
            return this.toString().equals(keyPair.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
