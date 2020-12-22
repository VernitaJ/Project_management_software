package access_roles;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

public class Role implements Roles {
    
    private String type;
    @JsonIncludeProperties
    private boolean canCreateTask = false;
    @JsonIncludeProperties
    private boolean adminAccess = false;
    
    public Role(String type) {
        this.type = type;
    }

    public Role(String type, boolean canCreateTask, boolean adminAccess) {
        this.type = type;
        this.canCreateTask = canCreateTask;
        this.adminAccess = adminAccess;
    }

    public Role() {
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean canCreateTask() {
        return this.canCreateTask;
    }

    public void setCanCreateTask(boolean canCreateTask) {
        this.canCreateTask = canCreateTask;
    }

    public boolean adminAccess() {
        return this.adminAccess;
    }

    public void setAdminAccess(boolean adminAccess) {
        this.adminAccess = adminAccess;
    }
}
