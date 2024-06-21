package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models;

import java.util.ArrayList;

public interface Recordable {
    public ArrayList<String> parseToRecord();
    public String parseToString();
}
