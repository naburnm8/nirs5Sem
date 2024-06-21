package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Message implements Recordable {
    @SerializedName("message")
    private String message;

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public ArrayList<String> parseToRecord() {
        ArrayList<String> record = new ArrayList<>();
        record.add(message);
        return record;
    }

    @Override
    public String parseToString() {
        return message;
    }
}
