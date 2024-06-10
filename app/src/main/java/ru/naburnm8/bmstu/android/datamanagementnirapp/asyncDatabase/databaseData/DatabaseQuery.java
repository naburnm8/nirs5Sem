package ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.databaseData;

import ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.AsyncReceiver;

public class DatabaseQuery {
    private String URL;
    private String JDBC_USERNAME;
    private String JDBC_PASSWORD;
    private String JDBC_Query;
    private AsyncReceiver context;

    public DatabaseQuery(String URL, String JDBC_USERNAME, String JDBC_PASSWORD, String JDBC_Query, AsyncReceiver context) {
        this.URL = URL;
        this.JDBC_USERNAME = JDBC_USERNAME;
        this.JDBC_PASSWORD = JDBC_PASSWORD;
        this.JDBC_Query = JDBC_Query;
        this.context = context;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getJDBC_USERNAME() {
        return JDBC_USERNAME;
    }

    public void setJDBC_USERNAME(String JDBC_USERNAME) {
        this.JDBC_USERNAME = JDBC_USERNAME;
    }

    public String getJDBC_PASSWORD() {
        return JDBC_PASSWORD;
    }

    public void setJDBC_PASSWORD(String JDBC_PASSWORD) {
        this.JDBC_PASSWORD = JDBC_PASSWORD;
    }

    public String getJDBC_Query() {
        return JDBC_Query;
    }

    public void setJDBC_Query(String JDBC_Query) {
        this.JDBC_Query = JDBC_Query;
    }

    public AsyncReceiver getContext() {
        return context;
    }
    public void setContext(AsyncReceiver context) {
        this.context = context;
    }
}
