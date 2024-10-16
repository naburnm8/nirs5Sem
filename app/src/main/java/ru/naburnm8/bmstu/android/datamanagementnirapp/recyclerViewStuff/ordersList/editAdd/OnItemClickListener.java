package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.ordersList.editAdd;

import android.view.View;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.RESTDBOutput;
import ru.naburnm8.bmstu.android.datamanagementnirapp.RESTDatabase.models.Orders;

public interface OnItemClickListener extends RESTDBOutput {
    public void onItemClick(Orders order);
}
