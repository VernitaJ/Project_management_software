package access_roles;

public class Owner implements Roles
{

    public boolean canRemoveMember(){

        return true;
    }

    public boolean canChangeProjectStatus(){
        return true;
    }

    public boolean assignRole(){
        return true;
    }

    public String roleType() {
        return "Owner";
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
