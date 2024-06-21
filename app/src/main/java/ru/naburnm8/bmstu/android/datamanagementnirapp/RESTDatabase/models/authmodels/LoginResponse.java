package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.authmodels;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;

import java.util.ArrayList;


public class LoginResponse implements Recordable {
    private String token;
    private final String TYPE = "Bearer";
    private int id;
    private String username;
    private String role;

    public LoginResponse(String token, int id, String username, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTYPE() {
        return TYPE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public ArrayList<String> parseToRecord() {
        ArrayList<String> record = new ArrayList<>();
        record.add(token);
        record.add(Integer.toString(id));
        record.add(username);
        record.add(role);
        return record;
    }

    @Override
    public String parseToString() {
        return parseToRecord().toString();
    }
}
