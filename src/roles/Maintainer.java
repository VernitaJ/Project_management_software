package roles;

import components.Task;
import entities.User;

public class Maintainer implements Roles {



    public User assignProjectMember(){
        return null;
    }

    public boolean changeProjectStatus(){
        return true;
    }

    public void assignRole(){
    }
    public Task createTask() {
        return null;
    }


    public String roleType() {
        return "Maintainer";
    }

    public boolean canCreateTask() {
        return true;
    }

    public boolean canDelete(){
        return false;
    }
    //maintainer shouldn't be able to delete, think about
    public boolean setAdminAccess() {

        return true;
    }
}
