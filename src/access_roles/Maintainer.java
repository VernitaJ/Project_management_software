package access_roles;

public class Maintainer implements Roles {



    public boolean changeProjectStatus(){
        return true;
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
    public boolean adminAccess() {

        return true;
    }
}
