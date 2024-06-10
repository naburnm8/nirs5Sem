package ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.dialects;

import android.util.Log;
import ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.DatabaseOperator;

public class MicrosoftSQLOperator extends DatabaseOperator {
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
