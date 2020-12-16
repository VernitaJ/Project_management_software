package entities;

import access_roles.RoleFactory;
import tools.Input;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.WeakHashMap;


public class TeamLibrary extends DataLibrary{
    private static final TeamLibrary instance = null;
    private Team team;
    private RoleFactory roleFactory;
    private UserLibrary userLibrary;
    private WeakHashMap<String, User> memberList;
    Input input = Input.getInstance();

    public static TeamLibrary getInstance() {
        if (instance == null) {
            return new TeamLibrary();
        } else {
            return instance;
        }
    }

   /*
   public void createTeam(Project project) {
        if (project.getTeam() != null) {
            System.out.println("Project '" + project.getName() + "' already has a team.");
        } else {
            String option = input.getStr("Are you sure you want to create a team for project '"
                    + project.getName() + "'?: Y/N ");
            option = option.toLowerCase();
            if (option.equals("y") || option.equals("yes")) {
                String teamName = input.getStr("Enter new team name: ");
                try {
                    if (teamName.isBlank() || teamName.isEmpty() || teamName == null)
                        throw new Exception("Invalid team name!");
                    if (project.getProjectManager() == null)
                        throw new Exception("Invalid team owner user specified for team '" + teamName + "'.");

                    Team newTeam = new Team(project.getProjectManager());
                    addToList(newTeam);
                    project.setTeam(newTeam);
                    System.out.println("Successfully created team '" + teamName + "'.");
                        } catch (Exception e) {
                        System.out.println((e.getMessage()));
                }
            }
        }
    }


    public void editTeamName(Team team) {
        if (team == null) {
            System.out.println("Team does not exist.");
        } else {
            String newTeamName = input.getStr("Please enter the new team name: ");
            if (newTeamName.isBlank() || newTeamName.isEmpty() || newTeamName == null) {
                System.out.println("Invalid team name provided. Please retry again.");
            } else {
                team.setTeamName(newTeamName);
                System.out.println("Successfully renamed team to '" + newTeamName + "'!");
            }
        }
    }
*/

    public void viewTeam(Team team) {
        if (team == null) {
            System.out.println("Team does not exist.");
        } else {
            List<User> maintainers = team.getMaintainers();
            maintainers.sort(Comparator.comparing(User::getUserName));
            List<User> developers = team.getDevelopers();
            developers.sort(Comparator.comparing(User::getUserName));
            List<TeamMember> customMembers = team.getCustomMembers();
            customMembers.sort(Comparator.comparing(m->m.getRole().roleType()));

            System.out.println("============");
            System.out.print(input.BLUE + "Owner: " + input.RESET + team.getOwner().getUser().getUserName());
            System.out.println(team.getOwner().getUser().getTag());
            input.spacer();

            System.out.println(input.BLUE + "Maintainers: " + input.RESET);
            int count = 1;
            for (User maintainer: maintainers) {
                System.out.print(count + ". " + maintainer.getUserName());
                System.out.println(maintainer.getTag());
                count ++;
            }
            input.spacer();

            count = 1;
            System.out.println(input.BLUE + "Developers: " + input.RESET);
            for (User developer: developers) {
                System.out.print(count + ". " + developer.getUserName());
                System.out.println(developer.getTag());
                count ++;
            }
            input.spacer();

            count = 1;
            System.out.println(input.BLUE + "Custom Roles: " + input.RESET);
            for (TeamMember customMember: customMembers) {
                System.out.print(count + ". "+ customMember.getRole().roleType() +" - " + customMember.getUser().getUserName());
                System.out.println(customMember.getUser().getTag());
                count ++;
            }
            input.spacer();
        }
    }

    /*
    public void removeTeam(Project currentProject, User currentUser) {
        if (currentProject.getProjectManager().checkID(currentUser.getID())) {
            if (currentProject.getTeam() == null) {
                System.out.println("Project '" + currentProject.getName() + "' does not have a team.");
            } else {
                String option = input.getStr(
                        "Are you sure you want to remove team '" +
                                currentProject.getTeam().getTeamName() +
                                "' from project '" +
                                currentProject.getName() + "'?: Y/N ");
                option = option.toLowerCase();
                if (option.equals("y") || option.equals("yes") || option.equals("ye") || option.equals("yeah") || option.equals("yup")) {
                    removeItFromList(currentProject.getTeam().getID());
                    currentProject.setTeam(null);
                    System.out.println("Team successfully removed from the project.");
                } else {
                    System.out.println("Team was not removed.");
                }
            }
        } else {
            System.out.println("Only the team owner can remove the team.");
        }
    }
*/
    public void addTeamMember(Team team, User currentUser, String role) {
        if (team == null) {
            System.out.println("Team does not exist.");
        } else {
            List<User> nonMemberUsers = new ArrayList<>();
            int count = 1;
            System.out.println("Available users: ");
            List<User> allUsers = UserLibrary.getInstance().getAllUsers();
            allUsers.sort(Comparator.comparing(User::getUserName));

            for (User user: allUsers) {
                if (!team.isMember(user)) {
                    nonMemberUsers.add(user);
                    System.out.println(count + ". " + user.getUserName());
                    count++;
                }
            }
            int choice = Input.getInstance().getInt("Select a new team member to be given role (" + role + "): ");
            if (choice >= 1 || choice < count) {
                try {
                    team.addMember(nonMemberUsers.get(choice - 1), currentUser, role);
                } catch (Exception e) {
                    System.out.println("Your input does not match the available choices. Please try again.");
                }
            }
        }
    }

    public void removeTeamMember(Team team, User currentUser) {
        if (team == null) {
            System.out.println("Team does not exist");
        } else {
            List<TeamMember> members = team.getAllTeamMembers();
            if (members.size() == 0) {
                System.out.println("This project has no removable team members.");
            } else {
                members.sort(Comparator.comparing(member -> member.getUser().getUserName()));
                int count = 1;

                for (int i = 0; i < members.size(); i++) {
                    System.out.println(count + ". (" + members.get(i).getRole().roleType() + ")\t\t" + members.get(i).getUser().getUserName());
                    count ++;
                }

                int choice = input.getInt("Select a team member you want to remove: ");
                if (choice >= 1 || choice < count) {
                    try {
                        team.removeTeamMember(members.get(choice - 1).getUser(), currentUser);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    // For sysadmin maybe?
    /*
    public List<Team> getAllTeams() {
        List<Data> dataList = getDataList();
        List<Team> teams = new ArrayList<>();
        for(Data data: dataList) { teams.add((Team) data); }
        return teams;
    }
     */
}
