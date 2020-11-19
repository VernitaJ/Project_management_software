package Roles;
import entities.User;
import entities.DataLibrary;

//Logic to be added once Projects are implemented
public class RoleFactory extends DataLibrary {
    private User user;
    public RoleFactory(){
    }


    public Roles createOwner(boolean bool){

        return new Owner();
    }
    public Roles createMaintainer(boolean bool){
        return new Maintainer();
    }
    public Roles createDeveloper(boolean bool){
        return new Developer();
    }
}
