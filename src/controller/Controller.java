package controller;

import access_roles.CustomRoles;
import access_roles.Developer;
import access_roles.Maintainer;
import components.Login;
import entities.*;
import tools.Input;
import tools.Menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller {
    private Input input = Input.getInstance();
    private Menu menu;
    private Login login = Login.getInstance();
    private UserLibrary userLibrary = UserLibrary.getInstance();
    private TeamLibrary teamLibrary = TeamLibrary.getInstance();
    private ProjectLibrary projectLibrary = ProjectLibrary.getInstance();
    private TaskLibrary taskLibrary = TaskLibrary.getInstance();
    private static Controller instance = null;
    private User currentUser = null;
    private Project currentProject = null;
    private Task currentTask = null;
    private File testFile = new File("src/temptestdata.txt");

    private Controller(){}

    public static Controller getInstance()
    {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void teardown()
    {
        instance = null;
    }

    public void run()
    {
        readFile();
        loginMenu();
    }
    private void exit()
    {
        input.teardown();
        teardown();
        System.exit(0);
    }

    private void readFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        BufferedReader br;
        String line;
        try {
            br = new BufferedReader(new FileReader(testFile));
            while((line = br.readLine()) != null) {
                String[] token = line.split(",");
                switch (token[0].toLowerCase()) {
                    case "user" -> userLibrary.addUserToList(new User(token[1],token[2],token[3], token[4],token[5], Float.parseFloat(token[6]), Float.parseFloat(token[7])));
                    case "project" -> {
                        this.currentUser = (User) userLibrary.findUserInList(token[2]);
                        projectLibrary.addProjectToList(new Project(token[1], currentUser, token[3], LocalDate.of(Integer.parseInt(token[4]), Integer.parseInt(token[5]), Integer.parseInt(token[6])), LocalDate.of(Integer.parseInt(token[7]), Integer.parseInt(token[8]), Integer.parseInt(token[9])), Float.parseFloat(token[10])));
                        this.currentUser = null;
                    }
                    case "task" -> notImplemented();
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // method just to say that a menu item has not been implemented. (temporary)
    private void notImplemented() {
        System.out.println(Input.RED + "This Feature Has Not been Implemented" + Input.RESET);
    }

    private void loginMenu() {
        String[] options =
                {
                        "Create Account",
                        "Login",
                        "System Administrator Login",
                        "Exit"
                };
        menu = new Menu("Login Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> createUser();
                case "2" -> login();
                case "3" -> notImplemented();
                case "4" -> exit();
            }
        } while (true);
    }

    private void mainMenu() {
        String[] options =
                {
                        "Leaderboard",
                        "Projects",
                        "Profile",
                        "Messaging",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Main Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> leaderboardMenu();
                case "2" -> projectMenu();
                case "3" -> profileMenu();
                case "4" -> messageMenu();
                case "5" -> logout();
                case "6" -> exit();
            }
        } while (true);
    }

    private void sysAdminMenu() {
        String[] options =
                {
                        "Import (test) Data",
                        "Export (test) Data",
                        "Remove User",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("System Admin Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> notImplemented();
                case "2" -> notImplemented();
                case "3" -> notImplemented();
                case "4" -> mainMenu();
                case "5" -> logout();
                case "6" -> exit();
            }
        } while (true);
    }

    private void projectMenu() {
        String[] options =
                {
                        "View Projects",
                        "Create Project",
                        "Remove Project",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Project Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> {
                    currentProject = projectLibrary.navigateBetweenProjects(currentUser);
                        if(currentProject != null) {
                            currentProjectMenu();
                        }
                }
                case "2" -> createProject();
                case "3" -> notImplemented();
                case "4" -> mainMenu();
                case "5" -> logout();
                case "6" -> exit();
            }
        } while (true);
    }
    
    private void currentProjectMenu() {
        String[] options =
                {
                        "View Project Details",
                        "Countdown",
                        "Completed Tasks",
                        "Gantt Chart",
                        "Team Menu",
                        "View Tasks",
                        "Add Task",
                        "Update Status",
                        "Delete Project",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Project: '" + currentProject.getName() + "' Menu", options);
        do
            {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> projectLibrary.viewProjectDetails(currentProject);
                case "2" -> taskLibrary.countdown(currentProject);
                case "3" -> taskLibrary.completedTasks(currentProject);
                case "4" -> notImplemented();
                case "5" -> teamMenu();
                case "6" -> {
                    currentTask = taskLibrary.navigateBetweenTasks(currentProject);
                    if (currentTask != null) {
                        currentTaskMenu(currentProject, currentTask, currentUser);
                    }
                } // taskMenu
                case "7" -> taskLibrary.createTask(currentProject, currentUser);
                case "8" -> projectLibrary.updateStatus(currentProject, currentUser);
                case "9" -> {
                    Boolean isSuccessful = projectLibrary.deleteProject(currentProject, currentUser);
                    if(isSuccessful){
                        mainMenu();
                    }
                 }
                case "10" -> mainMenu();
                case "11" -> logout();
                case "12" -> exit();
            }
        } while (true);
    }

    private void teamMenu() {
        String menuName;
        if (currentProject.getTeam() == null)
        {
            menuName = "Team Menu";
        }
        else
        {
            menuName = currentProject.getTeam().getTeamName() + " Menu";
        }
        String[] options =
                {
                        "Create Team",
                        "View Team",
                        "Edit Team Name",
                        "Add Team Member",
                        "Remove Team Member",
                        "Change Team Member Role",
                        "Remove Team",
                        currentProject.getName() + " Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu(menuName, options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> teamLibrary.createTeam(currentProject);
                case "2" -> teamLibrary.viewTeam(currentProject.getTeam());
                case "3" -> teamLibrary.editTeamName(currentProject.getTeam());
                case "4" -> addMember();
                case "5" -> teamLibrary.removeTeamMember(currentProject.getTeam(), currentUser);
                case "6" -> changeMemberRoleMenu();
                case "7" -> teamLibrary.removeTeam(currentProject, currentUser);
                case "8" -> currentProjectMenu();
                case "9" -> logout();
                case "10" -> exit();
            }
        } while (true);
    }
    private void addMember() {
        String[] options =
                {
                        "Maintainer",
                        "Developer",
                        "Custom Role",
                        "Team Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Add Member", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> teamLibrary.addTeamMember(currentProject.getTeam(), currentUser, "Maintainer");
                case "2" -> teamLibrary.addTeamMember(currentProject.getTeam(), currentUser, "Developer");
                case "3" -> teamLibrary.addTeamMember(currentProject.getTeam(), currentUser, "Custom");
                case "4" -> teamMenu();
                case "5" -> logout();
                case "6" -> exit();
            }
        } while (true);
    }
    
    private void currentTaskMenu(Project currentProject, Task currentTask, User currentUser) {
        String[] options =
                {
                        "View Task Details",
                        "Add Team Member",
                        "Remove Team Member",
                        "Update Name",
                        "Update Description",
                        "Update Status",
                        "Delete Task",
                        "Time Management Menu",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu(currentTask.getName() + " Task", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> taskLibrary.viewTaskDetails(currentTask);
                case "2" -> taskLibrary.addAssignee(currentProject, currentTask, currentUser);
                case "3" -> taskLibrary.removeAssignee(currentProject.getTeam(), currentTask.getAssignees(), currentUser);
                case "4" -> notImplemented();
                case "5" -> notImplemented();
                case "6" -> taskLibrary.updateStatus(currentProject, currentTask, currentUser);
                case "7" -> taskLibrary.deleteTask(currentProject, currentUser);
                case "8" -> taskTimeManagementMenu(currentProject, currentTask, currentUser);
                case "9" -> mainMenu();
                case "10" -> logout();
                case "11" -> exit();
            }
        } while (true);
    }

    private void taskTimeManagementMenu(Project currentProject, Task currentTask, User currentUser) {
        String[] options =
                {
                        "Add Worked Hours",
                        "Task Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu(currentTask.getName() + " Task", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {

                case "1" -> taskLibrary.addWorkedHours(currentProject, currentTask, currentUser);
                case "2" -> currentTaskMenu(currentProject, currentTask, currentUser);
                case "3" -> logout();
                case "¤" -> exit();
            }
        } while (true);
    }

    private void messageMenu() {
        String[] options =
                {
                        "Create Message",
                        "Read Messages",
                        "Delete Message",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu(currentUser.getUserName() + " Messaging", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> createMessage();
                case "2" -> readMessage();
                case "3" -> deleteMessage();
                case "4" -> mainMenu();
                case "5" -> logout();
                case "6" -> exit();
            }
        } while (true);
    }

    private void profileMenu() {
        String[] options =
                {
                        "View Profile",
                        "Edit Profile",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Profile Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> notImplemented();
                case "2" -> editProfileMenu();
                case "3" -> mainMenu();
                case "4" -> logout();
                case "5" -> exit();
            }
        } while (true);
    }

    private void editProfileMenu() {
        String[] options =
                {
                        "Change Username",
                        "Change Password",
                        "Update Email",
                        "Update Company",
                        "Update Occupation",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Edit Profile", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> notImplemented();
                case "2" -> notImplemented();
                case "3" -> notImplemented();
                case "4" -> notImplemented();
                case "5" -> notImplemented();
                case "6" -> mainMenu();
                case "7" -> logout();
                case "8" -> exit();
            }
        } while (true);
    }

    private void leaderboardMenu() {
        String[] options =
                {
                        "View Leaderboard",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Leaderboard", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> notImplemented();
                case "2" -> mainMenu();
                case "3" -> logout();
                case "4" -> exit();
            }
        } while (true);
    }
    private void changeMemberRoleMenu() {
        TeamMember userToChange = currentProject.getTeam().roleChange(currentUser);
        String[] options =
                {
                        "Maintainer",
                        "Developer",
                        "Custom Role",
                       currentProject.getName() + " Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu(currentProject.getTeam().getTeamName() + " Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> userToChange.setRole(new Maintainer());
                case "2" -> userToChange.setRole(new Developer());
                case "3" -> userToChange.setRole(currentProject.getTeam().addMemberWithCustomRole(currentUser));
                case "4" -> currentProjectMenu();
                case "5" -> logout();
                case "6" -> exit();
            }
        } while (true);
    }

    private void login() {
       currentUser = (userLibrary.login());
       if (currentUser !=null)
       {
           mainMenu();
       }
    }

    public void logout(){
        currentUser = null;
        loginMenu();
    }

    public User userProfile(String name) {
        for (User user : userLibrary.getAllUsers()) {
            if (user.getUserName().equals(name)){
                return user;
            }
        }
        return null;
    }

    private void createUser() {
        userLibrary.createUser();
    }

    private void createProject() {
        projectLibrary.createProject(currentUser);
    }

    
    private void createMessage() {
        userLibrary.createMessage(currentUser);
    }
    
    private void readMessage() {
        userLibrary.readMessage(currentUser);
    }
    
    private void deleteMessage() {
        userLibrary.deleteMessage(currentUser);
    }

    
}

