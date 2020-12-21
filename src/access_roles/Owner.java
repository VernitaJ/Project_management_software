package access_roles;

public class Owner extends Role {

public Owner() {
    super("Owner");
}
    
    @Override
    public Owner getRole() {
        return this;
    }

    public boolean canRemoveMember(){

        return true;
    }

    public boolean canChangeProjectStatus(){
        return true;
    }

    public boolean assignRole(){
        return true;
    }

    public boolean canCreateTask() {
        return true;
    }

    public boolean canDelete(){
        return true;
    }

    public boolean adminAccess() {
        return true;
    }
}
