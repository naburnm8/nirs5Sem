package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.clientsList;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Catalogue;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;

public interface OnDBandRecyclerListener extends RESTDBOutput {
    void onEditClick(Clients item);
    void onDeleteClick(Clients item);
}
