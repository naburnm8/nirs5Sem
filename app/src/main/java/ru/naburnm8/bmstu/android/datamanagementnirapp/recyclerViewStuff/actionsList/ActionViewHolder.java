package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.actionsList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import ru.naburnm8.bmstu.android.datamanagementnirapp.R;

public class ActionViewHolder extends RecyclerView.ViewHolder{
    public TextView actionName, permissionList, actionDescription;
    public ImageView actionImage;
    public View view;
    public ActionViewHolder(View itemView) {
        super(itemView);
        actionName = itemView.findViewById(R.id.actionName);
        permissionList = itemView.findViewById(R.id.permissionsList);
        actionDescription = itemView.findViewById(R.id.actionDescription);
        actionImage = itemView.findViewById(R.id.actionImage);
        view = itemView;
    }
}
