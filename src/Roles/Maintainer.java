package Roles;

import components.Task;
import entities.User;

public class Maintainer implements Roles {



    public User assignProjectMember(){
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


    public boolean setAdminAccess() {

        return true;
    }
}
