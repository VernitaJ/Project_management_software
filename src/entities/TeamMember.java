package entities;

import access_roles.Roles;

import javax.management.relation.Role;

public class TeamMember {

   private User user;
   private Roles role;

   public TeamMember(User teamMember, Roles role){
       this.user = teamMember;
       this.role = role;
   }

    public User getUser() {
        return user;
    }

    public Roles getRole() {
        return role;
    }
}
