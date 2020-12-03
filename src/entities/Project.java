package entities;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Project extends Data {
    private String name;
    private String description;
    private String status; // Active/Inactive
    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate endDate;
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


    public long duration() {
        long daysBetween = ChronoUnit.DAYS.between(getStartDate(), getEndDate());
        return daysBetween;
    }
}


