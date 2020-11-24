package access_roles;

public class Developer implements Roles {




    public String roleType() {
      return "Developer";
    }

    public boolean canCreateTask() {
        return true;
    }

    public boolean adminAccess() {
        return false;
    }
}
