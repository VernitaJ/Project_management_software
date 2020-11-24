package roles;

public class RoleFactory {
    public RoleFactory(){
    }

    public Roles createOwner(boolean bool)
    {
        return new Owner();
    }

    public Roles createMaintainer(boolean bool)
    {
        return new Maintainer();
    }

    public Roles createDeveloper(boolean bool)
    {
        return new Developer();
    }
}
