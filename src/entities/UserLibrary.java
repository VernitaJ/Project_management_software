package entities;

import tools.Input;

public class UserLibrary extends DataLibrary {
    Input input = Input.getInstance();
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
            do{
                String user = this.input.getStr("Enter desired username:");
                if (findUserInList(user) == null){
                    userName = user;
                } else System.out.println("That username already exists. Try again. \n");
            } while (userName == null);
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
        if (password.isBlank()) {
            password = null;
            System.out.println("Your password can't be empty. Please try again.");
        } else if (password.length() < 5){
            password = null;
            System.out.println("Your password needs to be more than 4 characters long. Please try again.");
        } else if (!password.equals(this.input.getStr("Verify password:"))) {
            password = null;
            System.out.println("Passwords doesn't match. Please try again.");
        }
        return(password);
    }

    public User login() {
        System.out.println("Welcome to Simple Direction. " + "\n" + "Please Log in to continue.");
        String userLogin = "";
        do {
            String userName = input.getStr("UserName: ");
            String password = input.getStr("Password: ");
            Data loggingIn = findUserInList(userName);
            if (loggingIn !=null){
                User user = (User) loggingIn;
                if (user.getPassword().equals(password)){
                    System.out.println("\n" + "Welcome back " + user.getUserName() + "!");
                    return user;
                } else userLogin = input.getStr("Wrong username or password, try again? Y/N: ");
            } else userLogin = input.getStr("Wrong username or password, try again? Y/N: ");
        } while (!userLogin.equalsIgnoreCase("n"));
        return null;
    }

    public Data findUserInList(String userName) {
        for (Data user : list) {
                if (((User) user).getUserName().equals(userName)){
                    return user;
                }
        }
        return null;
    }
}
