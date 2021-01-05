package entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import tools.UniqueID;

import java.time.LocalDate;
import java.util.Comparator;

@JsonDeserialize(as = Message.class)
public class Message extends Data {
    private String sender;
    private String receiver;
    private String content;
    private LocalDate dateSent;
    private boolean read;

    public Message(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.dateSent = LocalDate.now();
        this.read = false;
    }

    public Message() {

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDateSent() {
        return dateSent;
    }

    public void setDateSent(LocalDate dateSent) {
        this.dateSent = dateSent;
    }

    public boolean getRead(){
        return(this.read);
    }

    public String prettyRead() {
        if (read) {
            return "\033[32mRead\033[0m";
        } else return "\033[34mUnread\033[0m";
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public static Comparator<Message> sortByName = new Comparator<Message>() {

        @Override
        public int compare(Message o1, Message o2) {
            return o2.getDateSent().compareTo(o1.getDateSent());
        }
    };

    public String toString() {
        return prettyRead() + "\n" + getID() + "\n" + "From: " + getSender() + "\n" + "Sent: " + getDateSent() + "\n" + getContent();
    }
}