package access_roles;

public interface Roles
{
    String roleType();
    boolean canCreateTask();
    boolean adminAccess();
}
