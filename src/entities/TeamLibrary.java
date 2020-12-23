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

    public void viewTeam(Team team) {
        if (team == null) {
            System.out.println("Team does not exist.");
        } else {
            List<User> maintainers = team.getMaintainers();
            maintainers.sort(Comparator.comparing(User::getUserName));
            List<User> developers = team.getDevelopers();
            developers.sort(Comparator.comparing(User::getUserName));
            List<TeamMember> customMembers = team.getCustomMembers();
            customMembers.sort(Comparator.comparing(m->m.getRole().getType()));

            System.out.println("============");
            System.out.print(input.BLUE + "Owner: " + input.RESET + team.findOwner().getUser().getUserName());
            System.out.println(team.findOwner().getUser().achievementTracker.printTag());
            input.spacer();

            System.out.println(input.BLUE + "Maintainers: " + input.RESET);
            int count = 1;
            for (User maintainer: maintainers) {
                System.out.print(count + ". " + maintainer.getUserName());
                System.out.println(maintainer.achievementTracker.printTag());
                count ++;
            }
            input.spacer();

            count = 1;
            System.out.println(input.BLUE + "Developers: " + input.RESET);
            for (User developer: developers) {
                System.out.print(count + ". " + developer.getUserName());
                System.out.println(developer.achievementTracker.printTag());
                count ++;
            }
            input.spacer();

            count = 1;
            System.out.println(input.BLUE + "Custom Roles: " + input.RESET);
            for (TeamMember customMember: customMembers) {
                System.out.print(count + ". "+ customMember.getRole().getType() +" - " + customMember.getUser().getUserName());
                System.out.println(customMember.getUser().achievementTracker.printTag());
                count ++;
            }
            input.spacer();
        }
    }

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
                    System.out.println(count + ". (" + members.get(i).getRole().getType() + ")\t\t" + members.get(i).getUser().getUserName());
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
}
