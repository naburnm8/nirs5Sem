package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Orders;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;

import java.util.ArrayList;
import java.util.List;

public class OrdersResponse implements Recordable {
    private List<Orders> data;
    private boolean status;
    private Orders dataSinglet;

    public OrdersResponse(){
        data = new ArrayList<>();
        status = false;
    }
    public OrdersResponse(boolean status){
        this.status = status;
    }
    @Override
    public ArrayList<String> parseToRecord() {
        return null;
    }

    @Override
    public String parseToString() {
        if(data == null && dataSinglet != null){
            return dataSinglet.toString();
        }
        if(data != null){
            return data.toString();
        }
        return "";
    }

    public List<Orders> getData() {
        return data;
    }

    public void setData(List<Orders> data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Orders getDataSinglet() {
        return dataSinglet;
    }

    public void setDataSinglet(Orders dataSinglet) {
        this.dataSinglet = dataSinglet;
    }
}
