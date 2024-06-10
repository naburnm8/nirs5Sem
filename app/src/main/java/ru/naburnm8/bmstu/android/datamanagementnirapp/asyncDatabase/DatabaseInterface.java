package ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase;

/** @noinspection SpellCheckingInspection*/
public interface DatabaseInterface {
    String getJDBC_URLfromString(String serverURL);
    void loadJDBCdriver();
}
