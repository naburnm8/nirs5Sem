package ru.naburnm8.bmstu.android.datamanagementnirapp.misc;

import ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList.OrderUnited;

import java.util.ArrayList;

public class ReportData {
    private String startDate;
    private String endDate;
    private String username;
    private String dateGenerated;
    int orderCount;
    int orderPriceTotal;
    ArrayList<OrderUnited> orders;

    public ReportData(String startDate, String endDate, String username, String dateGenerated, int orderCount, int orderPriceTotal, ArrayList<OrderUnited> orders) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.username = username;
        this.dateGenerated = dateGenerated;
        this.orderCount = orderCount;
        this.orderPriceTotal = orderPriceTotal;
        this.orders = orders;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateGenerated() {
        return dateGenerated;
    }

    public void setDateGenerated(String dateGenerated) {
        this.dateGenerated = dateGenerated;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public int getOrderPriceTotal() {
        return orderPriceTotal;
    }

    public void setOrderPriceTotal(int orderPriceTotal) {
        this.orderPriceTotal = orderPriceTotal;
    }

    public ArrayList<OrderUnited> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<OrderUnited> orders) {
        this.orders = orders;
    }
}
