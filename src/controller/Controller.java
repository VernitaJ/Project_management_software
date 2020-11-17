package controller;

import components.Login;
import tools.Input;
import tools.Menu;
import entities.User;

import java.util.ArrayList;

public class Controller {
    private Input input = Input.getInstance();
    private Menu menu;
    private Login login = Login.getInstance();
    private static Controller instance = null;

    private Controller(){}

    public static Controller getInstance()
    {
        if (instance == null)
        {
            return new Controller();
        }
        else
        {
            return instance;
        }
    }

    public void teardown()
    {
        instance = null;
    }

    public void run()
    {
        // TODO Change to Login menu once implemented
        loginMenu();
    }
    private void exit()
    {
        input.teardown();
        teardown();
        System.exit(0);
    }

    // method just to say that a menu item has not been implemented. (temporary)
    private void notImplemented()
    {
        System.out.println(Input.RED + "This Feature Has Not been Implemented" + Input.RESET);
    }

    private void loginMenu()
    {
        String[] options =
                {
                        "Create Account",
                        "Login",
                        "Exit"
                };
        menu = new Menu("Login Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> notImplemented();
                case "2" -> login();
                case "3" -> exit();
            }
        } while (true);
    }

    private void mainMenu()
    {
        // options will change but this is just so you can move around the system.
        String[] options =
                {
                        "Admin Menu",
                        "User Menu",
                        "Project Menu",
                        "Activity Menu",
                        "Team Resource Menu",
                        "Statistics Menu",
                        "Exit"
                };
        menu = new Menu("Main Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> adminMenu();
                case "2" -> userMenu();
                case "3" -> projectMenu();
                case "4" -> activityMenu();
                case "5" -> teamResourceMenu();
                case "6" -> statisticsMenu();
                case "7" -> exit();
            }
        } while (true);
    }

    private void adminMenu()
    {
        String[] options =
                {
                        "Import (test) Data",
                        "Export (test) Data",
                        "Remove User",
                        "Main Menu",
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
                case "5" -> exit();
            }
        } while (true);
    }

    private void userMenu()
    {
        String[] options =
                {
                        "View Projects",
                        "Create Project",
                        "Remove Project",
                        "Main Menu",
                        "Exit"
                };
        menu = new Menu("User Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> notImplemented();
                case "2" -> notImplemented();
                case "3" -> notImplemented();
                case "4" -> mainMenu();
                case "5" -> exit();
            }
        } while (true);
    }

    private void projectMenu()
    {
        String[] options =
                {
                        "Activities",
                        "Team Resources",
                        "Statistics",
                        "Main Menu",
                        "Exit"
                };
        menu = new Menu("Project Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> notImplemented();
                case "2" -> notImplemented();
                case "3" -> notImplemented();
                case "4" -> mainMenu();
                case "5" -> exit();
            }
        } while (true);
    }

    private void activityMenu()
    {
        String[] options =
                {
                        "Add",
                        "Remove",
                        "Change",
                        "Main Menu",
                        "Exit"
                };
        menu = new Menu("Activity Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> notImplemented();
                case "2" -> notImplemented();
                case "3" -> notImplemented();
                case "4" -> mainMenu();
                case "5" -> exit();
            }
        } while (true);
    }

    private void teamResourceMenu()
    {
        String[] options =
                {
                        "Add",
                        "Remove",
                        "Change",
                        "Main Menu",
                        "Exit"
                };
        menu = new Menu("Team Resource Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> notImplemented();
                case "2" -> notImplemented();
                case "3" -> notImplemented();
                case "4" -> mainMenu();
                case "5" -> exit();
            }
        } while (true);
    }
    private void statisticsMenu()
    {
        String[] options =
                {
                        "Project KPI",
                        "Create Project",
                        "Remove Project",
                        "Main Menu",
                        "Exit"
                };
        menu = new Menu("Statistics Menu", options);
        do
        {
            String choice = menu.printMenu();
            switch (choice)
            {
                case "1" -> notImplemented();
                case "2" -> notImplemented();
                case "3" -> notImplemented();
                case "4" -> mainMenu();
                case "5" -> exit();
            }
        } while (true);
    }

    private ArrayList<User> userList = new ArrayList<>();

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
                    mainMenu();
                } else System.out.println("Wrong username or password, please try again.");
            } else System.out.println("Wrong username or password, please try again.");
        } while (!loggedIn);
    }

    public User userProfile(String name) {
        for (User user : userList) {
            if (user.getUserName().equals(name)){
                return user;
            }
        }
        return null;
    }
}

