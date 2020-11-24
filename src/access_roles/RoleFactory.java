package access_roles;

import entities.User;

public class RoleFactory {
    public RoleFactory(){
    }

    public Roles createOwner(User currentUser)
    {
        return new Owner();
    }

    public Roles createMaintainer(User user)
    {
        return new Maintainer();
    }

    public Roles createDeveloper(User user)
    {
        return new Developer();
    }
}
