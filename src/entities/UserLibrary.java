package entities;

import tools.Input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

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
                //achievement tracking
                sender.achievementTracker.addPoints("sendMessage",1, sender);
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
                message.setRead(true);
            }
        } else System.out.println("Inbox is Empty");
    }

    public void viewMyProfile(User user){
        System.out.println("Username: " + user.getUserName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Occupation: " + user.getOccupation());
        System.out.println("Company Name: " + user.getCompanyName());
        user.achievementTracker.printXpBar();
        if(user.achievementTracker.listUserAchievements().size() > 0){
            System.out.println("Achievements:" );
            user.achievementTracker.printUserAchievementsWithDetails();
        }

    }


    public void changeUsername(User user) {
        System.out.println("Your current username is: " + user.getUserName());
        String option = input.getStr("Are you sure you want to change it?: Y/N ");
        option = option.toLowerCase();
        if (option.equals("y") || option.equals("yes")) {
            String newUser = input.getStr("Enter your new username: ");
            if (findUserInList(newUser) == null) {
                user.setUserName(newUser);
                System.out.println("Your username has been successfully changed to: " + user.getUserName());
            } else {
                System.out.println("Desired username is already in use. Please try again!");
            }
        } else {
            System.out.println("Your username has not been changed.");
        }
    }

    public void changePassword(User user) {
        System.out.println("You are attempting to change your password.");
        String option = input.getStr("Are you sure you want to change it?: Y/N ");
        option = option.toLowerCase();
        if (option.equals("y") || option.equals("yes")) {
            String password = input.getStr("To proceed, enter your current password: ");
            if (password.equals(user.getPassword())) {
                do {
                    password = passwordValidate();
                } while (password == null);
                user.setPassword(password);
                System.out.println("Your password has been successfully changed!");
            } else {
                System.out.println("Your input does not match your registered password. Please try again!");
            }
        } else {
            System.out.println("Your password has not been changed.");
        }
    }

    public void updateEmail(User user){
        System.out.println("Your current email is: " + user.getEmail());
        String option = input.getStr("Are you sure you want to change it?: Y/N ");
        option = option.toLowerCase();
        if (option.equals("y") || option.equals("yes")) {
            String newEmail = input.getStr("Enter your new email: ");
            user.setEmail(newEmail);
            System.out.println("Your email has been successfully changed to: " + user.getEmail());
        } else {
            System.out.println("Your email has not been changed.");
        }
    }

    public void updateCompany(User user){
        System.out.println("Your current listed place of employ is at company: " + user.getCompanyName());
        String option = input.getStr("Are you sure you want to update it?: Y/N ");
        option = option.toLowerCase();
        if (option.equals("y") || option.equals("yes")) {
            String newCompany = input.getStr("Enter the new name of the company for which you work: ");
            user.setCompanyName(newCompany);
            System.out.println("The company's name has been successfully updated to: " + user.getCompanyName());
        } else {
            System.out.println("Your listed company's name has not been updated.");
        }
    }

    public void updateOccupation(User user){
        System.out.println("Your current occupation is: " + user.getOccupation());
        String option = input.getStr("Are you sure you want to update it?: Y/N ");
        option = option.toLowerCase();
        if (option.equals("y") || option.equals("yes")) {
            String newOccupation = input.getStr("Enter your new occupation: ");
            user.setOccupation(newOccupation);
            System.out.println("Your occupation has been successfully updated to: " + user.getOccupation());
        } else {
            System.out.println("Your listed occupation has not been updated.");
        }
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


    public void viewAllProfiles (){
        int count = 1;
        System.out.println("All users: ");
        List<User> allUsers = UserLibrary.getInstance().getAllUsers();
        allUsers.sort(Comparator.comparing(User::getUserName));

        for (User user: allUsers) {
            System.out.println(count + ". " + user.getUserName());
            count++;
        }
        int choice = Input.getInstance().getInt("Select the user whose profile you wish to view: ");
        if (choice >= 1 || choice < count) {
            try {
                viewMyProfile(allUsers.get(choice - 1));
            } catch (Exception e) {
                System.out.println("Your input does not match the available choices. Please try again.");
            }
        }

    }

    //if user1 is greater than user 2 -> return true;
    public boolean compareTwoUserAchievements(User user1, User user2){
        if(user1.achievementTracker.printNumOfUserAchievements() > user2.achievementTracker.printNumOfUserAchievements()){
            return false;
        } else if(user1.achievementTracker.printNumOfUserAchievements() < user2.achievementTracker.printNumOfUserAchievements()){
            return true;
        } else {
            //check the tiers
            if(user1.achievementTracker.getTotalTiers() > user2.achievementTracker.getTotalTiers()){
                return false;
            } else if (user1.achievementTracker.getTotalTiers() < user2.achievementTracker.getTotalTiers()){
                return true;
            } else{
                //tie breaker
                if(user1.getUserName().compareToIgnoreCase(user2.getUserName()) > 0){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
    }

    public List<User> sortUsersByAchievements(){
        List<User> allUsers = UserLibrary.getInstance().getAllUsers();
        allUsers.sort(Comparator.comparing(User::getUserName));
        Collections.reverse(allUsers);

        for (int i = 0; i < allUsers.size(); i++){
            for (int j = 0; j < allUsers.size()-i-1; j++){
                if (compareTwoUserAchievements(allUsers.get(j), allUsers.get(j+1)))
                {   // swap arr[j+1] and arr[j]
                    Collections.swap(allUsers,j,j+1);
                }
            }

        }

        return allUsers;
    }

    public void leaderboard(){
        List<User> allUsers = sortUsersByAchievements();
        System.out.println("--------------------"+ Input.BLUE + "LEADERBOARD" + Input.RESET +"--------------------");
        for(User user : allUsers){
            if(user.achievementTracker.printNumOfUserAchievements() > 0 ){
                System.out.println(user.getUserName() + " - Total Achievements: " + user.achievementTracker.printNumOfUserAchievements());
                user.achievementTracker.printUserAchievements();
            }
        }
    }

    public List<User> searchByUsername(String userName){
        List<User> searchResult = new ArrayList<>();
        User user = (User)findUserInList(userName);
        if(user!=null){
            searchResult.add(user);
        }
         return searchResult;
    }
    public List<User> searchByCompany(String company){
        List<User> searchResult = new ArrayList<>();
        for(Data user : list){
            User currentUser = (User)user;
            if(currentUser.getCompanyName().equalsIgnoreCase(company)){
                searchResult.add(currentUser);
            }
        }
        return searchResult;
    }
    public List<User> searchByOccupation(String occupation){
        List<User> searchResult = new ArrayList<>();
        for(Data user : list){
            User currentUser = (User)user;
            if(currentUser.getOccupation().equalsIgnoreCase(occupation)){
                searchResult.add(currentUser);
            }
        }
        return searchResult;
    }

    public void printSearchResults(Function<String, List<User>> searchMethod){
        String dataToSearch = this.input.getStr("Enter search term: ");
        List<User> searchResult = searchMethod.apply(dataToSearch);
        searchResult.sort(Comparator.comparing(User::getUserName));
        if(searchResult.size()==0){
            System.out.println("No result found!");
        } else{
            System.out.println("----------------------------");
            for(User user : searchResult){
                System.out.println("Name: " + user.getUserName());
                System.out.println("Occupation: " + user.getOccupation());
                System.out.println("Company: " + user.getCompanyName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("----------------------------");
            }
        }

    }

}
