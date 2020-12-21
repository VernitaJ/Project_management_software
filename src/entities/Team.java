package entities;


import access_roles.CustomRoles;
import access_roles.Owner;
import access_roles.RoleFactory;
import tools.Input;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
public class Team extends Data {
    
    private Input input = Input.getInstance();
    private RoleFactory roleFactory = new RoleFactory();
    private String ownerUserName;

    private WeakHashMap<String, TeamMember> memberList;

    /*
        Create a team with 1 team member: the team's owner.
     */
    public Team(User teamOwner){
        this.memberList = new WeakHashMap<String, TeamMember>();
        this.ownerUserName = teamOwner.getUserName();
        this.memberList.put(teamOwner.getUserName(), new TeamMember(teamOwner, roleFactory.createOwner()));
    }
    
    public Team() {
        this.memberList = new WeakHashMap<>();
    }
    
    public TeamMember findOwner() {
        return memberList.get(ownerUserName);
    }
    
    // If owner needs to be set (owner wants to remove self?)
    /* public void setOwner(User owner) {
        this.memberList.remove(ownerID);
        this.memberList.put(findOwner().getUserName(), owner);
        this.ownerID = owner.getID();
    }
     */
    
    public TeamMember findTeamMember(User toFind){
        return this.memberList.get(toFind.getUserName());
    }
    
    public CustomRoles addMemberWithCustomRole(User user)
    {
        if (hasAdminAccess(user)){
            String roleType = input.getStr("Enter role name: ");
            String canCreateTask = input.getStr("Can the " + roleType + " role create a task? Y/N: ");
            String adminAccess = input.getStr("Does the " + roleType + " role have admin access? Y/N: ");
            boolean createTask = false;
            boolean hasAdminAccess = false;
            if (canCreateTask.equalsIgnoreCase("Y")){
                createTask = true;
            }
            if (adminAccess.equalsIgnoreCase("Y")){
                hasAdminAccess = true;
            }
            return new CustomRoles(roleType,createTask,hasAdminAccess);
        }else{
            System.out.println("You do not have the required access level.");
            return null;
        }
    }
    
    public boolean isMember(User user) {
        return memberList.containsKey(user.getUserName());
    }
    
    public void addMember(User newMember, User currentUser, String role) {
        TeamMember currentMember = findTeamMember(currentUser);
        if (currentMember != null && currentMember.getRole().adminAccess()){
            if (UserLibrary.getInstance().doesItExist(newMember.getID())) {
                if (role.equals("Developer")) {
                    this.memberList.put(newMember.getUserName(), new TeamMember(newMember, roleFactory.createDeveloper()));
                    System.out.println("'" + newMember.getUserName() + "' successfully added as a " + role + ".");
                } else if (role.equals("Maintainer")) {
                    this.memberList.put(newMember.getUserName(), new TeamMember(newMember, roleFactory.createMaintainer()));
                    System.out.println("'" + newMember.getUserName() + "' successfully added as a " + role + ".");
                } else if (role.equals("Custom")) {
                    Input input = Input.getInstance();
                    String roleType = input.getStr("Enter role name: ");
                    String canCreateTask = input.getStr("Can the role create a task?: Y/N ");
                    String adminAccess = input.getStr("Does the role have admin access?: Y/N ");
                    boolean createTask = false;
                    boolean hasAdminAccess = false;
                    if (canCreateTask.equalsIgnoreCase("Y")){
                        createTask = true;
                    }
                    if (adminAccess.equalsIgnoreCase("Y")){
                        hasAdminAccess = true;
                    }
                    memberList.put(newMember.getUserName(), new TeamMember(newMember, new CustomRoles(roleType,createTask,hasAdminAccess)));
                } else {
                    System.out.println("Invalid role provided.");
                }
            } else {
                System.out.println("Invalid user provided for the team.");
            }
        } else {
            System.out.println("You do not have the required access level.");
        }
    }
    
    public List<TeamMember> getAllTeamMembers(){
        List<TeamMember> teamMembers = new ArrayList<>();
        memberList.values().forEach( teamMember -> {
            teamMembers.add(teamMember);
        });
        return teamMembers;
    }
    
    public List<User> getAllTeamUsers(){
        List<User> teamUsers = new ArrayList<>();
        memberList.values().forEach( teamMember -> {
            teamUsers.add(teamMember.getUser());
        });
        return teamUsers;
    }
    
    public User getTeamMember(User user) throws Exception {
        if (memberList.containsKey(user.getUserName())) {
            return this.memberList.get(user.getUserName()).getUser();
        } else {
            throw new Exception(user.getUserName() + " is not part of the team.");
        }
    }
    
    public void removeTeamMember(User teamMember, User teamOwnerUser) throws Exception {
        TeamMember currentMember = findTeamMember(teamOwnerUser);
        // The target team member cannot be null
        if (teamMember == null)
            throw new Exception("Cannot remove a 'null' member from the team.");
        // Team leader cannot be null
        if (currentMember == null)
            throw new Exception(teamOwnerUser.getUserName() + " is not part of the team.");
        // Only team leader can remove others from the team
        if (teamOwnerUser.getUserName().equals(findOwner().getUser().getUserName()) == false)
            throw new Exception(teamOwnerUser.getUserName() + " is not the team leader of the team.");
        // Team leader should not be able to remove self from the team
        if (teamOwnerUser.getUserName().equals(teamMember.getUserName()))
            throw new Exception("The team leader " + teamOwnerUser.getUserName() + " cannot remove themself from the team.");
        
        if (memberList.containsKey(teamMember.getUserName())){
            this.memberList.remove(teamMember.getUserName());
        } else {
            throw new Exception("Cannot remove " + teamMember.getUserName() + ". The user is not part of this team.");
        }
    }
    
    public List<User> getMaintainers() {
        List<User> users = new ArrayList<>();
        for (TeamMember member: memberList.values()) {
            if (member.getRole().getType().equals("Maintainer"))
                users.add(member.getUser());
        }
        return users;
    }
    
    public List<User> getDevelopers() {
        List<User> users = new ArrayList<>();
        for (TeamMember member: memberList.values()) {
            if (member.getRole().getType().equals("Developer"))
                users.add(member.getUser());
        }
        return users;
    }
    
    public List<TeamMember> getCustomMembers() {
        List<TeamMember> users = new ArrayList<>();
        for (TeamMember member: memberList.values()) {
            if (!member.getRole().getType().equals("Owner") &&
                    !member.getRole().getType().equals("Developer") &&
                    !member.getRole().getType().equals("Maintainer"))
                users.add(member);
        }
        return users;
    }
    
    
    private boolean hasAdminAccess(User currentUser) {
        TeamMember currentMember = findTeamMember(currentUser);
        if (currentMember != null && currentMember.getRole().adminAccess()) {

            return true;
        }
        return false;
    }
    
    public TeamMember roleChange(User currentUser) {
        TeamMember currentMember = findTeamMember(currentUser);
        if (currentMember != null && currentMember.getRole().adminAccess()) {
            for (String member : memberList.keySet()) {
                System.out.println(memberList.get(member).getUser().getUserName());
            }
            String memberToChange = input.getStr("Team Member whose role you would like to modify: ");
            try {
                if (memberList.containsKey(memberToChange) &&
                        !(memberList.get(memberToChange).getRole() instanceof Owner)
                ) {
                    return memberList.get(memberToChange);
                } else {
                    System.out.println("User selected has an access level that denies modification.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    
    public WeakHashMap<String, TeamMember> getMemberList() {
        return memberList;
    }
}

