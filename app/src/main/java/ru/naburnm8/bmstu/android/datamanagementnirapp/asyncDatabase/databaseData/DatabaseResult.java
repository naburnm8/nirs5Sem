package ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.databaseData;

import ru.naburnm8.bmstu.android.datamanagementnirapp.asyncDatabase.AsyncReceiver;

import java.util.ArrayList;

@Deprecated
public class DatabaseResult {
    private String log;
    private ArrayList<ArrayList<String>> results;
    private AsyncReceiver context;


    public DatabaseResult(String log, ArrayList<ArrayList<String>> results, AsyncReceiver context) {
        this.log = log;
        this.results = results;
        this.context = context;
    }

    public String getLog() {
        if (log == null){
            return "No log";
        }
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public ArrayList<ArrayList<String>> getResults() {
        return results;
    }

    public void setResults(ArrayList<ArrayList<String>> results) {
        this.results = results;
    }

    public AsyncReceiver getContext() {
        return context;
    }

    public void setContext(AsyncReceiver context) {
        this.context = context;
    }
}
