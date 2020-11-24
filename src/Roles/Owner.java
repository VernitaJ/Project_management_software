package Roles;

import components.Task;
import entities.User;

public class Owner implements Roles
{


    public User createTeam(){
        return null;
    }
    public void removeMember(){

    }
    public User assignMember(){
        return null;
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

    public boolean adminAccess() {
        return true;
    }
}
