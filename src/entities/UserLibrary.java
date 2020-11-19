package entities;

import tools.Input;

import java.util.ArrayList;

public class UserLibrary extends DataLibrary {
    Input input = Input.getInstance();
    private ArrayList<User> userList = new ArrayList<>();
    private static final UserLibrary instance = null;

    public static UserLibrary getInstance() {
        if (instance == null) {
            return new UserLibrary();
        } else {
            return instance;
        }
    }

    public void createUser() {
        String userName = null;
        String password = null;
        String eMail = null;
        String occupation = null;
        String companyName = null;
        do {
            userName = this.input.getStr("Enter desired username:");
            do{
                password = passwordValidate();
            } while (password == null);
            eMail = this.input.getStr("Enter your e-mail:");
            occupation = this.input.getStr("Enter your current occupation:");
            companyName = this.input.getStr("Enter your company name:");
        } while (userName == null || eMail == null || occupation == null || companyName == null);
        super.addToList(new User(userName, password, eMail, occupation, companyName));
        System.out.println("Successfully created user.");
    }

    private String passwordValidate() {
        String password = this.input.getStr("Enter desired password:");

        if(!password.equals(this.input.getStr("Verify password:"))) {
            password = null;
            System.out.println("Passwords don't match. Please try again.");
        }

        return(password);
    }

    public User login() {
        System.out.println("Welcome to Simple Direction. " + "\n" + "Please Log in to continue.");
        boolean loggedIn = false;
        do {
            String userName = input.getStr("UserName: ");
            String password = input.getStr("Password: ");
            Data loggingIn = findItInList(userName);
            if (loggingIn !=null){
                User user = (User) loggingIn;
                if (user.getPassword().equals(password)){
                    System.out.println("\n" + "Welcome back " + user.getUserName() + "!");
                    return user;
                } else System.out.println("Wrong username or password, please try again.");
            } else System.out.println("Wrong username or password, please try again.");
        } while (!loggedIn);
        return null;
    }

    public Data findItInList(String userName) {
        for (Data user : list) {
                if (((User) user).getUserName().equals(userName)){
                    return user;
                }
        }
        return null;
    }
}
