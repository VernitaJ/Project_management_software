package access_roles;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

public class CustomRoles extends Role {
    
    @JsonIncludeProperties
    private boolean canCreateTask;
    @JsonIncludeProperties
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
