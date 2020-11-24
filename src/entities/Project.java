package entities;

import tools.Input;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Project extends Data{
    private String name;

    //needs to be integrated with team structure
    private String description;
    private String status; // Active/Inactive
    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate endDate;
    //waiting for other userStories
    // private Team team;
    private TaskLibrary taskList;

    public Project(String name, User owner, String description, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        //teams
        //add owner to the team
        this.description = description;
        this.createdDate = LocalDate.now();
        this.taskList = new TaskLibrary();
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    private void setStatus(String status) {
        this.status = status;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
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
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public long duration() {
        long daysBetween = ChronoUnit.DAYS.between(getStartDate(),getEndDate());
        return daysBetween;
    }


    //user & access level check needs to be added
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
        String newDesc = input.getStr("Enter the description: ");
        setName(newDesc);
    }

    //access level check needs to be added
    public void updateDescription(){
        Input input = Input.getInstance();
        String newName = input.getStr("Enter the name: ");
        setDescription(newName);
    }
    */

}
