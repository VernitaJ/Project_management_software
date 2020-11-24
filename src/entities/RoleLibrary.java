package entities;


import roles.*;

import java.util.HashMap;
import controller.*;

public class RoleLibrary {
private User user;
private Roles roles;
private String type;

private final HashMap<String, Roles> userRoles = new HashMap<>();


public Roles checkRole(String userName){
    userRoles.get(userName);
    return roles;
}


public void addRole(User currentUser){
   // for ()
   // if (currentUser.equals(){

    }
    //for ()

   /*if (user.getUserName().equalsIgnoreCase(userRoles.get("Owner", Owner))) {

    */




    //    userName and owner add
    //} else { userName and createRole(Developer);

   // }


    public Roles upgradeRole(String userName){

        return roles;
    }
    public void setUserRoles(Roles type) {
        this.roles = type;
        }


    public Roles getRoles(){
    return this.roles;
   // user.setUserRoles(new Developer());

    //sammaanvändare.assignRole(new Maintainer());
    }
}
