package entities;
import budget.Budget;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Project extends Data {
    private String name;
    private String description;
    private String status; // Active/Inactive
    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate endDate;


    protected Budget budget;
    protected TaskLibrary taskList;
    protected TeamLibrary teamLibrary = TeamLibrary.getInstance();
    private User projectManager;
    private Team team = null; // no team by default as part of the acceptance criteria

    public Project(String name, User projectManager, String description, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.description = description;
        this.createdDate = LocalDate.now();
        this.startDate = startDate;
        this.endDate = endDate;
        this.taskList = new TaskLibrary();
        this.status = "";
        this.projectManager = projectManager;
        this.budget = new Budget();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeam(Team team) { this.team = team; }

    public String getName() {
        return name;
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

    public User getProjectManager() {return projectManager; }

    public Team getTeam() { return team; }

    public Budget getBudget() {
        return budget;
    }

    public long duration() {
        long daysBetween = ChronoUnit.DAYS.between(getStartDate(), getEndDate());
        return daysBetween;
    }
    public String timeLeftBeforeExceedingBudget()
    {
        return "Total Time Before Exceeding budget\n" +
                "Recommend Member to cut" // to be implemented
                + timeLeftBeforeExceedingBudget();
    }

    //not mentioned in the user stories soooo idk
    /*
    public void updateName(){
        if(team.findTeamMember(currentUser).getRole().adminAccess()){
            Input input = Input.getInstance();
            String newDesc = input.getStr("Enter the description: ");
            setName(newDesc);
        }
        else{
            System.out.println("You are not authorized to perform this action!!");
        }

    }

    public void updateDescription(){
         if(team.findTeamMember(currentUser).getRole().adminAccess()){
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


