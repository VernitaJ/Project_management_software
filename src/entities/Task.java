package entities;

public class Task extends Data{
    private User createdBy;
    // User assignedTo; //we can create a new class extends DataLibrary
    private String status; //NotStarted/Ongoing/Completed?
    private String name;
    private String description;
    private User assignees;
    
    public Task(User createdBy, String name, String description){
        this.createdBy = createdBy;
        this.name = name;
        this.description = description;
        this.status = "Default";
    }
    
    //public assign(){}
    
    //public changeStatus(){}
    
    //public changeTitle(){}
    
    //public changeDescription(){}
    
    //getters
    //setter
    
    
    
    
    
    
    
}
