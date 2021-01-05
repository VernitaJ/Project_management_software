package access_roles;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

public class CustomRoles extends Role {


    public CustomRoles(String roleType, boolean canCreateTask, boolean adminAccess){
        super(roleType, canCreateTask, adminAccess);
    }

    public CustomRoles(String roleType){
        super(roleType);
    }
    
}
