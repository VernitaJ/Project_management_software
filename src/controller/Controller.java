package controller;
import entities.UserLibrary;
import tools.Input;
import tools.Menu;
import entities.User;

public class Controller {
    private Input input = Input.getInstance();
    private Menu menu;
    private UserLibrary userLibrary = UserLibrary.getInstance();
    private static Controller instance = null;
    private User currentUser = null;

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
                case "1" -> createUser();
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
                        "Logout",
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
                case "7" -> logout();
                case "8" -> exit();
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

    private void login()
    {
        currentUser = userLibrary.login();
        if (currentUser != null)
        {
            mainMenu();
        }
    }
    private void logout()
    {
        currentUser = null;
        mainMenu();
    }

    private void createUser()
    {
        userLibrary.createUser();
    }

}

