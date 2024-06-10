package ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.dialects;

import android.util.Log;
import ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.DatabaseOperator;

//Driver issue. Won't work.
public class PostgreSQLOperator extends DatabaseOperator {
    @Override
    public String getJDBC_URLfromString(String serverURL) {
        return "jdbc:postgresql://" + serverURL;
    }

    @Override
    public void loadJDBCdriver() {
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e) {
            Log.println(Log.ERROR, "PostgreSQL Driver", "Could not load PostgreSQL Driver");
        }
    }
}
