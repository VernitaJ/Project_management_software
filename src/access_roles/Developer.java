package access_roles;

public class Developer extends Role {
    
    public Developer() {
        super("Developer");
    }
    
    @Override
    public Developer getRole() {
        return this;
    }
    
    public boolean canCreateTask() {
        return true;
    }

    public boolean adminAccess() {
        return false;
    }
}
