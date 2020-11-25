package access_roles;

import entities.User;

//WIP
public class CustomRoles implements Roles{

    private String roleType;
    private boolean canCreateTask;
    private boolean adminAccess;

    public CustomRoles(String roleType, boolean canCreateTask, boolean adminAccess){
        this.roleType = roleType;
        this.canCreateTask = canCreateTask;
        this.adminAccess = adminAccess;
    }
    public String roleType()
    {
        return this.roleType;
    }

    public boolean canCreateTask()
    {
        return this.canCreateTask;
    }

    public boolean adminAccess()
    {
        return this.adminAccess;
    }

    //TODO
}
