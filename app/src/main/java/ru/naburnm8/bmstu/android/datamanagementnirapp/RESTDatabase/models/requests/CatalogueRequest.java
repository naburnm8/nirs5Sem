package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.requests;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Catalogue;

public class CatalogueRequest {
    private int id;
    private String method;
    private Catalogue object;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Catalogue getObject() {
        return object;
    }

    public void setObject(Catalogue object) {
        this.object = object;
    }
}
