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
        list.add(new User(userName, password, eMail, occupation, companyName));
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
        for (Data user : this.list) {
                if (((User) user).getUserName().equals(userName)){
                    return user;
                }
        }
        return null;
    }

    public void createMessage(User sender){
        String senderUserName = sender.getUserName();
        String receiver = input.getStr("To: ");
        Data sendTo = findUserInList(receiver);
        if (sendTo != null) {
            User userToSendTo = (User) sendTo;
            String content = input.getStr("Message: ");
            String userConfirm = input.getStr("\n" + "Send: " + "\n " + content  + "\n" + " To " + receiver + "? Y/N: " );
            if (userConfirm.equalsIgnoreCase("y")){
                userToSendTo.getInbox().add(new Message(senderUserName,receiver,content));
                System.out.println("Message sent.");
            }
        } else System.out.println("That user doesn't exist.");
    }

    public void readMessage(User user){
        ArrayList<Message> inbox = user.getInbox();
        if (inbox.size()>0){
            System.out.println("Messages:" + "\n");
            for (Message message: inbox){
                System.out.println(message.toString() + "\n");
                message.setStatus(true);
            }
        } else System.out.println("You have no messages");
    }

    public void deleteMessage(User user){
        readMessage(user);
        String messageId = input.getStr("ID of message to delete: ");
        user.getInbox().removeIf(message -> (message.getID().equals(messageId)));
    }
}
