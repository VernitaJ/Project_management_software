package controller;

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

import static java.lang.Double.parseDouble;

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
                        projectLibrary.addProjectToList(new Project(token[1], currentUser, token[3], LocalDate.of(Integer.parseInt(token[4]), Integer.parseInt(token[5]), Integer.parseInt(token[6])), LocalDate.of(Integer.parseInt(token[7]), Integer.parseInt(token[8]), Integer.parseInt(token[9]))));
                        this.currentUser = null;
                    }
                    case "task" -> {
                        currentUser = (User) userLibrary.findUserInList(token[2]);
                        currentProject = projectLibrary.listUsersProjects(currentUser, false).get(0);
                        TaskLibrary currentTaskLibrary = currentProject.getTaskList();
                        currentTaskLibrary.addTaskToList(currentProject, currentUser, token[3], token[4], LocalDate.parse(token[5]), LocalDate.parse(token[6]));
                    }
                    case "workedhours" -> {
                        currentUser = (User) userLibrary.findUserInList(token[1]);
                        currentProject = projectLibrary.listUsersProjects(currentUser, false).get(0);
                        TaskLibrary currentTaskLibrary = currentProject.getTaskList();
                        Task currentTask = currentTaskLibrary.listProjectsTasks(currentProject, false).get(0);
                        currentTaskLibrary.addWorkedHoursToList(currentTask, currentUser, parseDouble(token[2]));
                    }
                    case "budget" -> {
                        currentUser = (User) userLibrary.findUserInList(token[1]);
                        currentProject = projectLibrary.listUsersProjects(currentUser, false).get(0);
                        projectLibrary.addBudgetToList(currentProject,parseDouble(token[2]),parseDouble(token[3]));
                    }
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
                case "3" -> {
                    if (systemAdminLogin())
                    {
                        sysAdminMenu();
                    }
                    else
                    {
                        loginMenu();
                    }
                }
                case "4" -> exit();
            }
        } while (true);
    }

    private void mainMenu() {
        String[] options =
                {
                        "Leaderboard",
                        "Projects",
                        "Profiles",
                        "Messaging",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Main Menu", options);
        do
        {
            currentUser.getXpBar();
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
                        "View all Projects",
                        "View all Users",
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
                case "3" -> viewAllProjects();
                case "4" -> viewAllUsers();
                case "5" -> loginMenu();
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
                        "Tasks Menu",
                        "Finance Menu",
                        "Team Menu",
                        "Gantt Chart",
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
                case "2" -> tasksMenu();
                case "3" -> financeMenu(currentProject, currentUser);
                case "4" -> teamMenu();
                case "5" -> projectLibrary.ganttChart(currentProject);
                case "6" -> projectLibrary.updateStatus(currentProject, currentUser);
                case "7" -> {
                    Boolean isSuccessful = projectLibrary.deleteProject(currentProject, currentUser);
                    if(isSuccessful){
                        mainMenu();
                    }
                }
                case "8" -> mainMenu();
                case "9" -> logout();
                case "10" -> exit();
            }
        } while (true);
    }

    private void financeMenu(Project currentProject, User currentUser) {
        String[] options =
                {
                        "View Project Budget",
                        "Add Budget",
                        "Change Budget",
                        "View Cost",
                        "View total hours by task",
                        "Hours to invoice",
                        "Expenses Forecast",
                        currentProject.getName() + " Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Project: '" + currentProject.getName() + "' Budget Menu", options);
        do {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> projectLibrary.viewBudget(currentProject, currentUser);
                case "2" -> addBudgetMenu(currentProject, currentUser);
                case "3" -> updateBudgetMenu(currentProject, currentUser);
                case "4" -> projectLibrary.viewCost(currentProject);
                case "5" -> taskLibrary.printDetailedWorkedHours(currentProject);
                case "6" -> taskLibrary.billableTask(currentProject);
                case "7" -> projectLibrary.getExpenseForecast(currentProject, currentUser);
                case "8" -> currentProjectMenu();
                case "9" -> logout();
                case "10" -> exit();
            }
        } while (true);
    }
    
    private void addBudgetMenu(Project currentProject, User currentUser) {
        String[] options =
                {
                        "Add Budget (SEK)",
                        "Add Budgeted Hours",
                        "Finance Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Project: '" + currentProject.getName() + "' Budget Menu", options);
        do {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> projectLibrary.addBudgetMoney(currentProject, currentUser);
                case "2" -> projectLibrary.addBudgetHours(currentProject, currentUser);
                case "3" -> financeMenu(currentProject, currentUser);
                case "4" -> logout();
                case "5" -> exit();
            }
        } while (true);
    }
    
    private void updateBudgetMenu(Project currentProject, User currentUser) {
        String[] options =
                {
                        "Update Budget (SEK)",
                        "Update Budgeted Hours",
                        "Finance Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu("Project: '" + currentProject.getName() + "' Budget Menu", options);
        do {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> projectLibrary.updateBudgetMoney(currentProject, currentUser);
                case "2" -> projectLibrary.updateBudgetHours(currentProject, currentUser);
                case "3" -> financeMenu(currentProject, currentUser);
                case "4" -> logout();
                case "5" -> exit();
            }
        } while (true);
    }
    
    private void tasksMenu() {
        String[] options =
                {
                        "Add Task",
                        "View Tasks",
                        "Remove Task",
                        "Completed Tasks",
                        "Countdown",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu(currentProject.getName() + ": Tasks" +  " Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> taskLibrary.createTask(currentProject, currentUser);
                case "2" -> {
                    currentTask = taskLibrary.navigateBetweenTasks(currentProject);
                    if (currentTask != null) {
                        currentTaskMenu(currentProject, currentTask, currentUser);
                    }
                } // taskMenu
                case "3" -> taskLibrary.deleteTask(currentProject, currentUser);
                case "4" -> taskLibrary.completedTasks(currentProject);
                case "5" -> taskLibrary.countdown(currentProject);
                case "6" -> mainMenu();
                case "7" -> logout();
                case "8" -> exit();
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
            menuName = "Team Menu";
        }
        String[] options =
                {
                        "View Team",
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
                case "1" -> teamLibrary.viewTeam(currentProject.getTeam());
                case "2" -> addMemberMenu();
                case "3" -> teamLibrary.removeTeamMember(currentProject.getTeam(), currentUser);
                case "4" -> changeMemberRoleMenu();
               // case "7" -> teamLibrary.removeTeam(currentProject, currentUser);
                case "5" -> currentProjectMenu();
                case "6" -> projectMenu();
                case "7" -> logout();
                case "8" -> exit();
            }
        } while (true);
    }
    private void addMemberMenu() {
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
                        "Add Worked Hours",
                        "Total Worked Hours",
                        "Project Menu",
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
                case "4" -> taskLibrary.updateName(currentProject, currentTask, currentUser);
                case "5" -> taskLibrary.updateDescription(currentProject, currentTask, currentUser);
                case "6" -> taskLibrary.updateStatus(currentProject, currentTask, currentUser);
                case "7" -> taskLibrary.addWorkedHours(currentProject, currentTask, currentUser);
                case "8" -> taskLibrary.printAllWorkedHours(currentTask);
                case "9" -> currentProjectMenu();
                case "10" -> mainMenu();
                case "11" -> logout();
                case "12" -> exit();
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
                        "View My Profile",
                        "Edit Profile",
                        "View All User Profiles",
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
                case "1" -> userLibrary.viewMyProfile(currentUser);
                case "2" -> editProfileMenu();
                case "3" -> userLibrary.viewAllProfiles();
                case "4" -> mainMenu();
                case "5" -> logout();
                case "6" -> exit();
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
                case "1" -> userLibrary.changeUsername(currentUser);
                case "2" -> userLibrary.changePassword(currentUser);
                case "3" -> userLibrary.updateEmail(currentUser);
                case "4" -> userLibrary.updateCompany(currentUser);
                case "5" -> userLibrary.updateOccupation(currentUser);
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
        menu = new Menu("Team Roles Menu", options);
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

    private boolean systemAdminLogin(){
        if (Login.getInstance().authorizeAdmin())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void viewAllProjects()
    {
        projectLibrary.printAllProjects();
    }
    private void viewAllUsers()
    {
        UserLibrary.getInstance().printAllUsers();
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

