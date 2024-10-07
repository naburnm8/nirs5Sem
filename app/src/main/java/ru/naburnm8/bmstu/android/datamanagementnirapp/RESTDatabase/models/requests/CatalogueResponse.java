package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Catalogue;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;

import java.util.ArrayList;
import java.util.List;


public class CatalogueResponse implements Recordable {
    private List<Catalogue> data;
    private boolean status;
    private Catalogue dataSinglet;

    @Override
    public ArrayList<String> parseToRecord() {
        ArrayList<String> record = new ArrayList<>();
        for(Catalogue catalogue : data) {
            record.add(catalogue.toString());
        }
        return record;
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

    public List<Catalogue> getData() {
        return data;
    }

    public void setData(List<Catalogue> data) {
        this.data = data;
    }

    public boolean getStatus() {
        return status;
    }

    public CatalogueResponse(boolean delete){
        status = delete;
    }
    public CatalogueResponse(){
        data = new ArrayList<>();
        status = false;
    }

    public Catalogue getDataSinglet() {
        return dataSinglet;
    }

    public void setDataSinglet(Catalogue dataSinglet) {
        this.dataSinglet = dataSinglet;
    }
}
