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

    public static List<ActionData> generateAdminActions(){
        ArrayList<ActionData> out = new ArrayList<>();
        out.add(new ActionData("RWU Catalogue", new String[]{"Read", "Write", "Update"}, "Table controls", "catalogue"));
        out.add(new ActionData("RWU Clients", new String[]{"Read", "Write", "Update"}, "Table controls", "clients"));
        out.add(new ActionData("RWU Orders", new String[]{"Read", "Write", "Update"}, "Table controls", "orders"));
        out.add(new ActionData("RWU Storage", new String[]{"Read", "Write", "Update"}, "Table controls", "storage"));
        out.add(new ActionData("RWU Supply", new String[]{"Read", "Write", "Update"}, "Table controls", "supply"));
        out.add(new ActionData("RWU Users", new String[]{"Read", "Write", "Update"}, "Users control", "users"));
        return out;
    }

    public static List<ActionData> generateConsultantActions(){
        ArrayList<ActionData> out = new ArrayList<>();
        out.add(new ActionData("R Catalogue", new String[]{"Read"}, "Table controls", "catalogue"));
        out.add(new ActionData("RWU Clients", new String[]{"Read", "Write", "Update"}, "Table controls", "clients"));
        out.add(new ActionData("RWU Orders", new String[]{"Read", "Write", "Update"}, "Table controls", "orders"));
        out.add(new ActionData("R Storage", new String[]{"Read"}, "Table controls", "storage"));
        out.add(new ActionData("R Supply", new String[]{"Read"}, "Table controls", "supply"));
        return out;
    }

    public static List<ActionData> generateStorageActions(){
        ArrayList<ActionData> out = new ArrayList<>();
        out.add(new ActionData("R Catalogue", new String[]{"Read"}, "Table controls", "catalogue"));
        out.add(new ActionData("RWU Storage", new String[]{"Read", "Write", "Update"}, "Table controls", "storage"));
        out.add(new ActionData("R Supply", new String[]{"Read"}, "Table controls", "supply"));
        return out;
    }
    public String getAffected() {
        return affected;
    }

    public void setAffected(String affected) {
        this.affected = affected;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getPermissionsString(){
        String out = "";
        for(String permission : permissions){
            out += permission + ", ";
        }
        return out.substring(0, out.length()-2);
    }
}
