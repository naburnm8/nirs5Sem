package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.catalogueList;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Catalogue;

public interface OnDBandRecyclerListener extends RESTDBOutput {
    void onEditClick(Catalogue item);
    void onDeleteClick(Catalogue item);
}
