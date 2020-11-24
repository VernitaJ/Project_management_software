package entities;

import tools.Input;
import access_roles.*;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;


public class TeamLibrary extends DataLibrary{
    private static final TeamLibrary instance = null;
    private Team team;
    private RoleFactory roleFactory;
    private UserLibrary userLibrary;
    private WeakHashMap<String, User> memberList;


    public static TeamLibrary getInstance() {
        if (instance == null) {
            return new TeamLibrary();
        } else {
            return instance;
        }
    }


    public void createTeam(User currentUser){
        Input input = Input.getInstance();
        String teamName = null;
        User owner = currentUser;
        User firstTeamMember = null;
        boolean escape = false;
        do {
            teamName = input.getStr("Enter desired team name:");

            // What if you want to escape/go back?
        } while (teamName == null);

        if (!escape) {
            try {
                addToList(new Team(teamName, owner, selectUsers(owner)));
                System.out.println("Successfully created team.");

            } catch(Exception e) {

            }
        } else {
            System.out.println("Cancelled team creation.");
        }
    }

    // WIP WIP WIP WIP
    private List<User> selectUsers(User owner) {
        List<User> teamMembers = new ArrayList<User>();
        // TODO List all people minus the owner and allow the user to pick several team members
        List<User> users = UserLibrary.getInstance().getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (!users.get(i).getID().equals(owner.getID())) {
                System.out.println(i + "\t" + users.get(i).getUserName());

            }
        }
        return teamMembers;
    }


    public List<Team> getAllTeams() {
        List<Data> dataList = getDataList();
        List<Team> teams = new ArrayList<>();
        for(Data data: dataList) { teams.add((Team) data); }
        return teams;
    }

    // Delete Team? Or should just team members be removed?
    // Access level and ownership check is missing
    public boolean deleteTeam(String idToDelete){
        return removeItFromList(idToDelete);
    }

}
