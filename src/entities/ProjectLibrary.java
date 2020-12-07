package entities;
import tools.*;


import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectLibrary extends DataLibrary{
    private static final ProjectLibrary instance = null;

    public static ProjectLibrary getInstance() {
        if (instance == null) {
            return new ProjectLibrary();
        } else {
            return instance;
        }
    }
    public void createProject(User currentUser) {
        Input input = Input.getInstance();
        String name = input.getStr("Project name: ");
        String description = input.getStr("Project description: ");
        LocalDate startDate = input.getDate("Project start date (YYYY-MM-DD): ");
        LocalDate endDate = input.getDate("Project end date (YYYY-MM-DD): ");
        float totalBudget = input.getFloat("Total Budget of Project: ");
        if(confirmAction("Are you sure you want to create this project?")){
            addToList(new Project(name, currentUser, description, startDate, endDate, totalBudget));
        }
    }

    public void addProjectToList(Project project)
    {
        list.add(project);
    }

    public ArrayList<Project> listUsersProjects(User currentUser){
        ArrayList<Project> tempList = new ArrayList<>();
        for(Data project : list){
            Project currentProject = ((Project)project);


            if ((currentProject.getTeam() == null &&
                 currentProject.getProjectManager().getUserName().equals(currentUser.getUserName()))
                ||
                (currentProject.getTeam() != null &&
                 currentProject.getTeam().findTeamMember(currentUser) != null)) {

                tempList.add(currentProject);
            }
        }

        if (tempList.size() == 0){
            System.out.println("You have no projects!");
        } else {
            for (int i=0; i<tempList.size(); i++){
                System.out.println(" " + (i + 1) + ") " + tempList.get(i).getName());
            }
        }

        return tempList;
    }

    public Project navigateBetweenProjects(User currentUser){
        Input input = Input.getInstance();
        ArrayList<Project> projectList = listUsersProjects(currentUser);
        if(projectList.size()==0){
            return null;
        } else {
            int choice;
            do{
                choice = input.getInt("Enter project number or 0 to return to the previous menu: ");
            } while(choice < 0 || choice > projectList.size());

            if (choice == 0){
                return null;
            } else
                return projectList.get(choice-1);
        }
    }
    
    public void updateName(User currentUser){
        Project currentProject = null;
        for(Data project : list) {
            currentProject = ((Project) project);
        }
            if(currentProject.getTeam().findTeamMember(currentUser).getRole().adminAccess()){
            Input input = Input.getInstance();
            String newDesc = input.getStr("Enter the description: ");
            currentProject.setName(newDesc);
        }
        else{
            System.out.println("You are not authorized to perform this action!!");
        }
        
    }

    public void viewProjectDetails(Project currentProject){
        if(findItInList(currentProject.getID()) != null){
            System.out.println("Project Name: " + currentProject.getName());
            System.out.println("Project Description: " + currentProject.getDescription());
            if(!currentProject.getStatus().isEmpty()){
                System.out.println("Status: "+ currentProject.getStatus());
            }
            System.out.println("Start Date: " + currentProject.getStartDate());
            System.out.println("End Date: " + currentProject.getEndDate());
        } else {
            System.out.println("Project does not exist!");
        }

    }

    public void updateStatus(Project currentProject, User currentUser){
        if(currentProject.getProjectManager().checkID(currentUser.getID())){
            Input input = Input.getInstance();
            String newStatus = input.getStr("Enter the status: ");
            if(confirmAction("Are you sure you want to update the status?")){
                currentProject.setStatus(newStatus);
            }
        }
        else{
            System.out.println("You are not authorized to perform this action!");
        }
    }
    /*public void updateName(Project currentProject, User currentUser){
        if(currentProject.team.findTeamMember(currentUser).getRole().roleType().equals("Owner")){
            Input input = Input.getInstance();
            String newName = input.getStr("Enter the new name or enter 0 to abort: ");
            if(newName.equals("0")){
                System.out.println("Action aborted!");
            }else{
                currentProject.setName(newName);
            }
        }
        else{
            System.out.println("You are not authorized to perform this action!");
        }
    }    public void updateDescription(Project currentProject, User currentUser){
        if(currentProject.team.findTeamMember(currentUser).getRole().roleType().equals("Owner")){
            Input input = Input.getInstance();
            String newName = input.getStr("Enter the new description or enter 0 to abort: ");
            if(newName.equals("0")){
                System.out.println("Action aborted!");
            }else{
                currentProject.setName(newName);
            }
        }
        else{
            System.out.println("You are not authorized to perform this action!");
        }
    }*/


    public boolean deleteProject(Project currentProject, User currentUser){
        Project projectToDelete = (Project)findItInList(currentProject.getID());
        if(projectToDelete==null){
            System.out.println("Project does not exist!");
            return false;
        }
        if(projectToDelete.getProjectManager().checkID(currentUser.getID())){
            Input input = Input.getInstance();
            System.out.println("Warning!");
            System.out.println("You are about to delete the entire project!");
            System.out.println("This action is irreversible!");
            String password = input.getStr("Please enter password to confirm the deletion (leave empty to abort): ");
            if(currentUser.getPassword().equals(password)){
                if(removeItFromList(projectToDelete.getID())){
                    System.out.println("Project successfully deleted!");
                    System.out.println("Returning to main menu...");
                    return true;
                } else {
                    System.out.println("Something went wrong");
                    return false;
                }
            } else if(password.isEmpty()){
                System.out.println("Action aborted!");
                System.out.println("Returning to main menu...");
                return false;
            } else {
                System.out.println("Confirmation Failed!");
                return false;
            }

        }
        System.out.println("You are not authorized to perform this action!");
        return false;
    }

    private boolean confirmAction(String text){
        Input input = Input.getInstance();
        System.out.println(text);
        boolean confirmCheck = false;
        while(!confirmCheck){
            String confirmation = input.getStr("Please enter yes or no: ");
            if(confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")){
                return true;
            } else if (confirmation.equalsIgnoreCase("no") || confirmation.equalsIgnoreCase("n")) {
                System.out.println("Action aborted!");
                return false;
            } else {
                System.out.println("Invalid input!");
            }
        } return false;

    }

    public void viewCost(Project currentProject)
    {
        Project project = (Project)findItInList(currentProject.getID());
        if(project==null){
            System.out.println("Project does not exist!");
        } else {
            TaskLibrary tempLibrary = currentProject.taskList;
            double totalCost = 0;
            for (Data task : currentProject.taskList.list)
            {
                Task currentTask = (Task) task;
                double totalCostPerTask = 0;

                for (WorkedHours log : currentTask.getWorkedHours())
                {
                    totalCostPerTask += log.getUser().getSalary() * log.getWorkedHours();

                }
                System.out.println("The total cost for the Task  " + currentTask.getName() + " " + totalCostPerTask + "kr.");
                totalCost += totalCostPerTask;
            }
            System.out.println("Total cost for the project are " + totalCost + "kr.");
        }
    }

    public void printAllProjects()
    {
        for (Data project : list)
        {
          if (project instanceof Project)
          {
              System.out.println(project.toString());
          }
        }
    }
}
