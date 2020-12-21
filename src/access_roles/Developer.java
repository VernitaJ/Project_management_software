package access_roles;

public class Developer extends Role {
    
    public Developer() {
        super("Developer");
    }

    
    public boolean canCreateTask() {
        return true;
    }

    public boolean adminAccess() {
        return false;
    }
}
