package controller;

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

    private void readFile()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        BufferedReader br;
        String line;
        try {
            br = new BufferedReader(new FileReader(testFile));
            while((line = br.readLine()) != null) {
                String[] token = line.split(",");
                switch (token[0].toLowerCase()) {
                    case "user" -> userLibrary.addUserToList(new User(token[1],token[2],token[3], token[4],token[5]));
                    case "project" -> {
                        this.currentUser = (User) userLibrary.findUserInList(token[2]);
                        projectLibrary.addProjectToList(new Project(token[1], currentUser, token[3], LocalDate.of(Integer.parseInt(token[4]), Integer.parseInt(token[5]), Integer.parseInt(token[6])), LocalDate.of(Integer.parseInt(token[7]), Integer.parseInt(token[8]), Integer.parseInt(token[9]))));
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
                        "Inbox",
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
                case "61" -> exit();
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
                    Project a = projectLibrary.navigateBetweenProjects(currentUser);
                        if(a != null) {
                            currentProjectMenu(a);
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
    
    private void currentProjectMenu(Project currentProject) {
        String[] options =
                {
                        "View Project Details",
                        "Countdown",
                        "Gantt Chart",
                        "View Team",
                        "Add Team Member",
                        "Remove Team Member",
                        "View Tasks",
                        "Add Task",
                        "Remove Task",
                        "Update Status",
                        "Delete Project",
                        "Main Menu",
                        "Logout",
                        "Exit"
                };
        menu = new Menu(currentProject.getName() + " Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> projectLibrary.viewProjectDetails(currentProject);
                case "2" -> notImplemented();
                case "3" -> notImplemented();
                case "4" -> notImplemented();
                case "5" -> notImplemented();
                case "6" -> notImplemented();
                case "7" -> {
                    Task currentTask = taskLibrary.navigateBetweenTasks(currentProject);
                    if (currentTask != null) {
                        currentTaskMenu(currentProject, currentTask, currentUser);
                    }
                } // taskMenu
                case "8" -> taskLibrary.createTask(currentProject, currentUser);
                case "9" -> taskLibrary.deleteTask(currentProject, currentUser);
                case "10" -> projectLibrary.updateStatus(currentProject, currentUser);
                case "11" -> {
                //    Boolean isSuccessful = projectLibrary.deleteProject(currentProject, currentUser);
                //    if(isSuccessful){
                //        mainMenu();
                //    }
                 }
                case "12" -> mainMenu();
                case "13" -> logout();
                case "14" -> exit();
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
                case "2" -> taskLibrary.addAssignee(currentProject, currentTask);
                case "3" -> taskLibrary.removeAssignee(currentProject, currentTask);
                case "4" -> notImplemented();
                case "5" -> notImplemented();
                case "6" -> taskLibrary.updateStatus(currentProject, currentTask, currentUser);
                case "7" -> mainMenu();
                case "8" -> logout();
                case "9" -> exit();
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
        menu = new Menu(currentUser.getUserName() + " Inbox", options);
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

