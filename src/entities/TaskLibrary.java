package entities;

import tools.Input;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static entities.Message.sortByName;
import static entities.Task.sortByDate;

public class TaskLibrary extends DataLibrary {
    private static final TaskLibrary instance = null;

    public static TaskLibrary getInstance() {
        if (instance == null) {
            return new TaskLibrary();
        } else {
            return instance;
        }
    }

    public boolean confirmAccess(Project currentProject, User currentUser) {
        if (currentProject.team.findTeamMember(currentUser).getRole().adminAccess()) {
            return true;
        } else {
            return false;
        }
        // Below can be used in other methods for testing access.
        /*
        if (!confirmAccess(currentProject, currentUser)) {
            System.out.println("You are not authorized to perform this action!!");
            return;
        }
        */
    }

    public void createTask(Project currentProject, User currentUser) {
        System.out.println("Enter 0 at any step to return to the previous menu: ");
        Input input = Input.getInstance();
        String name = input.getStr("Task Name: ");
        if (input.abort(name)) {
            System.out.println("Returning to project menu...");
            return;
        }

        String description = input.getStr("Task Description: ");
        if (input.abort(description)) {
            System.out.println("Returning to project menu...");
            return;
        }

        LocalDate deadline = input.getDate("Task Deadline (YYYY-MM-DD): ");
        if (input.abort(description)) {
            System.out.println("Returning to project menu...");
            return;
        }
        currentProject.taskList.addToList(new Task(currentUser, name, description, deadline));
    }

    public void deleteTask(Project currentProject, User currentUser) {
        Task currentTask = navigateBetweenTasks(currentProject);
        if (currentTask == null) {
            return;
        }
        Task taskToDelete = (Task) findItInList(currentTask.getID());
        if (taskToDelete == null) {
            System.out.println("Task does not exist!");
            return;
        }
        Input input = Input.getInstance();
        String choice = "";
        System.out.println("You are about to delete this task!");
        do {
            choice = input.getStr("Are you sure you want to delete this task? Y/N: ");
        } while (!choice.toUpperCase().equals("Y") && !choice.toUpperCase().equals("N"));
        if (choice.toUpperCase().equals("Y")) {
            if (removeItFromList(currentTask.getID())) {
                System.out.println("Task successfully deleted");
                System.out.println("Returning to project menu...");
                return;
            }
        } else {
            System.out.println("Task not deleted");
            System.out.println("Returning to project menu...");
            return;
        }
    }

    public ArrayList<Task> listProjectsTasks(Project currentProject) {
        ArrayList<Task> tempList = new ArrayList<>();
        for (Data task : currentProject.taskList.list) {
            Task currentTask = ((Task) task);
            tempList.add(currentTask);
        }
        if (tempList.size() == 0) {
            System.out.println("This task does not exist!");
        } else {
            for (int i = 0; i < tempList.size(); i++) {
                System.out.println(i + 1 + "- " + tempList.get(i).getName());
            }
        }
        return tempList;
    }

    public Task navigateBetweenTasks(Project currentProject) {
        Input input = Input.getInstance();
        ArrayList<Task> taskList = listProjectsTasks(currentProject);
        if (taskList.size() == 0) {
            return null;
        } else {
            int choice;
            do {
                choice = input.getInt("Enter task number or 0 to return to the previous menu: ");
            } while (choice < 0 || choice > taskList.size());

            if (choice == 0) {
                return null;
            } else
                return taskList.get(choice - 1);
        }
    }

    public void viewTaskDetails(Task currentTask) {
        if (findItInList(currentTask.getID()) != null) {
            System.out.println("Task Name: " + currentTask.getName());
            if (!currentTask.getStatus().isEmpty()) {
                System.out.println("Status: " + currentTask.getStatus());
            }
            System.out.println("Description: " + currentTask.getDescription());
            System.out.println("Assignees: " + currentTask.getAssignees().toString());
        } else {
            System.out.println("Task does not exist!");
        }

    }

    public void updateStatus(Project currentProject, Task currentTask, User currentUser) {
        if (confirmAccess(currentProject, currentUser)) {
            Input input = Input.getInstance();
            String newStatus = input.getStr("Enter the status: ");
            currentTask.setStatus(newStatus);
        } else {
            System.out.println("You are not authorized to perform this action!");
        }
    }

    public void addAssignee(Project currentProject, Task currentTask) {
        // Check user rights to add assignee
        // IF TRUE
        // Retrieve list of projects team members.
        // Print list of team members
        // Input to select member to assign OR exit
        // ELSE
        // Print "not authorized"
        // RETURN
    }

    public void removeAssignee(Project currentProject, Task currentTask) {
    }

    public void countdown(Project currentProject) {
        ArrayList<Data> countdown = currentProject.taskList.list;
        Collections.sort(countdown, sortByDate);
        for (Data task : countdown) {
            Task projectTask = (Task) task;
            long daysToDeadline = ChronoUnit.DAYS.between(LocalDate.now(), projectTask.getDeadline());
            Team members = projectTask.getAssignees();
            System.out.println("Days to Deadline: " + daysToDeadline + "\n" + "Task: " + projectTask.getName() + "\n" + "Description" + projectTask.getDescription() + "" + "\n" + "Team Members: " + members.toString() + "\n");
        }
    }

    public void completedTasks(Project currentProject) {
        ArrayList<Data> tasks = currentProject.taskList.list;
        Collections.sort(tasks, sortByDate);
        for (Data task : tasks) {
            Task projectTask = (Task) task;
            if (projectTask.getStatus().equalsIgnoreCase("completed")) {
                Team members = projectTask.getAssignees();
                System.out.println("Task Deadline" + projectTask.getDeadline() + "\n" + " Task: " + projectTask.getName() + "\n" + "Description" + projectTask.getDescription() + "" + "\n" + "Team Members: " + members.toString() + "\n");
            }
        }
    }
}

