package ru.naburnm8.bmstu.android.datamanagementnirapp.database;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLDataException;
import java.sql.SQLException;
//NEED TO MAKE IT ASYNC
@Deprecated
public abstract class DatabaseOperator implements DatabaseInterface{
    private String JDBC_URL;
    private String JDBC_USER;
    private String JDBC_PASSWORD;
    private boolean validServer = true;
    private String errorMessage;
    private Connection con;
    public DatabaseOperator(String serverURL, String JDBC_USER, String JDBC_PASSWORD){
        this.JDBC_USER = JDBC_USER;
        this.JDBC_PASSWORD = JDBC_PASSWORD;
        this.JDBC_URL = getJDBC_URLfromString(serverURL);
    }
    public void testConnection(){
        Connection connection = null;
        try {
            loadJDBCdriver();
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            String message = e.getMessage();
            System.out.println(message);
            errorMessage = message;
            Log.println(Log.ERROR, "Database", e.getMessage());
            if (message == null){
                message = "null";
            }
            if (message.contains("Login failed")){
                validServer = true;
            }
        } finally {
            if (connection != null) {
                try{connection.close();} catch(Exception e){Log.println(Log.ERROR, "Database", e.getMessage());;}
            }
        }
        validServer = true;
    }
    public String getErrorMessage(){
        return errorMessage;
    }
    public boolean isValidServer(){
        return validServer;
    }
    protected void openConnection(){
        if(validServer){
            try{
                con = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_USER);
            } catch (Exception e) {
                Log.println(Log.ERROR, "Database", e.getMessage());
            }
        }
    }
    protected void closeConnection(){
        if(con != null){
            try{
                con.close();
            } catch (Exception e) {Log.println(Log.ERROR, "Database", e.getMessage());}
        }
    }



}
