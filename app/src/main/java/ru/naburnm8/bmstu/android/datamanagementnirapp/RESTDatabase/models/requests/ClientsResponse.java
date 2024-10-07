package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;

import java.util.ArrayList;
import java.util.List;

public class ClientsResponse implements Recordable {
    private List<Clients> data;
    private boolean status;
    private Clients dataSinglet;

    public ClientsResponse(){
        data = new ArrayList<>();
        status = false;
    }
    public ClientsResponse(boolean status){
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

    public List<Clients> getData() {
        return data;
    }

    public void setData(List<Clients> data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Clients getDataSinglet() {
        return dataSinglet;
    }

    public void setDataSinglet(Clients dataSinglet) {
        this.dataSinglet = dataSinglet;
    }
}
