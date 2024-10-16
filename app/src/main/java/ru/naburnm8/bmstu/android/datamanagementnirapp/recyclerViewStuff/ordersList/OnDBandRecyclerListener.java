package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList;

import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Clients;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Orders;

public interface OnDBandRecyclerListener extends RESTDBOutput {
    void onEditClick(Orders item);
    void onDeleteClick(Orders item);
    void onEditClick(OrderUnited item);
    void onDeleteClick(OrderUnited item);
}
