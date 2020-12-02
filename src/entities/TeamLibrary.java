package entities;

import tools.Input;
import access_roles.*;

import java.util.*;
import java.util.stream.Collectors;


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

    public void createTeam(Project project) {
        if (project.getTeam() != null) {
            System.out.println("Project '" + project.getName() + "' already has a team.");
        } else {
            Input input = Input.getInstance();
            String teamName = input.getStr("Enter new team name: ");
            try {
                if (teamName.isBlank() || teamName.isEmpty() || teamName == null)
                    throw new Exception("Invalid team name!");
                if (project.getProjectManager() == null)
                    throw new Exception("Invalid team owner user specified for team '" + teamName + "'.");

                Team newTeam = new Team(project.getProjectManager(), teamName);
                addToList(newTeam);
                project.setTeam(newTeam);
                System.out.println("Successfully created team '" + newTeam + "'.");
            } catch (Exception e) {
                System.out.println((e.getMessage()));
            }
        }
    }

    public void editTeamName(Team team) {
        if (team == null) {
            System.out.println("Team does not exist.");
        } else {
            Input input = Input.getInstance();
            String newTeamName = input.getStr("Please enter the new team name: ");
            if (newTeamName.isBlank() || newTeamName.isEmpty() || newTeamName == null) {
                // Need to implement the repeating input
                System.out.println("Invalid team name provided. Please retry again.");
            } else {
                team.setTeamName(newTeamName);
            }
        }
    }

    public void viewTeam(Team team) {
        if (team == null) {
            System.out.println("Team does not exist.");
        } else {
            System.out.println("============");
            System.out.println("Team name: " + team.getTeamName());
            System.out.println("Owner: " + team.getOwner().getUser().getUserName());
            System.out.println("Maintainer(s): ");
            int count = 1;
            List<User> maintainers = team.getMaintainers();
            maintainers.sort(Comparator.comparing(User::getUserName));
            List<User> developers = team.getDevelopers();
            developers.sort(Comparator.comparing(User::getUserName));

            for (User maintainer: maintainers) {
                System.out.println(count + ". " + maintainer.getUserName());
                count ++;
            }
            count = 1;
            System.out.println("Developer(s): ");
            for (User developer: developers) {
                System.out.println(count + ". " + developer.getUserName());
                count ++;
            }
        }
    }

    public void removeTeam(Project currentProject) {
        if (currentProject.getTeam() == null) {
            System.out.println("Project '" + currentProject.getName() + "' does not have a team.");
        } else {
            Input input = Input.getInstance();
            String option = input.getStr(
                    "Are you sure you want to remove team '" +
                    currentProject.getTeam().getTeamName() +
                    "' from project '" +
                    currentProject.getName() + "'? (Y/n)");
            option = option.toLowerCase();
            if (option.equals("y") || option.equals("yes") || option.equals("ye") || option.equals("yeah") || option.equals("yup")) {
                removeItFromList(currentProject.getTeam().getID());
                currentProject.setTeam(null);
                System.out.println("Team successfully removed from the project.");
            } else {
                System.out.println("Team was not removed.");
            }
        }
    }

    public void addTeamMaintainer(Team team, User currentUser) {
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
            int choice = Input.getInstance().getInt("Select a new team maintainer: ");
            if (choice >= 1 || choice < count) {
                try {
                    team.addTeamMaintainer(nonMemberUsers.get(choice - 1), currentUser);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void removeTeamMaintainer(Team team, User currentUser) {
        if (team == null) {
            System.out.println("Team does not exist");
        } else {
            List<User> maintainers = team.getMaintainers();
            if (maintainers.size() == 0) {
                System.out.println("Team '" + team.getTeamName() + "' has no maintainer team members.");
            } else {
                maintainers.sort(Comparator.comparing(User::getUserName));
                int count = 1;
                for (User maintainer: maintainers) {
                    System.out.println(count + ". " + maintainer.getUserName());
                    count ++;
                }
                Input input = Input.getInstance();
                int choice = input.getInt("Select the maintainer you want to remove: ");
                if (choice >= 1 || choice < count) {
                    try {
                        team.removeTeamMember(maintainers.get(choice - 1), currentUser);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    public void addTeamDeveloper(Team team, User currentUser) {
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
            int choice = Input.getInstance().getInt("Select a new team developer: ");
            if (choice >= 1 || choice < count) {
                try {
                    team.addTeamDeveloper(nonMemberUsers.get(choice - 1), currentUser);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void removeTeamDeveloper(Team team, User currentUser) {
        if (team == null) {
            System.out.println("Team does not exist");
        } else {
            List<User> developers = team.getDevelopers();
            if (developers.size() == 0) {
                System.out.println("Team '" + team.getTeamName() + "' has no developer team members.");
            } else {
                developers.sort(Comparator.comparing(User::getUserName));
                int count = 1;
                for (User developer: developers) {
                    System.out.println(count + ". " + developer.getUserName());
                    count ++;
                }
                Input input = Input.getInstance();
                int choice = input.getInt("Select the developer you want to remove: ");
                if (choice >= 1 || choice < count) {
                    try {
                        team.removeTeamMember(developers.get(choice - 1), currentUser);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
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
