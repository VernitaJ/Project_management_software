package entities;
import tools.*;


import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectLibrary extends DataLibrary{
    private static final ProjectLibrary instance = null;
    Input input = Input.getInstance();
    
    public static ProjectLibrary getInstance() {
        if (instance == null) {
            return new ProjectLibrary();
        } else {
            return instance;
        }
    }
    public void createProject(User currentUser) {
        String name = input.getStr("Project name: ");
        String description = input.getStr("Project description: ");
        LocalDate startDate = input.getDate("Project start date (YYYY-MM-DD): ");
        LocalDate endDate = input.getDate("Project end date (YYYY-MM-DD): ");
        addToList(new Project(name, currentUser, description, startDate, endDate));
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
                System.out.println(i+1 + "- " + tempList.get(i).getName());
            }
        }
        
        return tempList;
    }
    
    public Project navigateBetweenProjects(User currentUser){
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
        if(currentProject.getTeam().findTeamMember(currentUser).getRole().adminAccess()){
            String newStatus = input.getStr("Enter the status: ");
            currentProject.setStatus(newStatus);
        }
        else{
            System.out.println("You are not authorized to perform this action!");
        }
    }
    
    
    public boolean deleteProject(Project currentProject, User currentUser){
        Project projectToDelete = (Project)findItInList(currentProject.getID());
        if(projectToDelete==null){
            System.out.println("Project does not exist!");
            return false;
        }
        if(currentProject.getTeam().findTeamMember(currentUser).getRole().equals("Owner")){
            System.out.println("Warning!");
            System.out.println("You are about to delete the entire project!");
            System.out.println("This action is irreversible!");
            String email = input.getStr("Please enter email address to confirm the deletion: ");
            String password = input.getStr("Please enter password: ");
            if(currentUser.getEmail().equals(email) && currentUser.getPassword().equals(password)){
                if(removeItFromList(currentProject.getID())){
                    System.out.println("Project successfully deleted!");
                    System.out.println("Returning to main menu...");
                    return true;
                } else {
                    System.out.println("Something went wrong");
                    return false;
                }
            } else {
                System.out.println("Confirmation Failed!");
                return false;
            }
        }
        System.out.println("You are not authorized to perform this action!");
        return false;
    }
    
    public void addBudget(Project currentProject) {
        int choice = -1;
        double value = 0.0;
        String message = "Which budget do you want to add? \n" +
                "1. Budget in SEK \n" +
                "2. Budget in Hours \n" +
                "0. Return to previous menu";
        do {
            choice = input.getInt(message);
            value = input.getDouble("What is the total budget value?");
        } while(choice < 0 || choice > 2);
        
        if(choice == 0) {
            return;
        } else if(choice == 1) {
            currentProject.getBudget().setMoney(value);
        } else if(choice == 2) {
            currentProject.getBudget().setHours(value);
        }
        System.out.println("Budget has been set");
    }
    
}
