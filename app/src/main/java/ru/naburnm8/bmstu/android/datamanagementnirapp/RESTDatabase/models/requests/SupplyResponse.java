package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Storage;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Supply;

import java.util.ArrayList;
import java.util.List;

public class SupplyResponse implements Recordable {
    private List<Supply> data;
    private boolean status;
    private Supply dataSinglet;
    public SupplyResponse() {
        data = new ArrayList<>();
        status = false;
    }
    public SupplyResponse(boolean status) {
        this.status = status;
    }

    @Override
    public ArrayList<String> parseToRecord() {
        return null;
    }

    @Override
    public String parseToString() {
        return "";
    }

    public List<Supply> getData() {
        return data;
    }

    public void setData(List<Supply> data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Supply getDataSinglet() {
        return dataSinglet;
    }

    public void setDataSinglet(Supply dataSinglet) {
        this.dataSinglet = dataSinglet;
    }
}
