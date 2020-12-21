package access_roles;

public class Maintainer extends Role {
    
    public Maintainer() {
        super("Maintainer");
    }


    public boolean changeProjectStatus(){
        return true;
    }

    public boolean canCreateTask() {
        return true;
    }

    public boolean canDelete(){
        return false;
    }

    public boolean adminAccess() {
        return true;
    }
}
