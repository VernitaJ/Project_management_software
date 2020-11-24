package roles;
import components.Task;

public class Developer implements Roles {




    public String roleType() {
      return "Developer";
    }

    public boolean canCreateTask() {
        return true;
    }

    public boolean setAdminAccess() {
        return false;
    }
}
