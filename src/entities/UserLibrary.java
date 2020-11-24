package entities;

import tools.Input;

import java.util.ArrayList;
import java.util.List;

public class UserLibrary extends DataLibrary {
    Input input = Input.getInstance();

    private static UserLibrary instance = null;

    public static UserLibrary getInstance() {
        if (instance == null) {
            instance = new UserLibrary();
        }
        return instance;
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

    public List<User> getAllUsers() {
        List<Data> dataList = getDataList();
        List<User> users = new ArrayList<>();
        for(Data data: dataList) { users.add((User) data); }
        return users;
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
