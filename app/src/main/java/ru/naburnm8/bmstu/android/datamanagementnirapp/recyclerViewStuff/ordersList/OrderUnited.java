package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList;

import org.jetbrains.annotations.NotNull;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Orders;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class OrderUnited implements Serializable {
    private String dateOfTransaction;
    private Clients client;
    private ArrayList<Orders> orders;
    private int totalCost;
    private final String currency_format = "₽";
    public OrderUnited(ArrayList<Orders> orders) {
        this.dateOfTransaction = orders.get(0).getDateOfTransaction();
        this.client = orders.get(0).getClient();
        this.orders = new ArrayList<>(orders);
        for (Orders order : orders) {
            if (!order.getDateOfTransaction().equals(dateOfTransaction) || !order.getClient().equals(client)) {
                throw new RuntimeException("Dataset exception: Orders key pair mismatch");
            }
        }
        this.totalCost = countTotalCost();
    }
    public OrderUnited() {
        this.dateOfTransaction = getTodayDate();
        this.client = null;
        this.orders = new ArrayList<>();
        this.totalCost = 0;
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
        this.totalCost = countTotalCost();
    }

    @NotNull
    @Override
    public String toString() {
        return "Client: " + client.getLastName() + " date: " + dateOfTransaction + " listLen: " + orders.size();
    }
    public static String getTodayDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(today);
    }
    public String getTotalCostFormatted(){
        return String.valueOf(totalCost) + currency_format;
    }
    public int getTotalCost(){
        return totalCost;
    }
    public static Comparator<OrderUnited> dateComparator = (o1, o2) -> {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(o1.getDateOfTransaction(), formatter);
        LocalDate date2 = LocalDate.parse(o2.getDateOfTransaction(), formatter);
        return date1.compareTo(date2);
    };
    public static int compareDates(OrderUnited e1, OrderUnited e2){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(e1.getDateOfTransaction(), formatter);
        LocalDate date2 = LocalDate.parse(e2.getDateOfTransaction(), formatter);
        return date1.compareTo(date2);
    }
    public static int getTotal(ArrayList<OrderUnited> orders){
        int sum = 0;
        for(OrderUnited orderUnited: orders){
            sum+=orderUnited.getTotalCost();
        }
        return sum;
    }
    public static Comparator<OrderUnited> totalCostComparator = Comparator.comparingInt(OrderUnited::getTotalCost);
    private int countTotalCost(){
        int sum = 0;
        for(Orders order: orders){
            sum+=order.getqItem()*order.getItem().getItemPrice();
        }
        return sum;
    }



}
