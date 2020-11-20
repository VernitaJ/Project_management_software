package entities;

// import date time for calculating Duration

import tools.Input;

import java.time.LocalDate;

public class Project extends Data{
    private String name;

    //needs to be integrated with team structure
    private String owner; // Change to User data type
    private String description;
    private String status; // Active/Inactive
    private LocalDate createdDate;

    //waiting for other userStories
    // private Team team;
    private TaskLibrary taskList;

    public Project(String name, String owner, String description) {
        this.name=name;
        this.owner=owner;
        this.description=description;
        this.createdDate = LocalDate.now();
    }

    private void setStatus(String status) {
        this.status = status;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }
    
    public int duration() {
        // startDate - endDate;
        return 1;
    }


    //access level check needs to be added
    public void updateStatus(){
        Input input = Input.getInstance();
        String newStatus = input.getStr("Enter the status: ");
        setStatus(newStatus);
    }


    //not mentioned in the user stories soooo idk
    /*
    //access level check needs to be added
    public void updateName(){
        Input input = Input.getInstance();
        String newStatus = input.getStr("Enter the description: ");
        setStatus(newStatus);
    }

    //access level check needs to be added
    public void updateDescription(){
        Input input = Input.getInstance();
        String newStatus = input.getStr("Enter the description: ");
        setStatus(newStatus);
    }
    */

}
