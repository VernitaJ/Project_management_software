package access_roles;

public class RoleFactory {
    
    public Role createOwner()
    {
        return new Owner();
    }

    public Role createMaintainer()
    {
        return new Maintainer();
    }

    public Role createDeveloper()
    {
        return new Developer();
    }
}
