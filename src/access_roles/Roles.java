package access_roles;

public interface Roles
{
    String getType();
    boolean canCreateTask();
    boolean adminAccess();
}
