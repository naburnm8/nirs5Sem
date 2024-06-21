package ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Recordable;

public interface RESTDBOutput {
    void setLogged(String logged);
    void setData(Recordable data);
}
