package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import tools.Input;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;


@JsonDeserialize(as = Task.class)

public class Task extends Data {
    private User createdBy;
    private String name;
    private String description;
    private String status; //NotStarted/Ongoing/Completed?
    private LocalDate startDate;
    private LocalDate deadline;
    private ArrayList<User> assignees;
    private ArrayList<WorkedHours> workedHours;
    @JsonIgnoreProperties
    private ArrayList<Note> notes = new ArrayList<>();
    private Input input = Input.getInstance();

    public Task(User createdBy, String name, String description, LocalDate startDate, LocalDate deadline){
        this.createdBy = createdBy;
        this.name = name;
        this.description = description;
        this.status = "Default";
        this.startDate = startDate;
        this.deadline = deadline;
        this.assignees = new ArrayList<>();
        this.workedHours = new ArrayList<>();
    }
    
    public Task(User createdBy, String name, String description) {
        this.createdBy = createdBy;
        this.name = name;
        this.description = description;
        this.status = "Default";
        this.assignees = new ArrayList<User>();
        this.workedHours = new ArrayList<>();
    }
    
    public Task() {
    }
    
    public void setAssignees(ArrayList<User> assignees) {
        this.assignees = assignees;
    }
    
    public void setWorkedHours(ArrayList<WorkedHours> workedHours) {
        this.workedHours = workedHours;
    }
    
    public void setCreatedBy(User createdBy){
        this.createdBy=createdBy;
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

    public void addNote(User currentUser) {
        String message = input.getStr("Note: ");
        Note note = new Note(currentUser, message);
        this.notes.add(note);
        System.out.println("Note successfully appended to this task!");
    }

    public void viewUserNotes(User currentUser) {
        boolean found = false;
        for (int i = 0; i < notes.size(); i++){
            if (notes.get(i).getUser().equals(currentUser)){
                System.out.println((i + 1) + ". " + notes.get(i).toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("You do not have notes in the current task.");
        }
    }

   /*  Might be useful if we wanted non-personal notes? Not explicitly stated in the user story

   public void viewAllNotes() {
        for (int i = 0; i < notes.size(); i++){
            System.out.println((i + 1) + ". " + notes.get(i).getUsername() + " | " + notes.get(i).toString());
        }
    }
    */

    private int selectNote(User currentUser) {
        ArrayList<Integer> userNotes = new ArrayList<>();
        int counter = 1;
        for (int i = 0; i < notes.size(); i++){
            if (notes.get(i).getUser().equals(currentUser)){
                System.out.println(counter + ". " + notes.get(i).toString());
                userNotes.add(i);
                counter++;
            }
        }
        if (userNotes.size() > 0) {
            boolean correct = false;
            int option = -1;
            do {
                option = input.getInt("Select the note: ");
                if (option > 0 && option <= userNotes.size()) {
                    correct = true;
                } else {
                    System.out.println("Incorrect option selected. Please try again.");
                }
            } while (correct != true);
            return userNotes.get(option - 1).intValue();
        } else {
            System.out.println("You do not have notes in the current task.");
            return -1;
        }
    }

    public void deleteNote(User currentUser) {
        int option = selectNote(currentUser);
        if (option != -1) {
            notes.remove(option);
            System.out.println("Note was deleted successfully.");
        }
    }

    public void editNote(User currentUser) {
        int option = selectNote(currentUser);
        if (option != -1) {
            String message = input.getStr("Enter new message: ");
            notes.get(option).editMessage(currentUser, message);
        }
    }



@Override
    public String printTaskInfo(Task task) {
        return "Task: " + task.getName() + "\n" + task.getDescription() + "" + "\n" + "Team Members: " + task.getAssignees().toString() + "\n";
    }
}
