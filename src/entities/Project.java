package entities;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Project extends Data {
    protected Team team;
    private String name;
    private String description;
    private String status; // Active/Inactive
    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate endDate;
    protected TaskLibrary taskList;

    public Project(String name, User owner, String description, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.description = description;
        this.createdDate = LocalDate.now();
        this.startDate = startDate;
        this.endDate = endDate;
        this.taskList = new TaskLibrary();
        this.status = "";
        try {
            this.team = new Team(owner);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public long duration() {
        long daysBetween = ChronoUnit.DAYS.between(getStartDate(), getEndDate());
        return daysBetween;
    }

}


