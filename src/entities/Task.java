package entities;

import tools.Input;

public class Task extends Data{
    private User createdBy;
    private String status; //NotStarted/Ongoing/Completed?
    private String name;
    private String description;
   // private UserLibrary assignees; // Should this be UserLibrary instead?
    
    public Task(User createdBy, String name, String description){
        this.createdBy = createdBy;
        this.name = name;
        this.description = description;
        this.status = "Default";
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getStatus() {
        return status;
    }
    
/*
    public DataLibrary getAssignees() {
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
        Input input = Input.getInstance();
        // if(role == ?)
        String newStatus = input.getStr("Enter the name: ");
    }
    
    public void updateDescription() {
        Input input = Input.getInstance();
        // if(role == ?)
        String newStatus = input.getStr("Enter the description: ");
    }
    
    public void setStatus() {
        // Waiting to see if status is an object etc..
    }
    
    public void updateAssignees() {
        // if(role == ?)
        // addAssignee() OR removeAssignee()
    }
    
}
