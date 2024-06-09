package ru.naburnm8.bmstu.android.datamanagementnirapp.database;

import java.sql.ResultSet;

public interface AsyncDBOutput {
    void setLogged(String logged);
    void setData(ResultSet resultSet);
}
