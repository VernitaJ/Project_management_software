package access_roles;

public class Role implements Roles {
    
    private String type;
    
    public Role(String type) {
        this.type = type;
    }

    public Role() {
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    
    public boolean canCreateTask() {
        return false;
    }
    
    public boolean adminAccess() {
        return false;
    }
}
