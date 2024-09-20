package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.actionsList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import ru.naburnm8.bmstu.android.datamanagementnirapp.R;
import ru.naburnm8.bmstu.android.datamanagementnirapp.tableViewActivity.CatalogueViewActivity;

import java.util.ArrayList;

public class ActionAdapter extends RecyclerView.Adapter<ActionViewHolder>{
    private ArrayList<ActionData> actions;
    private LayoutInflater inflater;
    Context context;

    public ActionAdapter(Context context, ArrayList<ActionData> actions) {
        this.inflater = LayoutInflater.from(context);
        this.actions = actions;
        this.context = context;
    }


    @NonNull
    @NotNull
    @Override
    public ActionViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.action_list_recycler, parent, false);
        return new ActionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ActionViewHolder holder, int position) {
        String actionName = actions.get(position).getActionName();
        String actionDescription = actions.get(position).getDescription();
        String actionPermissions = actions.get(position).getPermissionsString();
        int actionImage = actions.get(position).getPicture();
        holder.actionName.setText(actionName);
        holder.actionDescription.setText(actionDescription);
        holder.permissionList.setText(actionPermissions);
        holder.actionImage.setImageResource(actionImage);
        int index = holder.getAdapterPosition();
        holder.view.setOnClickListener(view -> handleClick(index));
    }

    private void handleClick(int index) {
        String table = actions.get(index).getAffected();
        switch (table) {
            case "catalogue":
                //Toast.makeText(context, actions.get(index).getAffected(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CatalogueViewActivity.class);
                context.startActivity(intent);
                break;
            case "clients":
                //Toast.makeText(context, actions.get(index).getAffected(), Toast.LENGTH_SHORT).show();
                break;
            case "orders":
                //Toast.makeText(context, actions.get(index).getAffected(), Toast.LENGTH_SHORT).show();
                break;
            case "storage":
                //Toast.makeText(context, actions.get(index).getAffected(), Toast.LENGTH_SHORT).show();
                break;
            case "supply":
                //Toast.makeText(context, actions.get(index).getAffected(), Toast.LENGTH_SHORT).show();
                break;
            case "users":
                //Toast.makeText(context, actions.get(index).getAffected(), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public int getItemCount() {
        return actions.size();
    }
}
