package ru.naburnm8.bmstu.android.datamanagementnirapp.database;

import java.sql.ResultSet;
@Deprecated
public interface AsyncDBOutput {
    void setLogged(String logged);
    void setData(ResultSet resultSet);
}
