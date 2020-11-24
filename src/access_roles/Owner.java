package access_roles;

public class Owner implements Roles
{

    public void removeMember(){
    }

    public boolean canChangeProjectStatus(){
        return true;
    }

    public void assignRole(){

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
