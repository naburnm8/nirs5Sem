package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList.editAdd;

import android.view.View;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Orders;

public interface OnItemClickListener {
    public void onItemClick(Orders order);
}
