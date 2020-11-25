package access_roles;

import entities.User;

public class RoleFactory {
    public RoleFactory(){
    }

    public Roles createOwner()
    {
        return new Owner();
    }

    public Roles createMaintainer()
    {
        return new Maintainer();
    }

    public Roles createDeveloper()
    {
        return new Developer();
    }
}
