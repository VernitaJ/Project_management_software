package entities;

import access_roles.Role;

public class TeamMember {

   private User user;
   private Role role;

   public TeamMember(User teamMember, Role role){
       this.user = teamMember;
       this.role = role;
   }
   
   public TeamMember() {}

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return this.role.getRole();
    }
    
    public void setRole(Role newRole) {
        this.role = newRole;
    }
    
}
