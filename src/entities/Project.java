package entities;

// import date time for calculating Duration

public class Project extends Data{
    // Data.projectID
    private String name;
    private String owner; // Change to User
    // private Team team;
    // private TaskList taskList;
    // private createdDate;
    // private startDate;
    // private endDate;
    private String description;
    private String status; // Active/Inactive
    
    public Project(String name, String owner, String startDate, String endDate) {
    }
    
    public int duration() {
        // startDate - endDate;
        return 1;
    }
    
    // Getters & Setters
    
}
