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
            System.out.println("Incorrect password, please try again.");
        }

        return(password);
    }

}
