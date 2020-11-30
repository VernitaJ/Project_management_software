package entities;


import access_roles.CustomRoles;
import access_roles.RoleFactory;
import tools.Input;

import java.util.WeakHashMap;
public class Team extends Data {

    private UserLibrary userLibrary;
    private Input input;
    private RoleFactory roleFactory = new RoleFactory();
    private String teamName;

    private WeakHashMap<String, TeamMember> memberList;
    //deleted the name check
    public Team(User currentUser){
        //this.teamName = input.getStr("Enter desired team name:");
        this.memberList = new WeakHashMap<>();
        this.memberList.put(currentUser.getUserName(), new TeamMember(currentUser, roleFactory.createOwner()));
    }
    
    public Team() {
        this.memberList = new WeakHashMap<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public User getOwner() {
        User owner = null;
        for (TeamMember user : memberList.values()){
            if (user.getRole().roleType().equalsIgnoreCase("Owner")){
                owner = user.getUser();
                return owner;
            }
        }
        return owner;
    }
    /* public void setOwner(User owner) {
        this.memberList.remove(ownerID);
        this.memberList.put(getOwner().getUserName(), owner);
        this.ownerID = owner.getID();
    }
     */
    public TeamMember findTeamMember(User toFind){
        return this.memberList.get(toFind.getUserName());
    }
    public void addTeamDeveloper(User newDeveloper, User currentUser) {
        TeamMember currentMember = findTeamMember(currentUser);
        if (currentMember != null && currentMember.getRole().adminAccess()){
            if (newDeveloper.getUserName().equals(userLibrary.findUserInList(newDeveloper.getUserName()))) {
                this.memberList.put(newDeveloper.getUserName(),new TeamMember(newDeveloper, roleFactory.createDeveloper()));
        }
        } else {
            System.out.println("User does not exist or you do not have the correct access level");
        }
    }
    public void addTeamMaintainer(User newMaintainer, User currentUser) {
        TeamMember currentMember = findTeamMember(currentUser);
        if (currentMember != null && currentMember.getRole().adminAccess()){
            if (newMaintainer.getUserName().equals(userLibrary.findUserInList(newMaintainer.getUserName())))
            {
                this.memberList.put(newMaintainer.getUserName(), new TeamMember(newMaintainer, roleFactory.createOwner()));
            } else {
                System.out.println("You do not have the correct Access");
            }
        } else {
            System.out.println("You do not have the correct access");
        }
    }
   /*  public List<TeamMember> getAllTeamMembers(){
        return new ArrayList<>(this.memberList.values());
    }

    */


    public User getTeamMember(User user) throws Exception {
        if (memberList.containsKey(user.getUserName())) {
            return this.memberList.get(user.getUserName()).getUser();
        } else {
            throw new Exception("User does not exist");
        }
    }

    /* public void removeTeamMember() throws Exception {
        if (memberList.containsValue(userName)){
            this.memberList.remove(memberList.get(userName.getUserName()));
        }else{
            throw new Exception("User does not exist");
        }
    }
     */
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
            memberList.put(user.getUserName(), new TeamMember(user, new CustomRoles(roleType,createTask,hasAdminAccess)));
        }else{
            System.out.println("You do not have the required access level.");
        }
    }
    private boolean hasAdminAccess(User currentUser) {
        TeamMember currentMember = findTeamMember(currentUser);
        if (currentMember != null && currentMember.getRole().adminAccess()) {

            return true;
        }
        return false;
    }

//to be changed when getTeamMember function is implemented
    private String toString(Team team){
        return team.getOwner().toString();
    }
}

