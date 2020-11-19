package Roles;

import components.Task;
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
    public boolean changeProjectStatus(){
        return false;
    }

    public void assignRole(){
    }
    public Task createTask() {
        return null;
    }

    public void deleteTask(){
    }

    public boolean setAdminAccess() {
         boolean access = true;
        return true;
    }
}
