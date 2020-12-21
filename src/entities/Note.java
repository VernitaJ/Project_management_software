package entities;

import tools.Input;

import java.time.LocalDate;

public class Note
{
    private String username;
    private String message;
    private LocalDate date;
    private boolean dateHasBeenModified = false;


    public Note(User currentUser, String message)
    {
       this.username = currentUser.getUserName();
       this.message = message;
       this.date = LocalDate.now();
    }

    public void editMessage(User currentUser, String newMessage)
    {
        if (currentUser.equals(username))
        {
            this.message = Input.getInstance().getStr(newMessage);
            date = LocalDate.now();
            dateHasBeenModified = true;
        }
        else
        {
            System.out.println("This is not your note");
        }
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getDate() {
        return date;
    }

    public String toString()
    {
        String str;
        if (dateHasBeenModified == false){
            str = "Created " + date + "\n" + message;
        }
        else
        {
            str =  "Modified " + date + "\n" + message;
        }
        return str;
    }
}
