package roles;

public interface Roles
{
    String roleType();
    boolean canCreateTask();
    boolean setAdminAccess();
}
