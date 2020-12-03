package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Task extends Data{
    private User createdBy;
    private String name;
    private String description;
    private String status; //NotStarted/Ongoing/Completed?
    private LocalDate deadline;
    private ArrayList<User> assignees;
    
    public Task(User createdBy, String name, String description, LocalDate deadline){
        this.createdBy = createdBy;
        this.name = name;
        this.description = description;
        this.status = "Default";
        this.deadline = deadline;
        this.assignees = new ArrayList<User>();
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

    public static Comparator<Data> sortByDate = new Comparator<Data>() {

        @Override
        public int compare(Data o1, Data o2) {
            Task object2 = (Task) o2;
            Task object1 = (Task) o1;
            return object1.getDeadline().compareTo(object2.getDeadline());
        }
    };

    public String toString(Task task) {
        return "Task: " + task.getName() + "\n" + task.getDescription() + "" + "\n" + "Team Members: " + task.getAssignees().toString() + "\n";
    }
}
