package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Task extends Data{
    private User createdBy;
    private String name;
    private String description;
    private String status; //NotStarted/Ongoing/Completed?
    private LocalDate startDate;
    private LocalDate deadline;
    private ArrayList<User> assignees;
    private ArrayList<WorkedHours> workedHours;
    
    public Task(User createdBy, String name, String description, LocalDate startDate, LocalDate deadline){
        this.createdBy = createdBy;
        this.name = name;
        this.description = description;
        this.status = "Default";
        this.startDate = startDate;
        this.deadline = deadline;
        this.assignees = new ArrayList<User>();
        this.workedHours = new ArrayList<>();
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
    
    public ArrayList<User> getAssignees() {
        return assignees;
    }

    public ArrayList<WorkedHours> getWorkedHours() {
        return workedHours;
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

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public static Comparator<Data> sortByDeadline = new Comparator<Data>() {
        @Override
        public int compare(Data o1, Data o2) {
            Task object2 = (Task) o2;
            Task object1 = (Task) o1;
            return object1.getDeadline().compareTo(object2.getDeadline());
        }
    };

    public static Comparator<Data> sortByStartDate = new Comparator<Data>() {
        @Override
        public int compare(Data o1, Data o2) {
            Task object2 = (Task) o2;
            Task object1 = (Task) o1;
            return object1.getStartDate().compareTo(object2.getStartDate());
        }
    };

    public void addWorkedHours(WorkedHours log){
        this.workedHours.add(log);
    }

@Override
    public String printTaskInfo(Task task) {
        return "Task: " + task.getName() + "\n" + task.getDescription() + "" + "\n" + "Team Members: " + task.getAssignees().toString() + "\n";
    }
}
