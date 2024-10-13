package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Storage implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("item")
    private Catalogue item;

    @SerializedName("quantity")
    private int quantity;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
