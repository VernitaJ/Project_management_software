package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class Team extends Data {
    private String teamName;
    private String ownerID;
    private WeakHashMap<String, User> memberList;

    public Team(String teamName, User owner) throws Exception {
        this(teamName, owner, new ArrayList<User>());
    }
    public Team(String teamName, User owner, List<User> teamMembers) throws Exception {
        if (teamName == null || teamName.isBlank() || teamName.equals(""))
            throw new Exception();

        this.teamName = teamName;
        // Add regular team members
        this.memberList = new WeakHashMap<>();

        for(User member: teamMembers) {
            this.memberList.put(member.getID(), member);
        }
        // Add owner into member list
        this.memberList.put(owner.getID(), owner);
        this.ownerID = owner.getID();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public User getOwner() {
        return this.memberList.get(ownerID);
    }

    public void setOwner(User owner) {
        this.memberList.remove(ownerID);
        this.memberList.put(owner.getID(), owner);
        this.ownerID = owner.getID();
    }

    public User getTeamMember(String userID){
        User member = this.memberList.get(userID);
        return member;
    }

    // Team members excluding owner
    public List<User> getAllTeamMembers(){
        List <User> teamMemberList = new ArrayList<>(this.memberList.values());
        return teamMemberList;
    }

    public void addTeamMember(User teamMember) {
        this.memberList.put(teamMember.getID(), teamMember);
    }

    public void removeTeamMember(String userID) {
        this.memberList.remove(userID);
    }
}

