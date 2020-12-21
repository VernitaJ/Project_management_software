package access_roles;

public abstract class Role implements Roles {
    
    private String type;
    
    public Role(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public abstract Role getRole();
    
    public boolean canCreateTask() {
        return false;
    }
    
    public boolean adminAccess() {
        return false;
    }
}
