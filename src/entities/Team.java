package entities;


import access_roles.CustomRoles;
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

    private WeakHashMap<String, TeamMember> memberList;

    public Team(User currentUser) throws Exception {
        if (teamName == null || teamName.isBlank() || teamName.equals(""))
        {
            throw new Exception(" Try again dum dum ");
        }
        this.teamName = input.getStr("Enter desired team name:");
        this.memberList = new WeakHashMap<>();
        this.memberList.put(getOwner().getUserName(),(TeamMember) currentUser);
        this.memberList.get(getOwner().getUserName()).setRole(roleFactory.createOwner());
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public User getOwner() {
        return this.memberList.get(getOwner().getUserName());
    }

    /* public void setOwner(User owner) {
        this.memberList.remove(ownerID);
        this.memberList.put(getOwner().getUserName(), owner);
        this.ownerID = owner.getID();
    }
     */
    public void addTeamDeveloper(User newDeveloper, User currentUser) {
        if (currentUser.getRole().adminAccess()){
            if (newDeveloper.equals(userLibrary.findUserInList(newDeveloper.getUserName()))) {
                this.memberList.put(newDeveloper.getUserName(),(TeamMember) newDeveloper);
                memberList.get(currentUser.getUserName()).setRole(roleFactory.createDeveloper());
        }
        } else {
            System.out.println("User does not exist or you do not have the correct access level");
        }
    }
    public void addTeamMaintainer(User newMaintainer, User currentUser) {
        if (currentUser.getRole().adminAccess())
        {
            if (newMaintainer.equals(userLibrary.findUserInList(newMaintainer.getUserName())))
            {
                this.memberList.put(currentUser.getUserName(), (TeamMember) currentUser);
                memberList.get(currentUser.getUserName()).setRole(roleFactory.createMaintainer());
            } else {
                System.out.println("You do not have the correct Access");
            }
        } else {
            System.out.println("You do not have the correct access");
        }
    }
    public List<User> getAllTeamMembers(){
        return new ArrayList<>(this.memberList.values());
    }

    public User getTeamMember(User user) throws Exception {
        if (memberList.containsValue(user)) {
            return this.memberList.get(user.getUserName());
        } else {
            throw new Exception("User does not exist");
        }
    }

    public void removeTeamMember(TeamMember userName) throws Exception {
        if (memberList.containsValue(userName)){
            this.memberList.remove(memberList.get(userName.getUserName()));
        }else{
            throw new Exception("User does not exist");
        }

    }
    public void addMemberWithCustomRole(User user)
    {
        if (hasAdminAccess(user)){
            String roleType = input.getStr("Enter role name: ");
            String canCreateTask = input.getStr("Can role create a task?: Y/N");
            String adminAccess = input.getStr("Does the role have admin access?: Y/N");
            boolean createTask = false;
            boolean hasAdminAccess = false;
            if (canCreateTask.equalsIgnoreCase("Y")){
                createTask = true;
            }
            if (adminAccess.equalsIgnoreCase("Y")){
                hasAdminAccess = true;
            }
            memberList.put(user.getUserName(), (TeamMember) user);
            memberList.get(user.getUserName()).setRole(new CustomRoles(roleType,createTask,hasAdminAccess));
        }else{
            System.out.println("You do not have the required access level.");
        }
    }
    private boolean hasAdminAccess(User currentUser){
        return currentUser.getRole().adminAccess();
    }
}

