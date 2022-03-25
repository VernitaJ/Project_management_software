package entities;

import java.util.ArrayList;
import java.util.Collections;

public class messageHandler {
    private final UserLibrary userLibrary;

    public messageHandler(UserLibrary userLibrary) {
        this.userLibrary = userLibrary;
    }

    public void createMessage(User sender) {
        String senderUserName = sender.getUserName();
        String receiver = userLibrary.getInput().getStr("To: ");
        Data sendTo = userLibrary.findUserInList(receiver);
        if (sendTo != null) {
            User userToSendTo = (User) sendTo;
            String content = userLibrary.getInput().getStr("Message: ");
            String userConfirm = userLibrary.getInput().getStr("\n" + "Send: " + "\n " + content + "\n" + " To " + receiver + "? Y/N: ");
            if (userConfirm.equalsIgnoreCase("y")) {
                userToSendTo.getInbox().add(new Message(senderUserName, receiver, content));
                System.out.println("Message sent.");
                //achievement tracking
                sender.achievementTracker.addPoints("sendMessage", 1, sender);
            }
        } else System.out.println("That user doesn't exist.");
    }

    public void readMessage(User user) {
        System.out.println("Inbox \n _______________");
        ArrayList<Message> inbox = user.getInbox();
        Collections.sort(inbox, Message.sortByName);
        if (inbox.size() > 0) {
            for (Message message : inbox) {
                System.out.println(message.toString() + "\n");
                message.setRead(true);
            }
        } else System.out.println("Inbox is Empty");
    }
}