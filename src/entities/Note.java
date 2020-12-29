package entities;

import tools.Input;
import java.time.LocalDate;

public class Note extends Data {
    private User createdBy;
    private String message;
    private LocalDate date;
    private boolean dateHasBeenModified = false;
    private Input input = Input.getInstance();

    public Note(User currentUser, String message) {
       this.createdBy = currentUser;
       this.message = message;
       this.date = LocalDate.now();
    }

    public void editMessage(User currentUser, String newMessage) {
        if (currentUser.equals(createdBy)) {
            this.message = newMessage;
            date = LocalDate.now();
            dateHasBeenModified = true;
        } else {
            System.out.println("This is not your note, so you cannot edit it.");
        }
    }

    public User getUser() { return createdBy; }

    public String getUsername() { return createdBy.getUserName(); }

    public LocalDate getDate()
    {
        return date;
    }

    public String toString() {
        String state = (dateHasBeenModified) ? "Modified " : "Created ";
        return state + date + "\n" + message;
    }
}
