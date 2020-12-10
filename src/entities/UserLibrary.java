package entities;

import tools.Input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static entities.Message.sortByName;

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
        float salary = 0.0f;
        float workingHours = 0.0f;
        do {
            userName = this.input.getStr("Enter desired username: ");
            Data user = findUserInList(userName);
            if (user == null){
                do{
                    password = passwordValidate();
                } while (password == null);
                eMail = this.input.getStr("Enter your e-mail: ");
                occupation = this.input.getStr("Enter your current occupation: ");
                companyName = this.input.getStr("Enter your company name: ");
                salary = this.input.getFloat("Enter your Salary: "); //needs to be cleaned up for taking a float
                workingHours = this.input.getFloat("Enter your Daily Working Hours: "); // needs to be cleaned up for percentage
            } else System.out.println("This username is already in use, please try another.\n");
        } while (userName == null || eMail == null || occupation == null || companyName == null);
        list.add(new User(userName, password, eMail, occupation, companyName, salary, workingHours));
        System.out.println("Successfully created user.");
    }

    public void addUserToList(User userToAdd)
    {
        list.add(userToAdd);
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
            String userName = input.getStr("Username: ");
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
        System.out.println("Inbox \n _______________");
        ArrayList<Message> inbox = user.getInbox();
        Collections.sort(inbox, sortByName);
        if (inbox.size()>0){
            for (Message message: inbox){
                System.out.println(message.toString() + "\n");
                message.setStatus(true);
            }
        } else System.out.println("Inbox is Empty");
    }

    public void deleteMessage(User user){
        ArrayList<Message> inbox = user.getInbox();
        readMessage(user);
        String messageId = input.getStr("ID of message to delete: ");
        boolean itemExists = inbox.stream().map(Message::getID).anyMatch(messageId::equals);
        if (itemExists){
            inbox.removeIf(message -> (
                    message.getID().equals(messageId)));
            System.out.println("Message deleted");
            if (inbox.size()>0){
                String userChoice = input.getStr("Would you like to delete another? Y/N: ");
                if (userChoice.equalsIgnoreCase("y")){
                    deleteMessage(user);
                }
            }
        } else System.out.println("ID doesn't exist");
    }

    public void printAllUsers()
    {
        for (Data user : list)
        {
            if (user instanceof User)
            {
                System.out.println(((User) user).getUserName());
            }
        }
    }
}
