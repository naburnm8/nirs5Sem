package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models;

import com.google.gson.annotations.SerializedName;

public class Catalogue {
    @SerializedName("id")
    private int id;

    @SerializedName("itemName")
    private String itemName;

    @SerializedName("itemPrice")
    private int itemPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }
    @Override
    public String toString() {
        return "id=" + id + " itemName=" + itemName + " itemPrice=" + itemPrice;
    }
}
