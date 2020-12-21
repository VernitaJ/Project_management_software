package access_roles;

public class CustomRoles extends Role {
    
    private boolean canCreateTask;
    private boolean adminAccess;

    public CustomRoles(String roleType, boolean canCreateTask, boolean adminAccess){
        super(roleType);
        this.canCreateTask = canCreateTask;
        this.adminAccess = adminAccess;
    }


    public boolean canCreateTask()
    {
        return this.canCreateTask;
    }

    public boolean adminAccess()
    {
        return this.adminAccess;
    }
    
}
