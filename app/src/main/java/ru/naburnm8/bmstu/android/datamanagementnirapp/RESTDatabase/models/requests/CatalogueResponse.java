package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Catalogue;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;

import java.util.ArrayList;
import java.util.List;


public class CatalogueResponse implements Recordable {
    private List<Catalogue> data;
    private boolean status;
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
        return data.toString();
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
}
