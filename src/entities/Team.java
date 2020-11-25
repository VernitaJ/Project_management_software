package entities;


import access_roles.RoleFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import tools.*;
public class Team extends Data {

    private UserLibrary userLibrary;
    private Input input;
    private RoleFactory roleFactory = new RoleFactory();
    private String teamName;
    private String ownerID;
    private User owner;
    private WeakHashMap<String, User> memberList;

    public Team(String teamName, User owner) throws Exception {
        this(teamName, owner, new ArrayList<User>());
    }
    public Team(String teamName, User currentUser, List<User> teamMembers) throws Exception {
        if (teamName == null || teamName.isBlank() || teamName.equals(""))
            throw new Exception(" Try again dum dum ");

        for(User member: teamMembers) {
            this.memberList.put(member.getUserName(), member);
        }
        this.teamName = teamName;
        // Add regular team members
        this.memberList = new WeakHashMap<>();
        // Add owner into member list
        this.owner = currentUser;
        this.ownerID = currentUser.getID();
        this.owner.setRole(roleFactory.createOwner(currentUser));
        this.memberList.put(getOwner().getUserName(), currentUser);
       // this.ownerID = owner.getID();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    /*there is a problem with this one gonna create another*/
    /*key is username in the hash map but searching with ID in this method*/
    /*Also redundant bcs Team class stores owner as a user in a property*/
    public User getOwner() {
        return this.owner;
    }
    /*public User getOwner() {
        return this.memberList.get(ownerID);
    }*/

    /* public void setOwner(User owner) {
        this.memberList.remove(ownerID);
        this.memberList.put(getOwner().getUserName(), owner);
        this.ownerID = owner.getID();
    }
     */
    public void addTeamDeveloper(User newDeveloper, User currentUser) {
        if (currentUser.getRole().adminAccess()){
            if (newDeveloper.equals(userLibrary.findUserInList(newDeveloper.getUserName()))) {
                roleFactory.createDeveloper(newDeveloper);
                this.memberList.put(newDeveloper.getUserName(), newDeveloper);
        }
        } else {
            System.out.println("User does not exist or you do not have the correct access level");
        }
    }
    public void addTeamMaintainer(User newMaintainer, User currentUser) {
        if (currentUser.getRole().adminAccess()) {
            if (newMaintainer.equals(userLibrary.findUserInList(newMaintainer.getUserName()))) {
                roleFactory.createMaintainer(newMaintainer);
                this.memberList.put(newMaintainer.getUserName(), newMaintainer);
            } else {
                System.out.println("You do not have the correct Access");
            }
        } else {
            System.out.println("You do not have the correct access");
        }
    }
    public List<User> getAllTeamMembers(){
        List <User> teamMemberList = new ArrayList<>(this.memberList.values());
        return teamMemberList;
    }
    //WIP
    //changed the parameter is username. it was id before. -Numan
    public User getTeamMember(String username){
        User member = this.memberList.get(username);
        return member;
    }
    //WIP
    public void removeTeamMember(String userID) {
        this.memberList.remove(userID);
    }
}

