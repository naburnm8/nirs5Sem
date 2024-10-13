package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.supplyList;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Storage;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Supply;

public interface OnDBandRecyclerListener extends RESTDBOutput {
    void onEditClick(Supply item);
    void onDeleteClick(Supply item);
}
