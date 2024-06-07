package ru.naburnm8.bmstu.android.datamanagementnirapp.database;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MicrosoftSQLOperator extends DatabaseOperator {

    public MicrosoftSQLOperator(String serverURL, String JDBC_USER, String JDBC_PASSWORD) {
        super(serverURL, JDBC_USER, JDBC_PASSWORD);
    }

    @Override
    public String getJDBC_URLfromString(String serverURL) {
        return "jdbc:sqlserver://" + serverURL + ";trustServerCertificate=true";
    }

    @Override
    public void loadJDBCdriver() {
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");}
        catch (ClassNotFoundException e) {
            Log.println(Log.ERROR, "MicrosoftSQLOperator", e.toString());
        }
    }
}
