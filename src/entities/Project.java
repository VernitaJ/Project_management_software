package entities;
import tools.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

public class Project extends Data{
    private Team team;
    private String name;
    private String description;
    private String status; // Active/Inactive
    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private TaskLibrary taskList;
    public Project(String name, User owner, String description, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.description = description;
        this.createdDate = LocalDate.now();
        this.taskList = new TaskLibrary();
        try{
            // maybe we can add one more constructor to the Team class which does now expect a List<User>
            this.team = new Team("default-team", owner, Collections.emptyList());
        }   catch (Exception e){
            e.printStackTrace();
        }
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



    public void updateStatus(User currentUser){
        if(currentUser.getRole().adminAccess()){
            Input input = Input.getInstance();
            String newStatus = input.getStr("Enter the status: ");
            setStatus(newStatus);
        }
        else{
            System.out.println("You are not authorized to perform this action!");
        }

    }



    //not mentioned in the user stories soooo idk
    /*
    //access level check needs to be added
    public void updateName(){
        if(currentUser.getRole().adminAccess()){
            Input input = Input.getInstance();
            String newDesc = input.getStr("Enter the description: ");
            setName(newDesc);
        }
        else{
            System.out.println("You are not authorized to perform this action!!");
        }

    }

    //access level check needs to be added
    public void updateDescription(){
         if(currentUser.getRole().adminAccess()){
            Input input = Input.getInstance();
            String newName = input.getStr("Enter the name: ");
            setDescription(newName);
        }
        else{
            System.out.println("You are not authorized to perform this action!!");
        }

    }
    */

}
