package controller;

import components.Login;
import entities.*;
import tools.Input;
import tools.Menu;

import java.util.ArrayList;

public class Controller {
    private Input input = Input.getInstance();
    private Menu menu;
    private Login login = Login.getInstance();
    private UserLibrary userLibrary = UserLibrary.getInstance();
    private TeamLibrary teamLibrary = TeamLibrary.getInstance();
    private ProjectLibrary projectLibrary = ProjectLibrary.getInstance();
    private static Controller instance = null;
    private User currentUser = null;
    private Project currentProject = null;

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
        loginMenu();
    }
    private void exit()
    {
        input.teardown();
        teardown();
        System.exit(0);
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
        // options will change but this is just so you can move around the system.
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
        menu = new Menu("Admin Menu", options);
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
                case "1" -> notImplemented();
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
                        "Gantt Chart",
                        "View Team",
                        "Add Team Member",
                        "Remove Team Member",
                        "View Tasks",
                        "Add Task",
                        "Remove Task",
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
                case "1" -> notImplemented();
                case "2" -> notImplemented();
                case "3" -> notImplemented();
                case "4" -> notImplemented();
                case "5" -> notImplemented();
                case "6" -> notImplemented();
                case "7" -> notImplemented();
                case "8" -> notImplemented();
                case "9" -> notImplemented();
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
                        "logout",
                        "Exit"
                };
        menu = new Menu("Activity Menu", options);
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
        menu = new Menu("Team Resource Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> createTeam();
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

    public void login() {
        System.out.println("Heyyy. Welcome to the log in page");
        boolean loggedIn = false;
        do {
            String userName = input.getStr("UserName: ");
            String password = input.getStr("Password: ");

            User loggingIn = userProfile(userName);
            if (loggingIn !=null){
                if (loggingIn.getPassword().equals(password)){
                    System.out.println("\n" + "Welcome back " + loggingIn.getUserName() + "!");
                    loggedIn = true;
                    this.currentUser = loggingIn;
                    mainMenu();
                } else System.out.println("Wrong username or password, please try again.");
            } else System.out.println("Wrong username or password, please try again.");
        } while (!loggedIn);
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
    private void navigateBetweenProjects() {
        projectLibrary.navigateBetweenProjects(currentUser);
    }
    private void deleteProject(){

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
//    public User getCurrentUser() {
//        return this.currentUser;
//    }
    private void createTeam() {
        teamLibrary.createTeam(currentUser);
    }
}

