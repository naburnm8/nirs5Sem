package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Clients implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("patronymic")
    private String patronymic;

    @SerializedName("phone")
    private String phone;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @NotNull
    @Override
    public String toString() {
        return "Clients [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Clients) {
            Clients clients = (Clients)obj;
            return this.toString().equals(clients.toString());
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public static Clients getNeutralClient(){
        Clients clients = new Clients();
        clients.setFirstName("No client chosen");
        clients.setLastName(" ");
        return clients;
    }
}
