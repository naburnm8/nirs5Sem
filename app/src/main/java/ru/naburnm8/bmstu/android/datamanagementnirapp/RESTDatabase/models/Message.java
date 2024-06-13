package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models;

import com.google.gson.annotations.SerializedName;

public class Message {
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
}
