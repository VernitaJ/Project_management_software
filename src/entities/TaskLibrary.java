package entities;

import tools.Input;

import java.util.ArrayList;

public class TaskLibrary extends DataLibrary{
    private static final TaskLibrary instance = null;
    
    public static TaskLibrary getInstance() {
        if (instance == null) {
            return new TaskLibrary();
        } else {
            return instance;
        }
    }
    
    public void createTask(User createdBy){
        Input input = Input.getInstance();
        String name = input.getStr("Task Name: ");
        String description = input.getStr("Task Description: ");
        addToList(new Task(createdBy, name, description));
    }
    
    public ArrayList<Task> listProjectsTasks(Project currentProject){
/*        if((currentProject.taskList == null)) {
            return null;
        }*/
        ArrayList<Task> tempList = new ArrayList<>();
        for(Data task : list){
            Task currentTask = ((Task)task);
            
            //if((currentProject.team.findTeamMember(currentProject) != null)){
            tempList.add(currentTask);
            //}
        }
        if (tempList.size() == 0){
            System.out.println("This project does not have any tasks!");
        } else {
            for (int i=0; i<tempList.size(); i++){
                System.out.println(i+1 + "- " + tempList.get(i).getName());
            }
        }
        
        return tempList;
    }
    
    public Task navigateBetweenTasks(Project currentProject){
        Input input = Input.getInstance();
        ArrayList<Task> taskList = listProjectsTasks(currentProject);
        if(taskList.size()==0){
            return null;
        } else {
            int choice;
            do{
                choice = input.getInt("Enter task number or 0 to return to the previous menu: ");
            } while(choice < 0 || choice > taskList.size());
            
            if (choice == 0){
                return null;
            } else
                return taskList.get(choice-1);
        }
    }
    
    public void viewTaskDetails(Task currentTask){
        if(findItInList(currentTask.getID()) != null){
            System.out.println("Task Name: " + currentTask.getName());
            if(!currentTask.getStatus().isEmpty()){
                System.out.println("Status: "+ currentTask.getStatus());
            }
            System.out.println("Description: " + currentTask.getDescription());
            System.out.println("Assignees: " + currentTask.getAssignees().toString());
        } else {
            System.out.println("Task does not exist!");
        }
        
    }
    
    public void updateStatus(Project currentProject, Task currentTask, User currentUser){
        if(currentProject.team.findTeamMember(currentUser).getRole().adminAccess()){
            Input input = Input.getInstance();
            String newStatus = input.getStr("Enter the status: ");
            currentTask.setStatus(newStatus);
        }
        else{
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
    
/*
    public void deleteTask(User currentUser, String taskIDToRemove){
        if(accesslevel>3)
            removeItFromList(taskIDToRemove);
    }
*/
    
    
    //public void importTask(){}
    
}


