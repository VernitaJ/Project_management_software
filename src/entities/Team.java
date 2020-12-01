package entities;


import access_roles.CustomRoles;
import access_roles.Developer;
import access_roles.Maintainer;
import access_roles.RoleFactory;
import tools.Input;

import java.util.WeakHashMap;
public class Team extends Data {

    private UserLibrary userLibrary = UserLibrary.getInstance();
    private Input input = Input.getInstance();
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
    public TeamMember findTeamMemberByString(String toFind){
        return this.memberList.get(toFind);
    }

    public void addTeamDeveloper(User newTeamMember, User currentUser) {
        TeamMember currentMember = findTeamMember(currentUser);
        if (currentMember != null && currentMember.getRole().adminAccess()){
            if (memberList.containsKey(newTeamMember.getUserName())){
                System.out.println("You already have this User on your team.");
            } else {
                if (userLibrary.findUserInList(newTeamMember.getUserName()) != null) {
                    if (isOwner(newTeamMember)){
                        System.out.println("You're not allowed to change the role of owner");

                    } else {
                        this.memberList.put(newTeamMember.getUserName(), new TeamMember(newTeamMember, roleFactory.createDeveloper()));
                    }
                } else {
                    System.out.println("You do not have the correct Access");
                }
            }
        } else {
            System.out.println("You do not have the correct access");
        }
    }
    public void addTeamMaintainer(User newTeamMember, User currentUser) {
        TeamMember currentMember = findTeamMember(currentUser);
        if (currentMember != null && currentMember.getRole().adminAccess()){
            if (memberList.containsKey(newTeamMember.getUserName())){
                System.out.println("You already have this User on your team.");
            } else {
                if (userLibrary.findUserInList(newTeamMember.getUserName()) != null) {
                    if (isOwner(newTeamMember)) {
                        System.out.println("You're not allowed to change the role of owner");

                    } else {
                        this.memberList.put(newTeamMember.getUserName(), new TeamMember(newTeamMember, roleFactory.createMaintainer()));
                    }
                } else {
                    System.out.println("You do not have the correct Access");
                }
            }
        } else {
            System.out.println("You do not have the correct access or the user you are trying to add are already an owner.");
        }
    }

    public WeakHashMap<String, TeamMember> getMemberList()
    {
        return memberList;
    }


    public User getTeamMember(User user) throws Exception {
        if (memberList.containsKey(user.getUserName())) {
            return this.memberList.get(user.getUserName()).getUser();
        } else {
            throw new Exception("User does not exist");
        }
    }

    public void addTeamMemberWithCustomRole(User newTeamMember, User currentUser) {
        TeamMember currentMember = findTeamMember(currentUser);
        if (currentMember != null && currentMember.getRole().adminAccess()){
            if (memberList.containsKey(newTeamMember.getUserName())){
                System.out.println("You already have this User on your team.");
            } else {
                if (userLibrary.findUserInList(newTeamMember.getUserName()) != null) {
                    if (isOwner(newTeamMember)) {
                        System.out.println("You're not allowed to change the role of owner");

                    } else {
                        String roleType = input.getStr("Enter role name: ");
                        String canCreateTask = input.getStr("Can role create a task? Y/N: ");
                        String adminAccess = input.getStr("Does the role have admin access? Y/N: ");
                        boolean createTask = false;
                        boolean hasAdminAccess = false;
                        if (canCreateTask.equalsIgnoreCase("Y")){
                            createTask = true;
                        }
                        if (adminAccess.equalsIgnoreCase("Y")){
                            hasAdminAccess = true;
                        }
                        memberList.put(newTeamMember.getUserName(), new TeamMember(newTeamMember, new CustomRoles(roleType,createTask,hasAdminAccess)));
                    }
                } else {
                    System.out.println("You do not have the correct Access");
                }
            }
        } else {
            System.out.println("You do not have the correct access or the user you are trying to add are already an owner.");
        }
    }
    public void changeToMaintainer (User currentUser)
    {
        for (String team : memberList.keySet()){
            System.out.println(team);
        }
        String userToChange = input.getStr("Team member whose role you want to update: ");

        memberList.get(userToChange).setRole(new Maintainer());
    }
    public void changeToDeveloper (User currentUser)
    {
        for (String team : memberList.keySet()){
            System.out.println(team);
        }
        String userToChange = input.getStr("Team member whose role you want to update: ");

        memberList.get(userToChange).setRole(new Developer());
    }


    public void changeToCustomerRole (User currentUser)
    {

    }


    private boolean isAdmin(User currentUser)
    {
        System.out.println(currentUser.getUserName());
        return memberList.get(currentUser.getUserName()).getRole().adminAccess();
    }
    private boolean hasAdminAccess(User currentUser) {
        TeamMember currentMember = findTeamMember(currentUser);
        if (currentMember.getRole().adminAccess()) {
            return true;
        } else {
            return false;
        }
    }
    private boolean isOwner (User currentUser) {
        TeamMember currentMember = findTeamMember(currentUser);
        if (currentMember != null && currentMember.getRoleType().equalsIgnoreCase("Owner")){
            return true;
        } else {
            return false;
        }
    }
}

