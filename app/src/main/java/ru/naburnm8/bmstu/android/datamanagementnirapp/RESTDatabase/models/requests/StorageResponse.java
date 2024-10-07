package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Storage;

import java.util.ArrayList;
import java.util.List;

public class StorageResponse implements Recordable {
    private List<Storage> data;
    private boolean status;
    private Storage dataSinglet;
    public StorageResponse() {
        data = new ArrayList<>();
        status = false;
    }
    public StorageResponse(boolean status) {
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

    public List<Storage> getData() {
        return data;
    }

    public void setData(List<Storage> data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Storage getDataSinglet() {
        return dataSinglet;
    }

    public void setDataSinglet(Storage dataSinglet) {
        this.dataSinglet = dataSinglet;
    }
}
