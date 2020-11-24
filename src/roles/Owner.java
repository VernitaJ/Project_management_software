package roles;

import entities.User;

public class Owner implements Roles
{


    public User createTeam(){
        return null;
    }

    public User assignMember(){
        return null;
    }
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

    public boolean setAdminAccess() {

        return true;
    }
}
