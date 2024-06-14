package ru.naburnm8.bmstu.android.datamanagementnirapp.recyclerViewStuff.actionsList;

import ru.naburnm8.bmstu.android.datamanagementnirapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActionData implements Serializable {
    private String actionName;
    private String[] permissions;
    private String description;
    private String affected;
    private int picture;

    public ActionData(String actionName, String[] permissions, String description, String affected) {
        this.actionName = actionName;
        this.permissions = permissions;
        this.description = description;
        this.affected = affected;
        picture = R.drawable.ic_launcher_background;
    }

    public ActionData(String actionName, String[] permissions, String description, String affected, int picture) {
        this.actionName = actionName;
        this.permissions = permissions;
        this.description = description;
        this.affected = affected;
        this.picture = picture;
    }


    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<ActionData> generateUnauthorizedActions(){
        ArrayList<ActionData> out = new ArrayList<>();
        out.add(new ActionData("Unauthorized", new String[]{"None"}, "You need to log in.", "none"));
        return out;
    }

    public String getAffected() {
        return affected;
    }

    public void setAffected(String affected) {
        this.affected = affected;
    }
}
