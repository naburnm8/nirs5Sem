package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models;

import com.google.gson.annotations.SerializedName;

public class Orders {

    @SerializedName("id")
    private int id;

    @SerializedName("item")
    private Catalogue item;

    @SerializedName("qItem")
    private int qItem;

    @SerializedName("dateOfTransaction")
    private String dateOfTransaction;

    @SerializedName("client")
    private Clients client;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Catalogue getItem() {
        return item;
    }

    public void setItem(Catalogue item) {
        this.item = item;
    }

    public int getqItem() {
        return qItem;
    }

    public void setqItem(int qItem) {
        this.qItem = qItem;
    }

    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(String dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }
}
