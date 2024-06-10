package ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase;

import java.util.ArrayList;

public interface AsyncReceiver {
    void setLogged(String logged);
    void setData(ArrayList<ArrayList<String>> results);
}
