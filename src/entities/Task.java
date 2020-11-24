package entities;

import tools.Input;

public class Task extends Data{
    private User createdBy;
    private String status; //NotStarted/Ongoing/Completed?
    private String name;
    private String description;
   // private Team assignees;
    
    public Task(User createdBy, String name, String description){
        this.createdBy = createdBy;
        this.name = name;
        this.description = description;
        this.status = "Default";
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getStatus() {
        return status;
    }
    
/*
    public Team getAssignees() {
        return assignees;
    }
*/
    
    public User getCreatedBy() {
        return createdBy;
    }
    
    private void setName(String name) {
        this.name = name;
    }
    
    private void setDescription(String description) {
        this.description = description;
    }
    
    private void setStatus(String status) {
        this.status = status;
    }
    
    private void addAssignee() {
    
    }
    
    private void removeAssignee() {
    
    }
    
    public void updateName() {
        // if(role == ?)
        Input input = Input.getInstance();
        String newStatus = input.getStr("Enter the name: ");
    }
    
    public void updateDescription() {
        // if(role == ?)
        Input input = Input.getInstance();
        String newStatus = input.getStr("Enter the description: ");
    }
    
    public void updateStatus() {
        // Waiting to see if status is an object for tasks etc..
        // if(role == ?)
        Input input = Input.getInstance();
        String newStatus = input.getStr("Enter the description: ");
        setStatus(newStatus);
    }
    
    public void updateAssignees() {
        // if(role == ?)
        // addAssignee() OR removeAssignee()
    }
    
}
