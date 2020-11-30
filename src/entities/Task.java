package entities;

public class Task extends Data{
    private User createdBy;
    private String name;
    private String description;
    private String status; //NotStarted/Ongoing/Completed?
    private Team assignees;
    
    public Task(User createdBy, String name, String description){
        this.createdBy = createdBy;
        this.name = name;
        this.description = description;
        this.status = "Default";
        this.assignees = new Team();
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
    
    public Team getAssignees() {
        return assignees;
    }
    
    public User getCreatedBy() {
        return createdBy;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
}
