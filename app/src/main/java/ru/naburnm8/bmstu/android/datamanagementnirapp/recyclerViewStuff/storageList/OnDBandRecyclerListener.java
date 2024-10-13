package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.storageList;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Storage;

public interface OnDBandRecyclerListener extends RESTDBOutput {
    void onEditClick(Storage item);
    void onDeleteClick(Storage item);
}
