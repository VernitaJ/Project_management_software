package Roles;

import components.Task;

public class Developer implements Roles {


    public Task createTask() {
        return null;
    }

    public boolean adminAccess() {
        return false;
    }
}
