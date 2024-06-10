package ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase;

import android.os.AsyncTask;
import android.util.Log;
import ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.databaseData.DatabaseQuery;
import ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.databaseData.DatabaseResult;

import java.sql.*;
import java.util.ArrayList;


abstract public class DatabaseOperator extends AsyncTask<DatabaseQuery, String, DatabaseResult> implements DatabaseInterface {
    @Override
    protected DatabaseResult doInBackground(DatabaseQuery... params) {

        DatabaseQuery databaseQuery = params[0];
        String JDBC_URL = getJDBC_URLfromString(databaseQuery.getURL());
        String JDBC_USER = databaseQuery.getJDBC_USERNAME();
        String JDBC_PASSWORD = databaseQuery.getJDBC_PASSWORD();
        String query = databaseQuery.getJDBC_Query();
        AsyncReceiver context = databaseQuery.getContext();
        Connection connection = null;
        String log = "Successful";
        ResultSet resultSet;
        Statement statement = null;
        DatabaseResult databaseResult = new DatabaseResult("", null, context);
        try {
            loadJDBCdriver();
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            databaseResult.setResults(getResults(resultSet));
            databaseResult.setLog(log);
            resultSet.close();
        } catch (SQLException e) {
            log = e.getMessage();
            databaseResult.setLog(log);
            Log.println(Log.ERROR, "Database", e.getMessage());
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                Log.println(Log.ERROR, "Database", e.getMessage());
            }
        }
        return databaseResult;
    }
    @Override
    protected void onPostExecute(DatabaseResult result) {
        result.getContext().setData(result.getResults());
        result.getContext().setLogged(result.getLog());
    }
    protected ArrayList<ArrayList<String>> getResults(ResultSet resultSet) {
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        try{
        while(resultSet.next()){
            ArrayList<String> row = new ArrayList<>();
            for(int i = 0; i < resultSet.getMetaData().getColumnCount(); i++){
                row.add(resultSet.getString(i+1));
            }
            results.add(row);
        }
        } catch (SQLException e) {
            Log.println(Log.ERROR, "Database", e.getMessage());
        }
        return results;
    }

}
