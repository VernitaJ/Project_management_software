package Roles;

import components.Task;

public interface Roles
{
    Task createTask();
    boolean adminAccess();
}
