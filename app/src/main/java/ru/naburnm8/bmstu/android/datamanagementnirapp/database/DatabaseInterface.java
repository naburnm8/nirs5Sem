package ru.naburnm8.bmstu.android.datamanagementnirapp.database;

import java.sql.Connection;

/** @noinspection SpellCheckingInspection*/
@Deprecated
public interface DatabaseInterface {
    String getJDBC_URLfromString(String serverURL);
    void loadJDBCdriver();
}
